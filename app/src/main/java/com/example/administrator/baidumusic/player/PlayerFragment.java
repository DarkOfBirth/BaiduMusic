package com.example.administrator.baidumusic.player;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.base.MyApp;
import com.example.administrator.baidumusic.databean.MusicItemBean;
import com.example.administrator.baidumusic.effect.ZoomOutPageTransformer;
import com.example.administrator.baidumusic.main.MainActivity;
import com.example.administrator.baidumusic.main.SongListFragment;
import com.example.administrator.baidumusic.messageevent.ModeEvent;
import com.example.administrator.baidumusic.messageevent.MusicProgressEvent;
import com.example.administrator.baidumusic.messageevent.PlayerDataEvent;
import com.example.administrator.baidumusic.messageevent.ProgerssHandEvent;
import com.example.administrator.baidumusic.tools.AppValues;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by Administrator on 2016/11/8.
 */

public class PlayerFragment extends BaseFragment implements View.OnClickListener {
    private ImageView back;
    private ViewPager vp;
    private List<Fragment> fragmentList;
    private ImageView list_player;
    private ImageView play;
    private ImageView playNext;
    private ImageView playPrevious;
    private ImageView share;
    private ImageView playMode;
    private SeekBar seekBar;
    private TextView current, total;
    private MusicItemBean musicItemBean;
    private int[] modePic = {R.mipmap.bt_playpage_roundsingle_press_new, R.mipmap.bt_playpage_loop_press_new,
            R.mipmap.bt_playpage_order_press_new, R.mipmap.bt_playpage_random_press_new};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EventBus.getDefault().register(PlayerFragment.this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(PlayerFragment.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_player;
    }

    @Override
    protected void initView() {

        back = bindView(R.id.back_player);
        vp = bindView(R.id.vp_player);
        list_player = bindView(R.id.list_player);
        play = bindView(R.id.play_pause_player);
        seekBar = bindView(R.id.seekBar_player);
        current = bindView(R.id.current_time_player);
        total = bindView(R.id.total_time_player);
        playNext = bindView(R.id.next_player);
        playPrevious = bindView(R.id.previous_player);
        share = bindView(R.id.share_player);
        playMode = bindView(R.id.circle_mode_player);


        playMode.setOnClickListener(this);
        share.setOnClickListener(this);
        play.setOnClickListener(this);
        playNext.setOnClickListener(this);
        playPrevious.setOnClickListener(this);
        list_player.setOnClickListener(this);
        back.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    EventBus.getDefault().post(new ProgerssHandEvent(i));
                } else {
                    seekBar.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayerDataEvent event) {
        musicItemBean = event.getMusicItemBean();
        share.setClickable(true);
        /* Do something */
    }

    @Override
    protected void initData() {


        fragmentList = new ArrayList<>();


        Bundle bundle = getArguments();


        LeftFragment leftFragment = new LeftFragment();
        CenterFragment centerFragment = new CenterFragment();
        RightFragment rightFragment = new RightFragment();
        leftFragment.setArguments(bundle);
        centerFragment.setArguments(bundle);
        centerFragment.setArguments(bundle);
        fragmentList.add(leftFragment);
        fragmentList.add(centerFragment);
        fragmentList.add(rightFragment);
        PlayerViewPagerAdapter adapter = new PlayerViewPagerAdapter(getChildFragmentManager());
        adapter.setFragmentList(fragmentList);
        vp.setPageTransformer(true, new ZoomOutPageTransformer());
        vp.setAdapter(adapter);
        vp.setCurrentItem(1);

        SharedPreferences sp = mContext.getSharedPreferences("circle", Context.MODE_PRIVATE);
        playMode.setImageResource(modePic[sp.getInt("mode", 1)]);


    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).unShowBottom();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 返回
            case R.id.back_player:

                getActivity().onBackPressed();

                break;
            // 列表
            case R.id.list_player:

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                SongListFragment fragment = new SongListFragment();
                transaction.add(R.id.player_fl, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            // 暂停
            case R.id.play_pause_player:

                ((MainActivity) getActivity()).playOrPause();


                break;
            // 下一曲
            case R.id.next_player:
                ((MainActivity) getActivity()).playNext();
                break;
            // 上一曲
            case R.id.previous_player:
                ((MainActivity) getActivity()).playPre();
                break;
            // 分享
            case R.id.share_player:
                Toast.makeText(mContext, "share", Toast.LENGTH_SHORT).show();
                Log.d("PlayerFragment", "musicItemBean:" + musicItemBean);
                if (musicItemBean != null) {

                    showShare(musicItemBean);
                }
                break;
            case R.id.circle_mode_player:
                ((MainActivity) getActivity()).modeChange();

                break;

        }
    }

    // 传值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MusicProgressEvent event) {
        if (AppValues.PROGRESS_AUTO == event.getType()) {

            seekBar.setMax(event.getDuration());
            seekBar.setProgress(event.getCurrentPosittion());
            current.setText(timeFormat(event.getCurrentPosittion() / 1000));
            total.setText(timeFormat(event.getDuration() / 1000));

        }
    }

    // 传值
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ModeEvent event) {
        playMode.setImageResource(modePic[event.getMode()]);
    }

    @Override
    public void onDestroyView() {
        ((MainActivity) getActivity()).showBottom();
        super.onDestroyView();
        EventBus.getDefault().unregister(PlayerFragment.this);

    }

    // 时间格式化
    // 将毫秒数, 转化为00:00
    private String timeFormat(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return ((min < 10) ? "0" + min : min + "") + ":" + ((sec < 10) ? "0" + sec : sec + "");

    }


    private void showShare(MusicItemBean musicItemBean) {
        ShareSDK.initSDK(MyApp.getmContext());

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();


        oks.setTitle("音乐分享");

        oks.setTitleUrl("http://music.baidu.com/share/" + musicItemBean.getSonginfo().getSong_id() + "?share=1&fr=app_android");
        oks.setText(musicItemBean.getSonginfo().getTitle() + "\n" + musicItemBean.getSonginfo().getAuthor());
        oks.setImageUrl(musicItemBean.getSonginfo().getPic_small());
        // oks.setTitleUrl("http://mob.com");
        // oks.setText("分享测试文--Text");
        // oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");


        oks.show(MyApp.getmContext());
    }


}
