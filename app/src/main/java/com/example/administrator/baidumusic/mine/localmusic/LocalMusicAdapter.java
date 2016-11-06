package com.example.administrator.baidumusic.mine.localmusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/11/1.
 */
public class LocalMusicAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    String[] title = {"歌曲", "文件夹", "歌手", "专辑"};
    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
    }

    public LocalMusicAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
