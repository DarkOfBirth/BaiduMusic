package com.example.administrator.baidumusic.music.recommend.originalmusic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.music.recommend.RecommendBean;
import com.example.administrator.baidumusic.tools.SingleVolley;

/**
 * Created by Administrator on 2016/10/29.
 */

public class OriginalMusicAdapter extends RecyclerView.Adapter<OriginalMusicAdapter.MyViewHolder> {
    private Context context;
    private RecommendBean bean;

    public void setBean(RecommendBean bean) {
        this.bean = bean;
    }

    public OriginalMusicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_cd, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.author.setText(bean.getResult().getMix_9().getResult().get(position).getAuthor());
        holder.title.setText(bean.getResult().getMix_9().getResult().get(position).getTitle());
        SingleVolley.getInstance().getImage(bean.getResult().getMix_9().getResult().get(position).getPic(),
                holder.image);

    }

    @Override
    public int getItemCount() {
        return bean.getResult().getMix_9().getResult() == null ? 0 :
                bean.getResult().getMix_9().getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private ImageView image;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_item_new_cd);
            author = (TextView) itemView.findViewById(R.id.author_item_new_cd);
            image = (ImageView) itemView.findViewById(R.id.image_item_new_cd);
        }
    }
}
