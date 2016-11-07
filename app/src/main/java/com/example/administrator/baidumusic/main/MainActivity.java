package com.example.administrator.baidumusic.main;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseActivity;
import com.example.administrator.baidumusic.databean.MusicItemBean;
import com.example.administrator.baidumusic.messageevent.PlayMusicEvent;
import com.example.administrator.baidumusic.player.MusicService;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.GsonRequest;
import com.example.administrator.baidumusic.tools.SingleVolley;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FragmentTransaction transaction;
    private View bottomView;
    private ImageButton songList;
    private boolean flag; // 歌曲列表是否弹出
    Intent intent ;
    private MusicService.MusicServiceIBinder mMusicService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //绑定成功后，取得MusicSercice提供的接口
            mMusicService = (MusicService.MusicServiceIBinder) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        intent = new Intent(this,MusicService.class);
        startService(intent);
        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

        FragmentManager manager = getSupportFragmentManager();

        transaction = manager.beginTransaction();
        Fragment mainFragment = new MainFragment();
        transaction.add(R.id.main_fl,mainFragment);

        transaction.commit();

    }

    @Override
    protected void initViews() {
            bottomView = bindView(R.id.player_bottom);
            songList = bindView(bottomView,R.id.ib_music_list);

            songList.setOnClickListener(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
    // 传值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayMusicEvent event) {
        Log.d("MainActivity", event.getSongId());
        String musicUrl = AppValues.PLAY_SONG_HEAD +  event.getSongId();
        getMusicInfo(musicUrl);

        /* Do something */}

    private void getMusicInfo(String musicUrl) {
        GsonRequest<MusicItemBean> request = new GsonRequest<MusicItemBean>(MusicItemBean.class, musicUrl, new Response.Listener<MusicItemBean>() {
            @Override
            public void onResponse(MusicItemBean response) {
                mMusicService.addPlayList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingleVolley.getInstance().getRequestQueue().add(request);
    }


    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // 列表

            case R.id.ib_music_list:
                if(!flag){
                SongListFragment fragment = new SongListFragment();
                    jumpFragment(fragment);
                    flag = !flag;


                }else {
                    getSupportFragmentManager().popBackStack();
                    flag = !flag;
                }
                break;
        }
    }
    public <T extends Fragment>void  jumpFragment(T t){
       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_fl,t);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
