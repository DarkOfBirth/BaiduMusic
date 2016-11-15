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
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.SingleVolley;

/**
 * Created by dllo on 16/10/25.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private Context context;
    private OnVideoClickListener mOnVideoClickListener;
    private VideoBean bean;
    public void setListBeen(VideoBean bean) {
        Log.d("VideoAdapter", "执行");
        this.bean = bean;
        notifyDataSetChanged();
    }

    public void setmOnVideoClickListener(OnVideoClickListener mOnVideoClickListener) {
        this.mOnVideoClickListener = mOnVideoClickListener;
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
    public void onBindViewHolder(VideoAdapter.MyViewHolder myViewHolder, final int i) {
       myViewHolder.title.setText(bean.getResult().getMv_list().get(i).getTitle());
        myViewHolder.artist.setText(bean.getResult().getMv_list().get(i).getArtist());
        SingleVolley.getInstance().getImage(bean.getResult().getMv_list().get(i).getThumbnail(),
                myViewHolder.imageView);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mOnVideoClickListener.onVideoClick(AppValues.MV_HEAD +
                        bean.getResult().getMv_list().get(i).getMv_id() + AppValues.MV_END);

            }
        });
    }

    @Override
    public int getItemCount() {
        // Log.d("VideoAdapter", "listBeen.getContent().size():" + listBeen.getContent().size());
        return bean == null ? 0 : bean.getResult().getMv_list().size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView artist;
        private final TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_video);

            artist = (TextView) itemView.findViewById(R.id.tv_artist_item_video);
            title = (TextView) itemView.findViewById(R.id.tv_title_item_video);
        }
    }
    public interface OnVideoClickListener{
        void onVideoClick(String url);
    }
}
