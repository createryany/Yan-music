package com.createryan.yanmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: createryan
 * @date 2022/7/27 1:04
 */

// 该类是管理员信息实体类
@Data
@TableName("admin")
public class Admin implements Serializable {

    /*
    * 主键
    * */
    private Integer id;

    /*
    * 管理员账户名
    * */
    private String name;

    /*
    * 管理员密码
    * */
    private String password;
}
