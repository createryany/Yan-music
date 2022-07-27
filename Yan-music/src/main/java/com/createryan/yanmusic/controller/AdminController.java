package com.createryan.yanmusic.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.createryan.yanmusic.dto.AdminDTO;
import com.createryan.yanmusic.service.IAdminService;
import com.createryan.yanmusic.utils.ErrorMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

/**
 * @author: createryan
 * @date 2022/7/27 3:36
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;

    /**
     * 登录功能(判断是否第一次登录，依据token是否为空)
     * @param map 登录参数，包括用户名和密码
     * @return
     */
    @PostMapping("/login/status")
    public Object adminLogin(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        // 获得用户的token，并传参给adminLogin方法
        String tokenStr = request.getHeader("authorization");
        // 1.将map中转换成AdminDTO对象
        AdminDTO adminDTO = BeanUtil.toBean(map, AdminDTO.class);
        // 2.调用登录功能
        return adminService.adminLogin(adminDTO, tokenStr);
    }

    /**
     * 退出登录 TODO
     * @return
     */
    @PostMapping("/logout")
    public Object adminLogout(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        if (token == null) {
            return new ErrorMessage("服务器错误，请稍后重试！").getMessage();
        }
        return adminService.adminLogout(token);
    }
}
