package com.createryan.yanmusic.service;

import com.createryan.yanmusic.entity.ListSong;

import java.util.List;

public interface ListSongService {

    boolean addListSong(ListSong listSong);

    boolean updateListSongMsg(ListSong listSong);

    boolean deleteListSong(Integer songId);

    List<ListSong> allListSong();

    List<ListSong> listSongOfSongId(Integer songListId);
}
