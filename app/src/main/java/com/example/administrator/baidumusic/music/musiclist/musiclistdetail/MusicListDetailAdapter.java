package com.example.administrator.baidumusic.music.musiclist.musiclistdetail;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.databean.MusicListDetailBean;
import com.example.administrator.baidumusic.tools.CommonVH;

/**
 * Created by Administrator on 2016/11/6.
 */

public class MusicListDetailAdapter extends RecyclerView.Adapter<CommonVH> {
    private MusicListDetailBean musicListDetailBean;
    private MusicListDetailBean.ContentBean bean;

    public void setMusicListDetailBean(MusicListDetailBean musicListDetailBean) {
        this.musicListDetailBean = musicListDetailBean;
        notifyDataSetChanged();
    }

    @Override
    public CommonVH onCreateViewHolder(ViewGroup parent, int viewType) {

        return CommonVH.getViewHolder(parent, R.layout.item_musiclist_detail);
    }

    @Override
    public void onBindViewHolder(CommonVH holder, int position) {
        bean = musicListDetailBean.getContent().get(position);
        holder.setText(R.id.title_item_musiclist_detail, bean.getTitle())
                .setText(R.id.author_item_musiclist_detail, bean.getAuthor())
                .setImageVisible(R.id.mv_item_musiclist_detail, bean.getHas_mv_mobile() == 1 ? View.VISIBLE : View.INVISIBLE).
                setImageVisible(R.id.mike_item_musiclist_detail, bean.getLearn() == 1 ? View.VISIBLE : View.INVISIBLE);


    }

    @Override
    public int getItemCount() {
    int count = 0;
        try{

        count = musicListDetailBean.getContent().size();
        }catch (NullPointerException e) {
            Log.d("MusicListDetailAdapter", "e:" + e);
        }
        return count;
    }
}
