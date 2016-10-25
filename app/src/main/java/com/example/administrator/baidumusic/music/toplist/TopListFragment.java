package com.example.administrator.baidumusic.music.toplist;

import android.util.Log;
import android.widget.ListView;

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
public class TopListFragment extends BaseFragment {

    private ListView lvTopList;

    @Override
    protected int getLayout() {
        return R.layout.toplist_music_frag;
    }

    @Override
    protected void initView() {
        lvTopList = bindView(R.id.lv_toplist);
    }

    @Override
    protected void initData() {
        final TopListAdapter adapter = new TopListAdapter(mContext);
        lvTopList.setAdapter(adapter);

        GsonRequest<TopListBean> request = new GsonRequest<TopListBean>(TopListBean.class, AppValues.TOP_LIST, new Response.Listener<TopListBean>() {
            @Override
            public void onResponse(TopListBean response) {
                Log.d("TopListFragment", "response:" + response);
                adapter.setBean(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingleVolley.getInstance().getRequestQueue().add(request);

    }


}
