package com.example.administrator.baidumusic.music.recommend;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
public class RecommendFragment extends BaseFragment {
    private RecyclerView rvRecommend;

    @Override
    protected int getLayout() {
        return R.layout.frag_recommend_music;
    }

    @Override
    protected void initView() {
        rvRecommend = bindView(R.id.rv_recommend);
    }

    @Override
    protected void initData() {
        final RecommendAdapter recommendAdapter = new RecommendAdapter(mContext);
        rvRecommend.setAdapter(recommendAdapter);


        GsonRequest<RecommendBean> request = new GsonRequest<>(RecommendBean.class, AppValues.RECOMMEND,
                new Response.Listener<RecommendBean>() {
                    @Override
                    public void onResponse(RecommendBean response) {
                        Log.d("RecommendFragment", "正确");
                        Log.d("RecommendFragment", "response:" + response);
                        recommendAdapter.setRecommendBean(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("RecommendFragment", "错误");

            }
        });
        SingleVolley.getInstance().getRequestQueue().add(request);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvRecommend.setLayoutManager(manager);
    }
}
