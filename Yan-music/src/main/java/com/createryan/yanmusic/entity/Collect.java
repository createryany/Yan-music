package com.createryan.yanmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: createryan
 * @date 2022/7/27 1:14
 */

// 该类是用户收藏信息实体类
@Data
@TableName("collect")
public class Collect implements Serializable {

    /*
    * 主键
    * */
    private Integer id;

    /*
    * 用户id
    * */
    private Integer userId;

    /*
    * 歌曲是否被收藏(0：收藏，1：取消收藏)
    * */
    private Byte type;

    /*
    * 歌曲id
    * */
    private Integer songId;

    /*
    * 歌单id(收藏歌单未实现)
    * */
    private Integer songListId;

    /*
    * 创建时间
    * */
    private Date createTime;
}
