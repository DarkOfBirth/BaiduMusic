package com.example.administrator.baidumusic.music.recommend.listrecommed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.music.recommend.RecommendBean;
import com.example.administrator.baidumusic.tools.SingleVolley;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ListRecommedAdapter extends RecyclerView.Adapter<ListRecommedAdapter.MyViewHolder> {
    private Context mContext;
    private RecommendBean bean;

    public void setBean(RecommendBean bean) {
        this.bean = bean;
    }

    public ListRecommedAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ListRecommedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_recommed, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListRecommedAdapter.MyViewHolder holder, int position) {
        holder.listnum.setText( bean.getResult().getDiy().getResult().get(position).getListenum() + "");
        String title = bean.getResult().getDiy().getResult().get(position).getTitle();
        Log.d("Sysout", title);
        holder.title.setText(title);
        SingleVolley.getInstance().getImage(bean.getResult().getDiy().getResult().get(position)
                .getPic(),holder.image);
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getDiy().getResult()==null ? 0 :
                bean.getResult().getDiy().getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView listnum;
        private final TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_item_list_recommed);
            listnum = (TextView) itemView.findViewById(R.id.tv_item_listnum_list_recommed);
            title = (TextView) itemView.findViewById(R.id.tv_title_item_list_recommed);
        }
    }
}
