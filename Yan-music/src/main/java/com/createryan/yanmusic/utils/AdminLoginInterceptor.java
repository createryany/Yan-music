package com.createryan.yanmusic.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.createryan.yanmusic.dto.AdminDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.createryan.yanmusic.utils.RedisConstants.LOGIN_ADMIN_KEY;
import static com.createryan.yanmusic.utils.RedisConstants.LOGIN_ADMIN_TTL;

/**
 * @author: createryan
 * @date 2022/7/27 4:05
 */

public class AdminLoginInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public  AdminLoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1.获取请求头中的token
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            // 如果为空则拦截，返回状态码401
            response.setStatus(401);
            return false;
        }
        // 2.基于token获取redis中的admin信息
        String tokenKey = LOGIN_ADMIN_KEY + token;
        Map<Object, Object> adminMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        // 3.判断admin是否为空，为空则拦截
        if (adminMap.isEmpty()) {
            // 3.1.为空则拦截，返回状态码401
            response.setStatus(401);
            return false;
        }
        // 4.将查询到的Hash数据转换为AdminDTO对象
        AdminDTO adminDTO = BeanUtil.fillBeanWithMap(adminMap, new AdminDTO(), false);
        System.out.println("adminDTO = " + adminDTO);
        // 5.如果不为空，保存用户信息到ThreadLocal中
        AdminHolder.saveAdmin(adminDTO);
        // 6.刷新token的有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_ADMIN_TTL, TimeUnit.MINUTES);
        // 7.放行
        return true;
    }
}
