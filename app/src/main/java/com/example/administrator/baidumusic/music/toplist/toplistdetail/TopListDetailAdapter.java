package com.example.administrator.baidumusic.music.toplist.toplistdetail;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.databean.TopListDetailBean;
import com.example.administrator.baidumusic.tools.CommonVH;

/**
 * Created by Administrator on 2016/11/7.
 */

public class TopListDetailAdapter extends RecyclerView.Adapter<CommonVH> {
    private TopListDetailBean topListDetailBean;
    private TopListDetailBean.SongListBean bean;

    public void setTopListDetailBean(TopListDetailBean topListDetailBean) {
        this.topListDetailBean = topListDetailBean;
        notifyDataSetChanged();
    }

    @Override
    public CommonVH onCreateViewHolder(ViewGroup parent, int viewType) {

        return CommonVH.getViewHolder(parent, R.layout.item_musiclist_detail);
    }

    @Override
    public void onBindViewHolder(CommonVH holder, int position) {
        bean = topListDetailBean.getSong_list().get(position);
        holder.setText(R.id.title_item_musiclist_detail, bean.getTitle())
                .setText(R.id.author_item_musiclist_detail, bean.getAuthor())
                .setImageVisible(R.id.mv_item_musiclist_detail, bean.getHas_mv_mobile() == 1 ? View.VISIBLE : View.INVISIBLE).
                setImageVisible(R.id.mike_item_musiclist_detail, bean.getLearn() == 1 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        try{

            count = topListDetailBean.getSong_list().size();
        }catch (NullPointerException e) {
            Log.d("MusicListDetailAdapter", "e:" + e);
        }
        return count;
    }


}
