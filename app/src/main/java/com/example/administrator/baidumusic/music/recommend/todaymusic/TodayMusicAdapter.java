package com.example.administrator.baidumusic.music.recommend.todaymusic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.music.recommend.RecommendBean;
import com.example.administrator.baidumusic.tools.SingleVolley;

/**
 * Created by Administrator on 2016/10/27.
 */

public class TodayMusicAdapter extends RecyclerView.Adapter<TodayMusicAdapter.MyViewHolder> {
    private Context context;
    private RecommendBean bean;

    public void setBean(RecommendBean bean) {
//        List<RecommendBean.ResultBean.RecsongBean.RecsongResultBean> list
//                = bean.getResult().getRecsong().getResult();
//
//        List<RecommendBean.ResultBean.RecsongBean.RecsongResultBean> m
//                = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            if(String.valueOf(1).equals(list.get(i).getLearn())){
//                m.add(list.get(i));
//            }
//        }
//        bean.getResult().getRecsong().setResult(m);
        this.bean = bean;
        notifyDataSetChanged();
    }

    public TodayMusicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_today_music, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecommendBean.ResultBean.RecsongBean.RecsongResultBean resultBean
                = bean.getResult().getRecsong().getResult().get(position);
//        Log.d("bind中的learn的值", resultBean.getLearn());
//        if (String.valueOf(1).equals(resultBean.getLearn()) ) {
            Log.d("bind中的learn的值", "true");
            holder.author.setText(resultBean.getAuthor());
            holder.title.setText(resultBean.getTitle());
            SingleVolley.getInstance().getImage(resultBean.getPic_premium(), holder.image);
//        }


    }

    @Override
    public int getItemCount() {

        int total = bean.getResult().getRecsong().getResult().size();

        return bean.getResult().getRecsong().getResult() == null ? 0 : 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView image;
        private CircleImageView play;
        private TextView title;
        private TextView author;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.iv_today_music);
            play = (CircleImageView) itemView.findViewById(R.id.iv_today_music_play);
            title = (TextView) itemView.findViewById(R.id.title_today_music_play);
            author = (TextView) itemView.findViewById(R.id.author_today_music_play);
        }
    }
}
