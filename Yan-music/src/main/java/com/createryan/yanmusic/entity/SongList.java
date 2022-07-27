package com.createryan.yanmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: createryan
 * @date 2022/7/27 1:58
 */

@Data
@TableName("song_list")
public class SongList {

    /*
    * 主键
    * */
    private Integer id;

    /*
    * 歌单标题
    * */
    private String title;

    /*
    * 歌单照片
    * */
    private String pic;

    /*
    * 歌单类型
    * */
    private String style;
}
