package com.example.administrator.baidumusic.music.recommend.specialcolumn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.music.recommend.RecommendBean;
import com.example.administrator.baidumusic.tools.SingleVolley;

/**
 * Created by Administrator on 2016/10/29.
 */

public class SpecialColumnAdapter extends BaseAdapter {
    private RecommendBean bean;


    public void setBean(RecommendBean bean) {


        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getResult().getMod_7().getResult() == null ? 0:
                bean.getResult().getMod_7().getResult().size();
    }

    @Override
    public Object getItem(int i) {
        return  bean.getResult().getMod_7().getResult().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.item_special_column,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.desc.setText(bean.getResult().getMod_7().getResult().get(i).getDesc());
        holder.title.setText(bean.getResult().getMod_7().getResult().get(i).getTitle());
        SingleVolley.getInstance().getImage(bean.getResult().getMod_7().getResult().get(i).getPic(),holder.image);
        return view;
    }

    class ViewHolder {
        private TextView desc, title;
        private ImageView image;
        public ViewHolder(View view) {
            desc = (TextView) view.findViewById(R.id.desc_special_column);
            title = (TextView) view.findViewById(R.id.title_special_column);
            image = (ImageView) view.findViewById(R.id.iv_special_column);
        }
    }
}
