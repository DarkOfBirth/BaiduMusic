package com.example.administrator.baidumusic.music.musiclist;

import android.support.v7.widget.GridLayoutManager;
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
public class MusicListFragment extends BaseFragment {
    private RecyclerView musicList;
    @Override
    protected int getLayout() {
        return R.layout.musiclist_music_frag;
    }

    @Override
    protected void initView() {
        musicList = bindView(R.id.rv_music_list);
    }

    @Override
    protected void initData() {
        final MusicListAdapter adapter = new MusicListAdapter(mContext);
        musicList.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(mContext,2);
        musicList.setLayoutManager(manager);
        GsonRequest<MusicListBean> request = new GsonRequest<MusicListBean>(MusicListBean.class, AppValues.MUSIC_LIST,
                new Response.Listener<MusicListBean>() {
            @Override
            public void onResponse(MusicListBean response) {
                Log.d("MusicListFragment", "response.getContent().size():" + response.getContent().size());
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
