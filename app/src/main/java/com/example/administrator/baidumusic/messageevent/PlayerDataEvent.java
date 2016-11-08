package com.example.administrator.baidumusic.messageevent;

import com.example.administrator.baidumusic.databean.MusicItemBean;

/**
 * Created by Administrator on 2016/11/8.
 */

public class PlayerDataEvent {

    MusicItemBean musicItemBean;

    public PlayerDataEvent(MusicItemBean response) {
        this.musicItemBean = response;
    }

    public MusicItemBean getMusicItemBean() {
        return musicItemBean;
    }

    public void setMusicItemBean(MusicItemBean musicItemBean) {
        this.musicItemBean = musicItemBean;
    }
}
