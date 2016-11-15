package com.example.administrator.baidumusic.messageevent;

/**
 * Created by Administrator on 2016/11/15.
 */

public class LoginInfoEvent {
    private String userName;

    public LoginInfoEvent(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
