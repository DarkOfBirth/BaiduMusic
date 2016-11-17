package com.example.administrator.baidumusic.music.toplist;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.databean.TopListBean;
import com.example.administrator.baidumusic.music.toplist.toplistdetail.TopListDetailFragment;
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
        return R.layout.frag_toplist_music;
    }

    @Override
    protected void initView() {
        lvTopList = bindView(R.id.lv_toplist);
    }

    @Override
    protected void initData() {
        final TopListAdapter adapter = new TopListAdapter(mContext);
        lvTopList.setAdapter(adapter);


        lvTopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Bundle bundle = adapter.getData(i);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                TopListDetailFragment fragment = new TopListDetailFragment();
                fragment.setArguments(bundle);
                transaction.add(R.id.main_fl,fragment);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

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
        SingleVolley.getInstance().getmRequestQueue().add(request);

    }


}
