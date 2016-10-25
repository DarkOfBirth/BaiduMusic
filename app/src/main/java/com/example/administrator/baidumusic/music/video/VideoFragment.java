package com.example.administrator.baidumusic.music.video;

import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.GsonRequest;
import com.example.administrator.baidumusic.tools.SingleVolley;

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
        final VideoAdapter adapter = new VideoAdapter(mContext);


        GsonRequest<VideoBean> request = new GsonRequest<VideoBean>(VideoBean.class, AppValues.VIDEO,
                new Response.Listener<VideoBean>() {
                    @Override
                    public void onResponse(VideoBean response) {
                        adapter.setListBeen(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingleVolley.getInstance().getRequestQueue().add(request);
    }
}
