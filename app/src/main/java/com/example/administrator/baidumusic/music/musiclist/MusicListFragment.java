package com.example.administrator.baidumusic.music.musiclist;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.music.musiclist.musiclistdetail.MusicListDetailFragment;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.GsonRequest;
import com.example.administrator.baidumusic.tools.SingleVolley;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by dllo on 16/10/21.
 */
public class MusicListFragment extends BaseFragment implements View.OnClickListener {
    private PullLoadMoreRecyclerView musicList;
    private MusicListAdapter adapter;
    private TextView hotMusicList;
    private TextView lastMusicList;

    @Override
    protected int getLayout() {
        return R.layout.frag_musiclist_music;
    }

    @Override
    protected void initView() {
        musicList = bindView(R.id.rv_music_list);
        hotMusicList = bindView(R.id.hot_music_list);
        lastMusicList = bindView(R.id.last_music_list);
        hotMusicList.setOnClickListener(this);
        lastMusicList.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        adapter = new MusicListAdapter(mContext);
        musicList.setAdapter(adapter);
        musicList.setGridLayout(2);

        upData(AppValues.MUSIC_LIST);

        musicList.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                musicList.setPullRefreshEnable(false);
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(mContext, "上拉加载", Toast.LENGTH_SHORT).show();
                musicList.setPullLoadMoreCompleted();
            }
        });

        adapter.setmOnItemClickListener(new MusicListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String song_list) {
                    MusicListDetailFragment fragment = new MusicListDetailFragment();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_fl, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }


    /**
     * 更新数据
     *
     * @param url 地址
     */
    private void upData(String url) {
        GsonRequest<MusicListBean> request = new GsonRequest<MusicListBean>(MusicListBean.class, url,
                new Response.Listener<MusicListBean>() {
                    @Override
                    public void onResponse(MusicListBean response) {
                        //  Log.d("MusicListFragment", "response.getContent().size():" + response.getContent().size());

                        Log.d("MusicListFragment", "response:" + response);
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
        switch (v.getId()) {
            case R.id.hot_music_list:
                hotMusicList.setTextColor(getResources().getColor(R.color.colorTextBlue));
                lastMusicList.setTextColor(getResources().getColor(R.color.colorTextGray));
                upData(AppValues.MUSIC_LIST);
                break;
            case R.id.last_music_list:
                lastMusicList.setTextColor(getResources().getColor(R.color.colorTextBlue));
                hotMusicList.setTextColor(getResources().getColor(R.color.colorTextGray));
                upData(AppValues.MUSIC_LIST_LAST);
                break;

        }
    }
}
