package com.example.administrator.baidumusic.music.video.videodetail;

import android.webkit.WebView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;

/**
 * Created by Administrator on 2016/11/14.
 */

public class VideoDetailFragment extends BaseFragment {
    private WebView webView;
    @Override
    protected int getLayout() {
        return R.layout.frag_video_detail;
    }

    @Override
    protected void initView() {
       webView = bindView(R.id.web_video);

    }

    @Override
    protected void initData() {
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl( getArguments().getString("url"));

    }


}
