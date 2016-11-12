package com.example.administrator.baidumusic.messageevent;

/**
 * Created by Administrator on 2016/11/11.
 */
public class MusicProgressEvent {

    int type ;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int duration;
    private int currentPosittion;
    public MusicProgressEvent(int type, int duration, int currentPosition) {
        this.duration = duration;
        this.currentPosittion = currentPosition;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCurrentPosittion() {
        return currentPosittion;
    }

    public void setCurrentPosittion(int currentPosittion) {
        this.currentPosittion = currentPosittion;
    }
}
