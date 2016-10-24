package com.example.administrator.baidumusic.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
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
public class MainFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager vpHome;
    private ImageView ivHomeMore;
    private ImageView ivHomeSearch;
    private TabLayout tabHome;
    private ArrayList<Fragment> fragmentArrayList;



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
         FragmentManager manager;
        manager = getChildFragmentManager();
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new MineFragment());
        fragmentArrayList.add(new MusicFragment());
        fragmentArrayList.add(new TrendFragment());
        fragmentArrayList.add(new LiveFragment());

        MainFragmentPagerAdapter mainFragAdapter = new MainFragmentPagerAdapter(manager);
        mainFragAdapter.setfragmentArrayList(fragmentArrayList);
        vpHome.setAdapter(mainFragAdapter);
        tabHome.setupWithViewPager(vpHome);

        ivHomeMore.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more_home:
                FragmentManager manager = getChildFragmentManager();
                FragmentTransaction transaction =  manager.beginTransaction();
                MoreFragment moreFragment = new MoreFragment();
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.activity_main,null);
                FrameLayout frameLayout = (FrameLayout) view1.findViewById(R.id.main_fl);
                transaction.add(R.id.main_frag_frame,moreFragment,"1");
                transaction.addToBackStack("1");
                transaction.commit();

                break;
        }
    }

}
