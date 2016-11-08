package com.example.administrator.baidumusic.tools;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.administrator.baidumusic.base.MyApp;
import com.example.administrator.baidumusic.messageevent.SongListEvent;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Administrator on 2016/10/28.
 */

public class DBTools {

    // 此方法为 恶汉式 单例
    // LiteOrm 在使用的时候要使用单例
    private static DBTools dbTools = new DBTools();
    private final ExecutorService mThreadPool;
    private LiteOrm mLiteOrm;

    private Handler mHandler;

    private DBTools() {


        mLiteOrm = LiteOrm.newSingleInstance(MyApp.getmContext(), "BaiduMusic.db");
        // 确保在主线程中执行
        mHandler = new Handler(Looper.getMainLooper());
        int core = Runtime.getRuntime().availableProcessors();
        mThreadPool = Executors.newFixedThreadPool(core + 1);

    }

    public static DBTools getInstance() {
        return dbTools;
    }

    /**
     * 数据插入
     */
    public <T> void insertMusciInfo(T t) {

        mLiteOrm.insert(t);
    }

    public <T> void insertMusciInfo(List<T> ts) {
        mLiteOrm.insert(ts);
    }

    // 删除
    public <T> void deleteAllMusicInfo(Class<T> tClass) {
        mLiteOrm.deleteAll(tClass);
    }

    // 删除单个
    public <T> void deleteOneMusicInfo(Class<SongListEvent> tClass, String songId) {
        int count = mLiteOrm.delete(new WhereBuilder(tClass).where("songId" + "=?", songId));
        Log.d("DBTools", "单个删除count:" + count);
    }


    /**
     * 修改播放状态
     *
     * @param tClass 表名, 即类名
     * @param songId 歌曲的id 用来唯一值的匹配
     * @param column 要修改的列名
     * @param value  之后修改的值
     * @param <T>
     */
    public <T> void modifyMusicInfo(Class<T> tClass, String songId, String column, int value) {


        ColumnsValue columnsValue = new ColumnsValue(new String[]{column}, new Integer[]{value});
        if(songId.equals("")){
            int count = mLiteOrm.update(new WhereBuilder(tClass), columnsValue, ConflictAlgorithm.Replace);
        }
        else {

        int count = mLiteOrm.update(new WhereBuilder(tClass).where("songId" + "=?", songId), columnsValue, ConflictAlgorithm.Replace);
        Log.d("DBTools", "count:" + count);
        }
    }
    // 查询
    public <T> void queryMusicInfo(Class<T> tClass, final OnQueryMusicInfo<T> mOnQueryMusicInfo) {

        mThreadPool.execute(new QueryRunnable(mOnQueryMusicInfo, tClass));
    }
    // 一个传值的接口

    public interface OnQueryMusicInfo<T> {
        void OnQuery(ArrayList<T> query);
    }

    private class QueryRunnable<T> implements Runnable {
        private OnQueryMusicInfo onQueryMusicInfo;
        private Class mClass;


        public QueryRunnable(OnQueryMusicInfo mOnQueryMusicInfo, Class<T> tClass) {
            this.onQueryMusicInfo = mOnQueryMusicInfo;
            this.mClass = tClass;
        }

        @Override
        public void run() {
            ArrayList<T> query = mLiteOrm.query(mClass);
            mHandler.post(new CallBacks(onQueryMusicInfo, query));
        }
    }

    // 将值发回main
    private class CallBacks<T> implements Runnable {
        private OnQueryMusicInfo onQueryPersonValue;
        private ArrayList<T> beanArrayList;

        public CallBacks(OnQueryMusicInfo onQueryPersonValue,
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
