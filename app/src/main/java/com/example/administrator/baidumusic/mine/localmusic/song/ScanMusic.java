package com.example.administrator.baidumusic.mine.localmusic.song;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/31.
 */
public class ScanMusic {
    public  ArrayList<LocalMusicBean>  query(Context mContext){
    ArrayList<LocalMusicBean> arrayList = new ArrayList<>();

        Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media
                .EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if(cursor != null) {


            LocalMusicBean localMusicBean = null;
            while (cursor.moveToNext()){
                localMusicBean = new LocalMusicBean();
                String musicName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String musicSinger = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String musicPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String musicAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));

                localMusicBean.setName(musicName);
                localMusicBean.setArtist(musicSinger);
                localMusicBean.setPath(musicPath);
                localMusicBean.setAlbum(musicAlbum);

                arrayList.add(localMusicBean);
            }
            cursor.close();
        }

        return arrayList;

    }



}
