package com.example.administrator.baidumusic.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.administrator.baidumusic.main.MainActivity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/24.
 */
public class MyApp extends Application {
    private static ArrayList<Activity> activityArrayList = new ArrayList<>();
    private static Context mContext;

    public MyApp() {
        mContext = this;

    }
    public static Context getmContext(){
        return mContext;
    }

    private static MainActivity myActivity;

    public  void setInstance(MainActivity instance){
        myActivity = instance;
    }

    public static MainActivity getInstance(){
        return myActivity;
    }
    public static void addActivity(Activity activity){
        activityArrayList.add(activity);
    }
    public static void removActivity(Activity activity){
        activityArrayList.remove(activity);
    }
    //
    public static void finAll(){
        for (Activity activity : activityArrayList) {
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
