package com.example.administrator.baidumusic.player;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.baidumusic.databean.MusicItemBean;
import com.example.administrator.baidumusic.messageevent.LrcEvent;
import com.example.administrator.baidumusic.messageevent.MusicProgressEvent;
import com.example.administrator.baidumusic.messageevent.PlayMusicEvent;
import com.example.administrator.baidumusic.messageevent.PlayerDataEvent;
import com.example.administrator.baidumusic.messageevent.ProgerssHandEvent;
import com.example.administrator.baidumusic.messageevent.SongListEvent;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.DBTools;
import com.example.administrator.baidumusic.tools.GsonRequest;
import com.example.administrator.baidumusic.tools.LrcRequest;
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
    private int duration;
    private int currentPosition;
    private String lrcStr;
    private MusicItemBean rePostData;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        mediaPlayer = new MediaPlayer();
        // 歌曲播放完毕的监听器
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override

            public void onCompletion(MediaPlayer mp) {
                Log.d("playPause", "完毕");
              //  mp.stop();
              //  mp.reset();
             playNextInner();
            }

        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayer.start();
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {

                sendProgress();
            }
        }).start();

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

        public MusicService getService(){
            return MusicService.this;
        }

        public void play() {
            playInner();

        }

        public void playNext() {
            playNextInner();

        }
        public void rePost(){
            rePostDataInner();
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

    private void rePostDataInner() {
        EventBus.getDefault().post(new PlayerDataEvent(rePostData));
    }

    //真正实现功能的方法
    public void addPlayListInner(List<MusicItemBean> items) {

    }

    public void addPlayListInner(MusicItemBean item) throws IOException {


        path = item.getBitrate().getShow_link();
        if (mediaPlayer.isPlaying()) {

            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        Log.d("path22", path);
         mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setDataSource(path);
        mediaPlayer.prepareAsync();

    }

    // 发送进度
    public void sendProgress()  {
        while (true){

            while (mediaPlayer.isPlaying()) {

                duration = mediaPlayer.getDuration();
                currentPosition = mediaPlayer.getCurrentPosition();

                EventBus.getDefault().post(new MusicProgressEvent(AppValues.PROGRESS_AUTO,duration, currentPosition));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // 下一曲播放
    public void playNextInner() {
        Log.d("MusicService111", "count" + this);
        DBTools.getInstance().queryMusicInfo(SongListEvent.class, new DBTools.OnQueryMusicInfo<SongListEvent>() {
            @Override
            public void OnQuery(ArrayList<SongListEvent> query) {
                Log.d("MusicService111", "query.size():" + query.size());
                for(int i = 0; i < query.size(); i ++){
                    int state = query.get(i).getState();
                    Log.d("MusicService111", "state:" + state);
                    if(AppValues.PLAY_STATE == state){
                        Log.d("MusicService111", "找到");
                        DBTools.getInstance().modifyMusicInfo(SongListEvent.class,
                                query.get(i).getSongId(),"state",AppValues.STOP_STATE);
                        DBTools.getInstance().modifyMusicInfo(SongListEvent.class,
                                query.get((i + 1) % query.size()).getSongId(),"state",AppValues.PLAY_STATE);
                        String musicUrl = AppValues.PLAY_SONG_HEAD +  query.get((i + 1) % query.size()).getSongId();
                        getMusicInfo(musicUrl);
                        break;
                    }
                }

            }
        });


    }

    // 接受进度
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProgerssHandEvent event) {


            // 手动拖动进度条
            Log.d("MusicService", "手动");
            int position = event.getPosition();
          //  pauseInner();
            mediaPlayer.seekTo(position);
          //  playInner();

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
                   // Log.d("MusicService", response.getSonginfo().getLrclink());
                    getLrcInfo(response.getSonginfo().getLrclink());
                  rePostData =  response;
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

    /**
     * 获取歌词的信息
     * @param lrcUrl 歌词的URL
     */
    private void getLrcInfo(final String lrcUrl) {
        Log.d("Sysout-url", lrcUrl);
        LrcRequest lrcRequest = new LrcRequest(lrcUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Sysout-response", response);

                lrcStr = response;
                EventBus.getDefault().postSticky(new LrcEvent(lrcStr));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Sysout-error", "" + error.getMessage());
            }
        });

        SingleVolley.getInstance().getRequestQueue().add(lrcRequest);
    }

    public void playInner() {
        Toast.makeText(this, "恢复播放", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
    }

    public void playPreInner() {

        Log.d("MusicService111", "count" + this);
        DBTools.getInstance().queryMusicInfo(SongListEvent.class, new DBTools.OnQueryMusicInfo<SongListEvent>() {
            @Override
            public void OnQuery(ArrayList<SongListEvent> query) {
                Log.d("MusicService111", "query.size():" + query.size());
                for(int i = 0; i < query.size(); i ++){
                    int state = query.get(i).getState();
                    Log.d("MusicService111", "state:" + state);
                    if(AppValues.PLAY_STATE == state){
                        Log.d("MusicService111", "找到");
                        DBTools.getInstance().modifyMusicInfo(SongListEvent.class,
                                query.get(i).getSongId(),"state",AppValues.STOP_STATE);
                        DBTools.getInstance().modifyMusicInfo(SongListEvent.class,
                                query.get((i - 1) % query.size()).getSongId(),"state",AppValues.PLAY_STATE);
                        String musicUrl = AppValues.PLAY_SONG_HEAD +  query.get((i - 1 + query.size()) % query.size()).getSongId();
                        getMusicInfo(musicUrl);
                        break;
                    }
                }

            }
        });
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