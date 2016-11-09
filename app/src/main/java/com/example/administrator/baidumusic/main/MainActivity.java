package com.example.administrator.baidumusic.main;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseActivity;
import com.example.administrator.baidumusic.databean.MusicItemBean;
import com.example.administrator.baidumusic.messageevent.PlayerDataEvent;
import com.example.administrator.baidumusic.player.MusicService;
import com.example.administrator.baidumusic.player.PlayerActivity;
import com.example.administrator.baidumusic.tools.SingleVolley;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FragmentTransaction transaction;
    private View bottomView;
    private ImageButton songList; // 右下角的列表按钮
    private ImageButton next;// 下一首
    private TextView title, author;
    private ImageView pic;
    private boolean flag; // 歌曲列表是否弹出
    private LinearLayout playerLayout; // 面板, 点击弹出整个播放器详情
    Intent intent;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        EventBus.getDefault().register(this);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        bottomView = bindView(R.id.player_bottom);
        songList = bindView(bottomView, R.id.ib_music_list);
        pic = bindView(bottomView, R.id.iv_music_pic);
        title = bindView(bottomView, R.id.tv_music_name);
        author = bindView(bottomView, R.id.tv_music_singer);
        next = bindView(bottomView, R.id.ib_music_next);
        playerLayout = bindView(bottomView, R.id.ll_music_bottom);
        songList.setOnClickListener(this);
        next.setOnClickListener(this);
        playerLayout.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        Fragment mainFragment = new MainFragment();
        jumpFragment(mainFragment);
//        FragmentManager manager = getSupportFragmentManager();
//        transaction = manager.beginTransaction();
//        transaction.add(R.id.main_fl,mainFragment);
//        transaction.commit();

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
        SetBottomPlayerData(event.getMusicItemBean());

        /* Do something */
    }

    /**
     * 给下面的播放器界面赋值
     *
     * @param response
     */
    private void SetBottomPlayerData(MusicItemBean response) {
        String titleString = response.getSonginfo().getTitle();
        String authorString = response.getSonginfo().getAuthor();
        String picUrl = response.getSonginfo().getPic_small();
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
                mMusicService.playNext();
                break;
            case R.id.ll_music_bottom:
                Intent intent = new Intent(this, PlayerActivity.class);
                startActivity(intent);
                break;

        }
    }


    public <T extends Fragment> void jumpFragment(T t) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_fl, t);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
