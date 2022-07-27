package com.createryan.yanmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: createryan
 * @date 2022/7/27 1:16
 */

// 该类是评论的实体类
@Data
@TableName("comment")
public class Comment implements Serializable {

    /*
    * 主键
    * */
    private Integer id;

    /*
    * 用户id
    * */
    private Integer userId;

    /*
    * 歌曲id
    * */
    private Integer songId;

    /*
    * 歌单id
    * */
    private Integer songListId;

    /*
    * 评论内容
    * */
    private String content;

    /*
    * 创建时间
    * */
    private Date createTime;

    /*
    * 评论是否已经删除(0：未删除，1：已删除)
    * */
    private Byte type;

    /*
    * 点赞数
    * */
    private Integer up;
}
