package com.example.administrator.baidumusic.tools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.administrator.baidumusic.base.MyApp;
import com.litesuits.orm.LiteOrm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * Created by Administrator on 2016/10/28.
 */

public class DBTools {

    // 此方法为 恶汉式 单例
    // LiteOrm 在使用的时候要使用单例
    private static DBTools dbTools = new DBTools();
    private final ExecutorService mThreadPool;
    private  LiteOrm mLiteOrm;

    private Handler mHandler;

    private DBTools(){


        mLiteOrm = LiteOrm.newSingleInstance(MyApp.getmContext(),"BaiduMusic.db");
        // 确保在主线程中执行
        mHandler = new Handler(Looper.getMainLooper());
        int core = Runtime.getRuntime().availableProcessors();
        mThreadPool = Executors.newFixedThreadPool(core + 1);

    }
    public static DBTools getInstance(){
        return dbTools;
    }

    /**
     * 数据插入
     *
     */
    public <T> void insertPerson(T t){
        mLiteOrm.insert(t);
    }

    public <T> void insertPerson(List<T> ts)
    {
        mLiteOrm.insert(ts);
    }

    public<T>  void queryPerson( Class<T> tClass,final OnQueryPersonValue<T> mOnqueryPersonValue){

        mThreadPool.execute(new QueryRunnable(mOnqueryPersonValue, tClass));
    }
    // 一个传值的接口

    public interface  OnQueryPersonValue<T>{
       void OnQuery(ArrayList<T> query);
    }

    private class QueryRunnable<T> implements Runnable{
        private OnQueryPersonValue onQueryPersonValue;
        private  Class mClass ;


        public QueryRunnable(OnQueryPersonValue mOnqueryPersonValue, Class<T> tClass) {
            this.onQueryPersonValue = mOnqueryPersonValue;
            this.mClass = tClass;
        }

        @Override
        public void run() {
           ArrayList<T> query = mLiteOrm.query(mClass);
            mHandler.post(new CallBacks(onQueryPersonValue,query));
        }
    }
// 将值发回main
  private  class CallBacks<T> implements Runnable{
      private OnQueryPersonValue onQueryPersonValue;
      private ArrayList<T> beanArrayList;
      public CallBacks(OnQueryPersonValue onQueryPersonValue,
                       List<T> beanArrayList) {
          this.onQueryPersonValue = onQueryPersonValue;
          this.beanArrayList = (ArrayList<T>) beanArrayList;
      }

      @Override
        public void run() {
            onQueryPersonValue.OnQuery(beanArrayList);
        }
    }
}
