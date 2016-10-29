package com.example.administrator.baidumusic.base;

import android.app.Application;
import android.content.Context;

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
}
