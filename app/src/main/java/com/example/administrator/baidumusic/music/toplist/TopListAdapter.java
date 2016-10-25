package com.example.administrator.baidumusic.music.toplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.tools.SingleVolley;

/**
 * Created by dllo on 16/10/25.
 */
public class TopListAdapter extends BaseAdapter{
    private TopListBean bean;
    private Context context;

    public void setBean(TopListBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public TopListAdapter(Context mContext) {
        this.context = mContext;
    }

    @Override
    public int getCount() {
        return bean == null ? 0 : bean.getContent().size();
    }

    @Override
    public Object getItem(int i) {
        return bean.getContent().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholder = null;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_toplist,null);
            viewholder = new ViewHolder(view);
            view.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) view.getTag();
        }
        SingleVolley.getInstance().getImage(bean.getContent().get(i).getPic_s192(),viewholder.image);
        viewholder.name.setText(bean.getContent().get(i).getName());
        viewholder.first.setText(bean.getContent().get(i).getContent().get(0).getTitle()
                +"-" +bean.getContent().get(i).getContent().get(0).getAuthor());
        viewholder.second.setText(bean.getContent().get(i).getContent().get(1).getTitle()
                +"-"+ bean.getContent().get(i).getContent().get(1).getAuthor());
        viewholder.third.setText(bean.getContent().get(i).getContent().get(2).getTitle()
                +"-" + bean.getContent().get(i).getContent().get(2).getAuthor());
        return view;
    }

    class ViewHolder {

        private final ImageView image;
        private final TextView name;
        private final TextView first;
        private final TextView second;
        private final TextView third;

        public ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.iv_item_toplist);
            name = (TextView) view.findViewById(R.id.tv_name_toplist);
            first = (TextView) view.findViewById(R.id.tv_first_toplist);
            second = (TextView) view.findViewById(R.id.tv_second_toplist);
            third = (TextView) view.findViewById(R.id.tv_third_toplist);
        }
    }
}
