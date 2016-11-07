package com.example.administrator.baidumusic.messageevent;

/**
 * Created by Administrator on 2016/11/7.
 */
// 清空列表时传值
public class ClearEvent {
    boolean flag ;

    public ClearEvent(boolean b) {
        this.flag = b;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
