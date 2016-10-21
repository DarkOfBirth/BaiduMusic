package com.example.administrator.baidumusic.music;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/21.
 */
public class MusicFragPagerAdapter extends FragmentPagerAdapter{
    private String[] titleMusic = {"推荐", "歌单", "榜单", "视频", "K歌"};
    private ArrayList<Fragment> fragmentArrayList ;

    public void setFragmentArrayList(ArrayList<Fragment> fragmentArrayList) {
        this.fragmentArrayList = fragmentArrayList;
    }

    public MusicFragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList == null ? 0 : fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleMusic[position];
    }
}
