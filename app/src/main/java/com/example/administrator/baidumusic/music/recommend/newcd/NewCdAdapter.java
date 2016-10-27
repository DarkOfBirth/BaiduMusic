package com.example.administrator.baidumusic.music.recommend.newcd;

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

public class NewCdAdapter extends RecyclerView.Adapter<NewCdAdapter.MyViewholder> {
    private Context context;
    private RecommendBean bean;

    public void setBean(RecommendBean bean) {
        this.bean = bean;
    }

    public NewCdAdapter(Context context) {
        this.context = context;
    }

    @Override

    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_cd, parent, false);
        MyViewholder viewholder = new MyViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        String title = bean.getResult().getMix_1().getResult().get(position).getTitle();
        String author =bean.getResult().getMix_1().getResult().get(position).getAuthor();
        String imgmurl = bean.getResult().getMix_1().getResult().get(position).getPic();
        holder.title.setText(title);
        holder.author.setText(author);
        SingleVolley.getInstance().getImage(imgmurl,holder.image);
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getMix_1().getResult()== null ? 0 : 6;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView title;
        private final TextView author;

        public MyViewholder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_item_new_cd);
            title = (TextView) itemView.findViewById(R.id.title_item_new_cd);
            author = (TextView) itemView.findViewById(R.id.author_item_new_cd);
        }
    }
}
