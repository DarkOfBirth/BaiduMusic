package com.example.administrator.baidumusic.music.video;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.GsonRequest;
import com.example.administrator.baidumusic.tools.SingleVolley;

import static com.example.administrator.baidumusic.tools.AppValues.VIDEO;

/**
 * Created by dllo on 16/10/21.
 */
public class VideoFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView rvVideo;
    private VideoAdapter adapter;
    private TextView lastVideo;
    private TextView hotVideo;

    @Override
    protected int getLayout() {
        return R.layout.frag_video_music;
    }

    @Override
    protected void initView() {
        rvVideo = bindView(R.id.rv_video);
        lastVideo = bindView(R.id.last_video);
        hotVideo = bindView(R.id.hot_video);


        lastVideo.setOnClickListener(this);
        hotVideo.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        adapter = new VideoAdapter(mContext);

        rvVideo.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(mContext,2);
        rvVideo.setLayoutManager(manager);
        updateData(VIDEO);

    }



    private void updateData(String url){
        GsonRequest<VideoBean> request = new GsonRequest<VideoBean>(VideoBean.class,url,
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hot_video:

                hotVideo.setTextColor(getResources().getColor(R.color.colorTextBlue));
                lastVideo.setTextColor(getResources().getColor(R.color.colorTextGray));
                Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
             String newUrl =   AppValues.VIDEO.replace("order=1","order=0");
                Log.d("VideoFragment", newUrl);
                updateData(newUrl);
                break;
            case R.id.last_video:
                lastVideo.setTextColor(getResources().getColor(R.color.colorTextBlue));
                hotVideo.setTextColor(getResources().getColor(R.color.colorTextGray));
                updateData(AppValues.VIDEO);
                break;
        }
    }
}
