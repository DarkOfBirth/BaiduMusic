package com.example.administrator.baidumusic.music.recommend.hotalbum;

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
 * Created by Administrator on 2016/10/27.
 */

public class HotAlbumAdapter extends RecyclerView.Adapter<HotAlbumAdapter.MyViewHolder> {
    private RecommendBean bean;

    public void setBean(RecommendBean bean) {
        this.bean = bean;
    }

    private Context context;
    public HotAlbumAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HotAlbumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_cd,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HotAlbumAdapter.MyViewHolder holder, int position) {
            holder.title.setText(bean.getResult().getMix_22().getResult().get(position).getTitle());
            holder.author.setText(bean.getResult().getMix_22().getResult().get(position).getAuthor());
        SingleVolley.getInstance().getImage(bean.getResult().getMix_22().getResult().get(position).getPic()
        ,holder.image);
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getMix_22().getResult()== null ? 0 :
        bean.getResult().getMix_22().getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private TextView author;
        private TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_item_new_cd);
            title = (TextView) itemView.findViewById(R.id.title_item_new_cd);
            author = (TextView) itemView.findViewById(R.id.author_item_new_cd);
        }
    }
}
