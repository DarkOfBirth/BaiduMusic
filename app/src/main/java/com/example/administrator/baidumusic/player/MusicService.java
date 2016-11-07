package com.example.administrator.baidumusic.player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.baidumusic.databean.MusicItemBean;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override

            public void onCompletion(MediaPlayer mp) {

                // TODO Auto-generated method stub

                //current++;

               // prepareAndPlay(current);

            }

        });
    }

    public class MusicServiceIBinder extends Binder {

        public void addPlayList(List<MusicItemBean> items) {
            addPlayListInner(items);
        }

        public void addPlayList(MusicItemBean item) {
            try {
                addPlayListInner(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void play() {
            playInner();

        }

        public void playNext() {
            playNextInner();

        }

        public void playPre() {
            playPreInner();
        }

        public void pause() {
            pauseInner();
        }

        public void seekTo(int pos) {
            seekToInner(pos);
        }

        public void registerOnStateChangeListener(OnStateChangeListenr l) {
            registerOnStateChangeListenerInner(l);

        }

        public void unregisterOnStateChangeListener(OnStateChangeListenr l) {
            unregisterOnStateChangeListenerInner(l);
        }

        public MusicItemBean getCurrentMusic() {
            return getCurrentMusicInner();
        }

        public boolean isPlaying() {
            return isPlayingInner();
        }

        public List<MusicItemBean> getPlayList() {
            return null;
        }

    }

    //真正实现功能的方法
    public void addPlayListInner(List<MusicItemBean> items) {

    }

    public void addPlayListInner(MusicItemBean item) throws IOException {
        String path = item.getBitrate().getShow_link();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        Log.d("MusicService", path);
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepare();
        mediaPlayer.start();
        Log.d("MusicService", "mediaPlayer.isPlaying():" + mediaPlayer.isPlaying());


    }

    public void playNextInner() {
    }

    public void playInner() {

    }

    public void playPreInner() {
    }

    public void pauseInner() {

    }

    public void seekToInner(int pos) {

    }

    public void registerOnStateChangeListenerInner(OnStateChangeListenr l) {

    }

    public void unregisterOnStateChangeListenerInner(OnStateChangeListenr l) {

    }

    public MusicItemBean getCurrentMusicInner() {
        return null;
    }

    public boolean isPlayingInner() {
        return false;
    }

    //创建Binder实例
    private final IBinder mBinder = new MusicServiceIBinder();

    @Override
    public IBinder onBind(Intent intent) {
        //当组件bindService()之后，将这个Binder返回给组件使用
        return mBinder;
    }


}