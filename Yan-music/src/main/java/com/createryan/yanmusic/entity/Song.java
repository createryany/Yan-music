package com.createryan.yanmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author: createryan
 * @date 2022/7/27 1:55
 */

@Data
@TableName("song")
public class Song {

    /*
    * 主键
    * */
    private Integer id;

    /*
    * 歌手id
    * */
    private Integer singerId;

    /*
    * 歌曲名
    * */
    private String name;

    /*
    * 歌曲简介
    * */
    private String introduction;

    /*
    * 创建时间
    * */
    private Date createTime;

    /*
    * 修改时间
    * */
    private Date updateTime;

    /*
    * 照片
    * */
    private String pic;

    /*
    * 带时间节点的歌词
    * */
    private String lyric;

    /*
    * 歌曲存放地址
    * */
    private String url;
}
