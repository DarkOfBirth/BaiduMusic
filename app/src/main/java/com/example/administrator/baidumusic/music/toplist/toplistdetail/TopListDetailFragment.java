package com.example.administrator.baidumusic.music.toplist.toplistdetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.base.MyApp;
import com.example.administrator.baidumusic.databean.MusicListDetailBean;
import com.example.administrator.baidumusic.databean.TopListDetailBean;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.GsonRequest;
import com.example.administrator.baidumusic.tools.SingleVolley;

/**
 * Created by Administrator on 2016/11/6.
 */

public class TopListDetailFragment extends BaseFragment implements View.OnClickListener {
    private MusicListDetailBean result;
    private TextView title;
    private ImageView back;
    private ImageView center;
    private ImageView bg;
    private TextView num;
    private TextView listenNum;
    private View view;
    private RecyclerView rv;
    private TopListDetailAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.frag_toplist_detail;
    }

    @Override
    protected void initView() {
        title = bindView(R.id.title_toplist_detail);
        back = bindView(R.id.back_toplist_detail);
        view = bindView(R.id.content_toplist_detail);
        rv = bindView(view,R.id.rv_musiclist_detail);
        bg = bindView(R.id.bg_toplist_detail);

        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Bundle bundle =getArguments();
        String type = bundle.getString("type");
        String imgurl = bundle.getString("pic");
        String name = bundle.getString("name");
        title.setText(name);
        SingleVolley.getInstance().getImage(imgurl,bg);

        String newUrl = AppValues.MUSICTOP_SONGLIST_HEAD + type + AppValues.MUSICTOP_SONGLIST_END;
        adapter = new TopListDetailAdapter();
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApp.getmContext());
        rv.setLayoutManager(layoutManager);


        getData(newUrl);
    }


    private void getData(String newUrl) {
        GsonRequest<TopListDetailBean> request = new GsonRequest<>(TopListDetailBean.class, newUrl, new Response.Listener<TopListDetailBean>() {
          @Override
            public void onResponse(TopListDetailBean response) {
            setViewData(response);
            adapter.setTopListDetailBean(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingleVolley.getInstance().getmRequestQueue().add(request);
    }

    private void setViewData(TopListDetailBean response) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_toplist_detail:
                getActivity().onBackPressed();
                break;
        }
    }
}
