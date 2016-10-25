package com.example.administrator.baidumusic.music.video;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;

/**
 * Created by dllo on 16/10/21.
 */
public class VideoFragment extends BaseFragment {
    private RecyclerView video;
    @Override
    protected int getLayout() {
        return R.layout.video_music_frag;
    }

    @Override
    protected void initView() {
        video = bindView(R.id.rv_video);
    }

    @Override
    protected void initData() {

    }
}
