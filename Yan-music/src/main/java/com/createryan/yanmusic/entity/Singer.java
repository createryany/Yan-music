package com.createryan.yanmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author: createryan
 * @date 2022/7/27 1:53
 */

@Data
@TableName("singer")
public class Singer {

    /*
    * 主键
    * */
    private Integer id;

    /*
    * 歌手名
    * */
    private String name;

    /*
    * 性别
    * */
    private Byte sex;

    /*
    * 照片
    * */
    private String pic;

    /*
    * 生日
    * */
    private Date birth;

    /*
    * 出生地
    * */
    private String location;

    /*
    * 简介
    * */
    private String introduction;
}
