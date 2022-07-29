package com.createryan.yanmusic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.createryan.yanmusic.dto.AdminDTO;
import com.createryan.yanmusic.entity.Admin;
import com.createryan.yanmusic.mapper.AdminMapper;
import com.createryan.yanmusic.service.IAdminService;
import com.createryan.yanmusic.utils.ErrorMessage;
import com.createryan.yanmusic.utils.MD5Utils;
import com.createryan.yanmusic.utils.SuccessMessage;
import org.apache.ibatis.jdbc.Null;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.createryan.yanmusic.utils.RedisConstants.*;

/**
 * @author: createryan
 * @date 2022/7/27 2:13
 */

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 管理员登录校验
     * @param adminDTO
     * @return
     */
    @Override
    public Object adminLogin(@NotNull AdminDTO adminDTO, String tokenStr) {
        // 1.对用户名和密码去除空格
        String username = adminDTO.getName().trim();
        String password = adminDTO.getPassword().trim();
        // 1.1.对密码进行md5加密
        String pd = MD5Utils.md5(password);
        // 2.如果token不为空，则去redis查找用户信息
        if (tokenStr != null) {
            // 2.1.不为空
            String tokenKey = LOGIN_ADMIN_KEY + tokenStr;
            Map<Object, Object> adminMap = stringRedisTemplate.opsForHash().entries(tokenKey);
            // 2.2.判断admin登录信息为空则报错(情况很少)
            if (adminMap.isEmpty()) {
                // 2.3.adminMap为空
                return new ErrorMessage("服务器异常！").getMessage();
            }
            // 2.4.adminMap不为空，则获取redis中的用户名和密码
            String nameFromRedis = (String) adminMap.get("name");
            String pdFromRedis = (String) adminMap.get("password");
            if (!username.equals(nameFromRedis)) {
                // 2.5.用户名不同，则认定为第一次登录
                // 删除已有用户
                stringRedisTemplate.delete(tokenKey);
                tokenAndRedis(username, pd, adminDTO);
            } else if (!pd.equals(pdFromRedis)) {
                // 2.5.密码错误登录失败
                return new ErrorMessage("用户名或密码错误！").getMessage();
            }
            return new SuccessMessage<>("登录成功", tokenStr).getMessage();
        }
        // 3.第一次登录
        return tokenAndRedis(username, pd, adminDTO);
    }

    /**
     * 该方法是用于第一次登录(无token)，
     * 查找数据库，验证用户信息，验证成功后，
     * 返回给前端成功信息和token
     */
    public Object tokenAndRedis(String username, String pd, AdminDTO adminDTO) {
        // 3.没有token，第一次登录去数据库查询用户信息
        Admin admin = query().eq("name", username).one();
        // 3.判断用户是否存在
        if (admin == null) {
            return new ErrorMessage("用户名或密码错误！").getMessage();
        }
        // 4.判断密码是否正确
        if (!pd.equals(admin.getPassword())) {
            // 4.1.密码错误登录失败
            return new ErrorMessage("用户名或密码错误！").getMessage();
        }
        // 1.1.保存用户信息和token到redis中
        // 1.2.如果有就用传进来的tokenStr作为token
        String token = UUID.randomUUID().toString(true);
        // 2.将Admin对象转换为HashMap对象
        AdminDTO adminDTOHashMap = BeanUtil.copyProperties(admin, AdminDTO.class);
        Map<String, Object> adminMap = BeanUtil.beanToMap(adminDTOHashMap, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((name, value) -> value.toString()));
        // 3.将token保存到redis中
        String tokenKey = LOGIN_ADMIN_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, adminMap);
        // 4.设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_ADMIN_TTL, TimeUnit.MINUTES);
        // 5.返回token
        return new SuccessMessage<>("登录成功", token).getMessage();
    }

    /**
     * 管理员退出登录
     * @return
     */
    @Override
    public Object adminLogout(String tokenStr) {
        // 1.如果tokenStr不为空拼接出tokenKey
        if (tokenStr == null) {
            // 没有token信息(情况很少)
            return new SuccessMessage<Null>("退出成功！");
        }
        String tokenKey = LOGIN_ADMIN_KEY + tokenStr;
        // 2.删除redis中的token信息
        stringRedisTemplate.delete(tokenKey);
        return new SuccessMessage<Null>("退出成功！").getMessage();
    }
}
