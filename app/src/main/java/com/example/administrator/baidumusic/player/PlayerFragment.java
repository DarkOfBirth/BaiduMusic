package com.example.administrator.baidumusic.player;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.effect.ZoomOutPageTransformer;
import com.example.administrator.baidumusic.main.MainActivity;
import com.example.administrator.baidumusic.main.SongListFragment;
import com.example.administrator.baidumusic.messageevent.MusicProgressEvent;
import com.example.administrator.baidumusic.messageevent.ProgerssHandEvent;
import com.example.administrator.baidumusic.tools.AppValues;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    private SeekBar seekBar;
    private TextView current, total;

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

        play.setOnClickListener(this);
        playNext.setOnClickListener(this);
        playPrevious.setOnClickListener(this);
        list_player.setOnClickListener(this);
        back.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                   // EventBus.getDefault().post(new MusicProgressEvent(1,duration, 0));
                    EventBus.getDefault().post(new ProgerssHandEvent(i));
                    //seekBar.setProgress(i);
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
            case R.id.next_player:
                ((MainActivity) getActivity()).playNext();
                break;
            case R.id.previous_player:
                ((MainActivity) getActivity()).playPre();
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


}
