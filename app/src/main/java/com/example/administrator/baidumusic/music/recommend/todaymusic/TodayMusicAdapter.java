package com.example.administrator.baidumusic.music.recommend.todaymusic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.music.recommend.RecommendBean;

/**
 * Created by Administrator on 2016/10/27.
 */

public class TodayMusicAdapter extends RecyclerView.Adapter<TodayMusicAdapter.MyViewHolder> {
    private Context context;
    private RecommendBean bean;
    public TodayMusicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_today_music,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        int total =bean.getResult().getRecsong().getResult().size();
        int num = 0;

        for (int i = 0; i < total ; i++) {
            if(bean.getResult().getRecsong().getResult().get(i).getLearn() == "0"){
                num++;
            }
        }
        return num;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
