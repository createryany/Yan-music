package com.createryan.yanmusic.service;

import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.createryan.yanmusic.dto.AdminDTO;
import com.createryan.yanmusic.entity.Admin;

public interface IAdminService extends IService<Admin> {

    /*
    * 管理员登录检查
    * */
    Object adminLogin(AdminDTO adminDTO, String tokenStr);

    /*
    * 管理员退出登录
    * */
    Object adminLogout(String tokenStr);
}
