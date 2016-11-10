package com.example.administrator.baidumusic.player;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.effect.ZoomOutPageTransformer;
import com.example.administrator.baidumusic.main.MainActivity;
import com.example.administrator.baidumusic.main.SongListFragment;

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


        play.setOnClickListener(this);
        list_player.setOnClickListener(this);
        back.setOnClickListener(this);

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
            case R.id.back_player:

                getActivity().onBackPressed();

                break;
            case R.id.list_player:

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                SongListFragment fragment = new SongListFragment();
                transaction.add(R.id.player_fl, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.play_pause_player:

                ((MainActivity)getActivity()).playOrPause();

                break;
        }
    }

    @Override
    public void onDestroyView() {
        ((MainActivity) getActivity()).showBottom();
        super.onDestroyView();

    }
}
