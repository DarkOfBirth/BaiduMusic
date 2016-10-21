package com.example.administrator.baidumusic.music;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.music.ksong.KsongFragment;
import com.example.administrator.baidumusic.music.musiclist.MusicListFragment;
import com.example.administrator.baidumusic.music.recommend.RecommendFragment;
import com.example.administrator.baidumusic.music.toplist.TopListFragment;
import com.example.administrator.baidumusic.music.video.VideoFragment;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/21.
 */
public class MusicFragment extends BaseFragment {
    private TabLayout tabMusicFrag;
    private ViewPager vpMusicFrag;
    @Override
    protected int getLayout() {
        return R.layout.music_frag;
    }

    @Override
    protected void initView() {
        tabMusicFrag = bindView(R.id.tab_musicfrag);
        vpMusicFrag = bindView(R.id.vp_musicfrag);

    }

    @Override
    protected void initData() {
        FragmentManager musicFragmentManager = getChildFragmentManager();
        ArrayList<Fragment> musicFragArrayList = new ArrayList<>();
        musicFragArrayList.add(new RecommendFragment());
        musicFragArrayList.add(new MusicListFragment());
        musicFragArrayList.add(new TopListFragment());
        musicFragArrayList.add(new VideoFragment());
        musicFragArrayList.add(new KsongFragment());

        tabMusicFrag.setupWithViewPager(vpMusicFrag);
        MusicFragPagerAdapter musicFragPagerAdapter = new MusicFragPagerAdapter(musicFragmentManager);
        musicFragPagerAdapter.setFragmentArrayList(musicFragArrayList);
        vpMusicFrag.setAdapter(musicFragPagerAdapter);

    }
}
