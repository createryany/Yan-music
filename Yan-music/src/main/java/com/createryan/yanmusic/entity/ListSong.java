package com.createryan.yanmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: createryan
 * @date 2022/7/27 1:24
 */

@Data
@TableName("list_song")
public class ListSong implements Serializable {

    /*
    * 主键
    * */
    private Integer id;

    /*
    * 歌曲id
    * */
    private Integer songId;

    /*
    * 歌曲关联的歌单id
    * */
    private Integer songListId;
}
