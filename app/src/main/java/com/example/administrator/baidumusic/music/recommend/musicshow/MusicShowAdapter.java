package com.example.administrator.baidumusic.music.recommend.musicshow;

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

public class MusicShowAdapter extends RecyclerView.Adapter<MusicShowAdapter.MyViewHolder> {
    private RecommendBean bean;

    public void setBean(RecommendBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_cd, parent, false);
        MusicShowAdapter.MyViewHolder myViewHolder = new MusicShowAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.author.setText("");
        holder.title.setText(bean.getResult().getRadio().getResult().get(position).getTitle());
        SingleVolley.getInstance().getImage(bean.getResult().getRadio().getResult().get(position).getPic(),
                holder.image);
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getRadio().getResult() == null ? 0 :
                bean.getResult().getRadio().getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;


        private ImageView image;

        private TextView author;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_item_new_cd);
            author = (TextView) itemView.findViewById(R.id.author_item_new_cd);

            image = (ImageView) itemView.findViewById(R.id.image_item_new_cd);
        }
    }
}
