package com.createryan.yanmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: createryan
 * @date 2022/7/27 1:25
 */

@Data
@TableName("rank_list")
public class RankList implements Serializable {

    /*
    * 主键
    * */
    private Long id;

    /*
    * 歌单id
    * */
    private Long songListId;

    /*
    * 用户id
    * */
    private Long consumerId;

    /*
    * 用户对歌单的评分
    * */
    private Integer score;
}
