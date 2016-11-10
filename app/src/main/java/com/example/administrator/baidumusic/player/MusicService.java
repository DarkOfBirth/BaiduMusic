package com.example.administrator.baidumusic.player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.baidumusic.databean.MusicItemBean;
import com.example.administrator.baidumusic.messageevent.PlayMusicEvent;
import com.example.administrator.baidumusic.messageevent.PlayerDataEvent;
import com.example.administrator.baidumusic.messageevent.SongListEvent;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.DBTools;
import com.example.administrator.baidumusic.tools.GsonRequest;
import com.example.administrator.baidumusic.tools.SingleVolley;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private String path = "";
    private String lastSongId;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override

            public void onCompletion(MediaPlayer mp) {
                Log.d("MusicService", "完毕");
                mp.stop();
                mp.reset();
             playNextInner();



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


        path = item.getBitrate().getShow_link();
        if (mediaPlayer.isPlaying()) {

            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        mediaPlayer.setDataSource(path);
        mediaPlayer.prepare();
        mediaPlayer.start();



    }

    public void playNextInner() {
        DBTools.getInstance().queryMusicInfo(SongListEvent.class, new DBTools.OnQueryMusicInfo<SongListEvent>() {
            @Override
            public void OnQuery(ArrayList<SongListEvent> query) {
                Log.d("MainActivity", "query.size():" + query.size());
                for(int i = 0; i < query.size(); i ++){
                    int state = query.get(i).getState();
                    Log.d("MainActivity", "state:" + state);
                    if(AppValues.PLAY_STATE == state){
                        Log.d("MainActivity", "找到");
                        DBTools.getInstance().modifyMusicInfo(SongListEvent.class,
                                query.get(i).getSongId(),"state",AppValues.STOP_STATE);
                        DBTools.getInstance().modifyMusicInfo(SongListEvent.class,
                                query.get((i + 1) % query.size()).getSongId(),"state",AppValues.PLAY_STATE);
                        String musicUrl = AppValues.PLAY_SONG_HEAD +  query.get((i + 1) % query.size()).getSongId();
                        getMusicInfo(musicUrl);
                    }
                }

            }
        });


    }


    // 传值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayMusicEvent event) {
        //Log.d("MainActivity", event.getSongId());
        if (lastSongId == event.getSongId()){
            return;
        }
        lastSongId = event.getSongId();
        String musicUrl = AppValues.PLAY_SONG_HEAD +  event.getSongId();
        getMusicInfo(musicUrl);

        /* Do something */}
    // 得到音乐的详细信息
    private void getMusicInfo(String musicUrl) {
        GsonRequest<MusicItemBean> request = new GsonRequest<MusicItemBean>(MusicItemBean.class, musicUrl, new Response.Listener<MusicItemBean>() {
            @Override
            public void onResponse(MusicItemBean response) {
              //  addPlayList(response);
                try {
                    addPlayListInner(response);
                    EventBus.getDefault().post(new PlayerDataEvent(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // SetBottomPlayerData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingleVolley.getInstance().getRequestQueue().add(request);
    }

    public void playInner() {
        Toast.makeText(this, "恢复播放", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
    }

    public void playPreInner() {
    }

    public void pauseInner() {
        mediaPlayer.pause();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}