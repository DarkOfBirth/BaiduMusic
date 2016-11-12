package com.example.administrator.baidumusic.messageevent;

/**
 * Created by Administrator on 2016/11/11.
 */

public class ProgerssHandEvent {
    int position;

    public ProgerssHandEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
