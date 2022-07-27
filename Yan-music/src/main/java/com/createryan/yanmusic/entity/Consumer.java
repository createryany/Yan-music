package com.createryan.yanmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: createryan
 * @date 2022/7/27 1:20
 */

// 用户实体类
@Data
@TableName("consumer")
public class Consumer implements Serializable {

    /*
    * 主键
    * */
    private Integer id;

    /*
    * 用户名
    * */
    private String username;

    /*
    * 密码，加密存储
    * */
    private String password;

    /*
    * 性别
    * */
    private Byte sex;

    /*
    * 电话号码
    * */
    private String phoneNum;

    /*
    * 邮箱
    * */
    private String email;

    /*
    * 生日
    * */
    private Date birth;

    /*
    * 自我简介
    * */
    private String introduction;

    /*
    * 所在地区
    * */
    private String location;

    /*
    * 头像地址
    * */
    private String avator;

    /*
    * 创建时间
    * */
    private Date createTime;

    /*
    * 最后修改时间
    * */
    private Date updateTime;
}
