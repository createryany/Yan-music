package com.createryan.yanmusic.service;

import com.createryan.yanmusic.entity.RankList;

public interface RankListService {

    boolean addRank(RankList rankList);

    int rankOfSongListId(Long songListId);

    int getUserRank(Long consumerId, Long songListId);

}
