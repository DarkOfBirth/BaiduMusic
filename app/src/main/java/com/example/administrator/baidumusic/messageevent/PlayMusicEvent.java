package com.example.administrator.baidumusic.messageevent;

/**
 * Created by Administrator on 2016/11/7.
 */

public class PlayMusicEvent {
    private String songId;

    public PlayMusicEvent(String songId) {
        this.songId = songId;
    }

    public PlayMusicEvent() {

    }

    public String getSongId() {

        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }
}
