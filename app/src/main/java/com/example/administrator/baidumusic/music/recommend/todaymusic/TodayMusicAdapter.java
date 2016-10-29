package com.example.administrator.baidumusic.music.recommend.todaymusic;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
            // 有可能空指针
        for (int i = 0; i < total ; i++) {
            if(bean.getResult().getRecsong().getResult().get(i).getLearn() == "0"){
                num++;
            }
        }
        Log.d("TodayMusicAdapter", "num:" + num);
        return num;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView image;
        private CircleImageView play;
        private TextView title;
        private TextView author;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.iv_today_music);
            play = (CircleImageView) itemView.findViewById(R.id.iv_today_music_play);
            title = (TextView) itemView.findViewById(R.id.title_today_music_play);
            author = (TextView) itemView.findViewById(R.id.author_today_music_play);
        }
    }
}
