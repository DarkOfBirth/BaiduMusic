package com.example.administrator.baidumusic.base;

import android.app.Application;
import android.content.Context;

import com.example.administrator.baidumusic.main.MainActivity;

/**
 * Created by dllo on 16/10/24.
 */
public class MyApp extends Application {
    private static Context mContext;

    public MyApp() {
        mContext = this;

    }
    public static Context getmContext(){
        return mContext;
    }

    private MainActivity myActivity;

    public  void setInstance(MainActivity instance){
        myActivity = instance;
    }

    public  MainActivity getInstance(){
        return myActivity;
    }
}
