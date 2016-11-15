package com.example.administrator.baidumusic.main;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseActivity;
import com.example.administrator.baidumusic.base.MyApp;
import com.example.administrator.baidumusic.databean.MusicItemBean;
import com.example.administrator.baidumusic.messageevent.PlayerDataEvent;
import com.example.administrator.baidumusic.database.SongListEvent;
import com.example.administrator.baidumusic.player.MusicService;
import com.example.administrator.baidumusic.player.PlayerFragment;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.DBTools;
import com.example.administrator.baidumusic.tools.SingleVolley;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FragmentTransaction transaction;
    private View bottomView;
    private ImageButton songList; // 右下角的列表按钮
    private ImageButton next;// 下一首
    private ImageButton play;
    private TextView title, author;
    private ImageView pic;
    private boolean flag; // 歌曲列表是否弹出
    private LinearLayout playerLayout; // 面板, 点击弹出整个播放器详情
    private boolean isPlay = false;
    Intent intent;
    private MusicItemBean musicItemBean = null;
    private MusicService mService;
    private MusicService.MusicServiceIBinder mMusicService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //绑定成功后，取得MusicSercice提供的接口
            mMusicService = (MusicService.MusicServiceIBinder) service;
           // mService = ((MusicService.MusicServiceIBinder) service).getService();
            mMusicService.rePost();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private String songId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ((MyApp)getApplication()).setInstance(this);
        intent = new Intent(this, MusicService.class);
        if (mMusicService == null) {

            startService(intent);
            bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();



    }

    @Override
    protected void onResume() {
        super.onResume();
        // 执行
        Log.d("back", "onResume");
        bottomView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("back", "onStart");
        Log.d("MainActivity", "mMusicService:" + mMusicService);
        if (mMusicService != null) {
            mMusicService.rePost();

        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    // 显示bottom
    public void showBottom() {
        bottomView.setVisibility(View.VISIBLE);
    }
    public void unShowBottom(){
        bottomView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initViews() {

        bottomView = bindView(R.id.player_bottom);
        songList = bindView(bottomView, R.id.ib_music_list);
        pic = bindView(bottomView, R.id.iv_music_pic);
        title = bindView(bottomView, R.id.tv_music_name);
        author = bindView(bottomView, R.id.tv_music_singer);
        next = bindView(bottomView, R.id.ib_music_next);
        play = bindView(bottomView, R.id.ib_music_play);
        playerLayout = bindView(bottomView, R.id.ll_music_bottom);


        play.setOnClickListener(this);
        songList.setOnClickListener(this);
        next.setOnClickListener(this);
        playerLayout.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        Fragment mainFragment = new MainFragment();
//        jumpFragment(mainFragment);
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.main_fl, mainFragment);
        transaction.commit();

    }


    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayerDataEvent event) {
        setBottomPlayerData(event.getMusicItemBean());
        musicItemBean = event.getMusicItemBean();
        isPlay = true;
        songId = musicItemBean.getSonginfo().getSong_id();
        /* Do something */
    }

    /**
     * 给下面的播放器界面赋值
     *
     * @param response
     */
    private void setBottomPlayerData(MusicItemBean response) {
        Log.d("MainActivity", "response:" + response);

        String titleString = response.getSonginfo().getTitle();
        String authorString = response.getSonginfo().getAuthor();
        String picUrl = response.getSonginfo().getPic_small();
        play.setBackgroundResource(R.mipmap.bt_minibar_pause_normal);
        title.setText(titleString);
        author.setText(authorString);

        SingleVolley.getInstance().getImage(picUrl, pic);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 列表
            case R.id.ib_music_list:
                if (!flag) {
                    SongListFragment fragment = new SongListFragment();
                    jumpFragment(fragment);
                    flag = !flag;
                } else {
                    getSupportFragmentManager().popBackStack();
                    flag = !flag;
                }
                break;
            // 下一首歌曲
            case R.id.ib_music_next:
                Toast.makeText(this, "下一首", Toast.LENGTH_SHORT).show();
                playNext();

                break;
            // 点击播放器
            case R.id.ll_music_bottom:
                bottomView.setVisibility(View.INVISIBLE);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundle", musicItemBean);
                PlayerFragment playerFragment = new PlayerFragment();
                playerFragment.setArguments(bundle);
                jumpFragment(playerFragment);
                break;
            // 播放暂停按钮
            case R.id.ib_music_play:


                playOrPause();
                // pause


                break;

        }
    }

    public void playPre() {
        mMusicService.playPre();
    }

    /**
     * 下一首
     */
    public void playNext() {
        mMusicService.playNext();
    }

    /**
     * 播放暂停功能
     */
    public void playOrPause() {
        if (isPlay) {
            Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show();
            DBTools.getInstance().modifyMusicInfo(SongListEvent.class, songId, "state", AppValues.PAUSE_STATE);
            play.setBackgroundResource(R.mipmap.bt_minibar_play_normal);
            mMusicService.pause();
            isPlay = !isPlay;

        } else {
            // play
            DBTools.getInstance().modifyMusicInfo(SongListEvent.class, songId, "state", AppValues.PLAY_STATE);
            play.setBackgroundResource(R.mipmap.bt_minibar_pause_normal);
            mMusicService.play();
            isPlay = !isPlay;
        }
    }

    /**
     * 跳转fragment 的通用方法
     *
     * @param t
     * @param <T>
     */
    public <T extends Fragment> void jumpFragment(T t) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_fl, t);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public <T extends Fragment> void jumpFragment(T t, int enterAnim, int exitAnim) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterAnim, exitAnim);
        transaction.add(R.id.main_fl, t);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void modeChange(){
        mMusicService.modeChange();
    }


}
