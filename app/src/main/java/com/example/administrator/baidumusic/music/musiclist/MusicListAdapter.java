package com.example.administrator.baidumusic.music.musiclist;

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
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MyViewHolder> {
    private Context context;

    private MusicListBean listBeen;
    public void setListBeen(MusicListBean listBeen) {
        Log.d("MusicListAdapter", "执行");
        this.listBeen = listBeen;
        notifyDataSetChanged();
    }





    public MusicListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MusicListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_musiclist,null);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MusicListAdapter.MyViewHolder myViewHolder, int i) {
        SingleVolley.getInstance().getImage(listBeen.getContent().get(i).getPic_300(),myViewHolder.imageView);
        myViewHolder.title.setText(listBeen.getContent().get(i).getTitle());
        myViewHolder.listnum.setText(listBeen.getContent().get(i).getListenum());
    }

    @Override
    public int getItemCount() {
       // Log.d("MusicListAdapter", "listBeen.getContent().size():" + listBeen.getContent().size());
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
