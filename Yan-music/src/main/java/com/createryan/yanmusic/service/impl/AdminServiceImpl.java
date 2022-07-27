package com.createryan.yanmusic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.createryan.yanmusic.dto.AdminDTO;
import com.createryan.yanmusic.entity.Admin;
import com.createryan.yanmusic.mapper.AdminMapper;
import com.createryan.yanmusic.service.IAdminService;
import com.createryan.yanmusic.utils.ErrorMessage;
import com.createryan.yanmusic.utils.MD5Utils;
import com.createryan.yanmusic.utils.SuccessMessage;
import org.apache.ibatis.jdbc.Null;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
     * 管理员登录校验(该方法只用于第一次登录)
     * @param adminDTO
     * @return
     */
    @Override
    public Object adminLogin(AdminDTO adminDTO, String tokenStr) {
        // 1.对用户名和密码去除空格
        String username = adminDTO.getName().trim();
        String password = adminDTO.getPassword().trim();
        // 2.判断用户是否存在
        Admin admin = query().eq("name", username).one();
        // 2.1.判断用户是否存在
        if (admin == null) {
            return new ErrorMessage("用户名或密码错误！").getMessage();
        }
        // 3.对密码进行md5加密
        String pd = MD5Utils.md5(password);
        // 4.判断密码是否正确
        if (!pd.equals(admin.getPassword())) {
            // 4.1.密码错误登录失败
            return new ErrorMessage("用户名或密码错误！").getMessage();
        }
        // 5.登录成功，判断token是否为空
        if (!tokenStr.isEmpty()) {
            // 5.1.不为空，刷新token有效期并登录
            return new SuccessMessage<>("登录成功", tokenStr).getMessage();
        }
        // 5.2.不为空，则保存用户信息和token到redis中
        // 5.1.生成随机token作为登录令牌
        String token = UUID.randomUUID().toString(true);
        // 5.2.将Admin对象转换为HashMap对象
        AdminDTO adminDTOHashMap = BeanUtil.copyProperties(admin, AdminDTO.class);
        Map<String, Object> adminMap = BeanUtil.beanToMap(adminDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((name, value) -> value.toString()));
        // 5.3.将token保存到redis中
        String tokenKey = LOGIN_ADMIN_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, adminMap);
        // 5.4.设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_ADMIN_TTL, TimeUnit.MINUTES);
        // 5.5.返回token
        return new SuccessMessage<>("登录成功", token).getMessage();
    }

    /**
     * 管理员退出登录
     * @return
     */
    @Override
    public Object adminLogout(String tokenStr) {
        // 1.拼接成tokenKey
        String tokenKey = LOGIN_ADMIN_KEY + tokenStr;
        // 2.删除redis中的token信息
        stringRedisTemplate.opsForHash().delete(tokenKey);
        return new SuccessMessage<Null>("退出成功！");
    }
}
