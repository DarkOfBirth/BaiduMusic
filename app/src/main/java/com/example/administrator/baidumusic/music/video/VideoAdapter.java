package com.example.administrator.baidumusic.music.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.tools.SingleVolley;

/**
 * Created by dllo on 16/10/25.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private Context context;

    private VideoAdapter listBeen;
    public void setListBeen(VideoAdapter listBeen) {
        Log.d("VideoAdapter", "执行");
        this.listBeen = listBeen;
        notifyDataSetChanged();
    }





    public VideoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_video,null);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

   
    

    @Override
    public void onBindViewHolder(VideoAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        // Log.d("VideoAdapter", "listBeen.getContent().size():" + listBeen.getContent().size());
        return listBeen == null ? 0 : listBeen.getContent().size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView listnum;
        private final TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_musiclist);
            listnum = (TextView) itemView.findViewById(R.id.tv_item_listnum);
            title = (TextView) itemView.findViewById(R.id.tv_title_item_musiclist);
        }
    }
}
