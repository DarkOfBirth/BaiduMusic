package com.example.administrator.baidumusic.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.live.LiveFragment;
import com.example.administrator.baidumusic.mine.MineFragment;
import com.example.administrator.baidumusic.music.MusicFragment;
import com.example.administrator.baidumusic.trend.TrendFragment;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/21.
 */
public class MainFragment extends BaseFragment {

    private ViewPager vpHome;
    private ImageView ivHomeMore;
    private ImageView ivHomeSearch;
    private TabLayout tabHome;
    private ArrayList<Fragment> fragmentArrayList;
    private FragmentManager manager;

    @Override
    protected int getLayout() {
        return R.layout.main_frag;
    }
    @Override
    protected void initView() {
        vpHome = bindView(R.id.vp_home);
        ivHomeMore = bindView(R.id.iv_more_home);
        ivHomeSearch = bindView(R.id.iv_search_home);
        tabHome = bindView(R.id.tab_home);
    }


    @Override
    protected void initData() {
        manager = getChildFragmentManager();
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new MineFragment());
        fragmentArrayList.add(new MusicFragment());
        fragmentArrayList.add(new TrendFragment());
        fragmentArrayList.add(new LiveFragment());

        MainFragmentPagerAdapter mainFragAdapter = new MainFragmentPagerAdapter(manager);
        mainFragAdapter.setfragmentArrayList(fragmentArrayList);
        tabHome.setupWithViewPager(vpHome);
        vpHome.setAdapter(mainFragAdapter);

    }
}
