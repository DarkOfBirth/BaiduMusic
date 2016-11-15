package com.example.administrator.baidumusic.music.musiclist.musiclistdetail;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.databean.MusicListDetailBean;
import com.example.administrator.baidumusic.messageevent.PlayMusicEvent;
import com.example.administrator.baidumusic.database.SongListEvent;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.CommonVH;
import com.example.administrator.baidumusic.tools.DBTools;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/11/6.
 */

public class MusicListDetailAdapter extends RecyclerView.Adapter<CommonVH> {
    public void setLoad(boolean load) {
        isLoad = load;
    }

    private boolean isLoad = false;
    private MusicListDetailBean musicListDetailBean;
    private MusicListDetailBean.ContentBean bean;
    private OnMusicListItemClickListener mOnMusicListItemClickListener;


    public void setmOnMusicListItemClickListener(OnMusicListItemClickListener mOnMusicListItemClickListener) {
        this.mOnMusicListItemClickListener = mOnMusicListItemClickListener;
    }

    public void setMusicListDetailBean(MusicListDetailBean musicListDetailBean) {
        this.musicListDetailBean = musicListDetailBean;
        notifyDataSetChanged();
    }

    @Override
    public CommonVH onCreateViewHolder(ViewGroup parent, int viewType) {

        return CommonVH.getViewHolder(parent, R.layout.item_musiclist_detail);
    }

    @Override
    public void onBindViewHolder(CommonVH holder, final int position) {
        bean = musicListDetailBean.getContent().get(position);
        holder.setText(R.id.title_item_musiclist_detail, bean.getTitle())
                .setText(R.id.author_item_musiclist_detail, bean.getAuthor())
                .setImageVisible(R.id.mv_item_musiclist_detail, bean.getHas_mv_mobile() == 1 ? View.VISIBLE : View.INVISIBLE).
                setImageVisible(R.id.mike_item_musiclist_detail, bean.getLearn() == 1 ? View.VISIBLE : View.INVISIBLE);


        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "position:" + position);
                PlayMusicEvent event = new PlayMusicEvent();

                event.setSongId(musicListDetailBean.getContent().get(position).getSong_id());
                EventBus.getDefault().post(event);
                modifyPlayState(musicListDetailBean.getContent().get(position), AppValues.PLAY_STATE);
                // 如果未添加, 添加列表
                if (!isLoad) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadData(position);

                        }
                    }).start();
                }

            }
        });

    }

    private void loadData(int position) {
        if (isLoad) {
            return;
        }
        // 先清空
        DBTools.getInstance().deleteAllMusicInfo(SongListEvent.class);
        for (int i = 0; i < musicListDetailBean.getContent().size(); i++) {
            SongListEvent songListEvent = new SongListEvent();
            Log.d("MusicListDetailAdapter1", "插入");
            songListEvent.setSongId(musicListDetailBean.getContent().get(i).getSong_id());
            songListEvent.setTitle(musicListDetailBean.getContent().get(i).getTitle());
            songListEvent.setAuthor(musicListDetailBean.getContent().get(i).getAuthor());
            songListEvent.setState(-1);
            DBTools.getInstance().insertMusciInfo(songListEvent);
        }
        DBTools.getInstance().modifyMusicInfo(SongListEvent.class,
                musicListDetailBean.getContent().get(position).getSong_id(),
                "state",AppValues.PLAY_STATE);
        isLoad = true;

    }
    // 修改播放状态n
    public void modifyPlayState(MusicListDetailBean.ContentBean contentBean,int state){
        // 先将其他的状态都复位
        DBTools.getInstance().modifyMusicInfo(SongListEvent.class,"","state",AppValues.STOP_STATE);
        DBTools.getInstance().modifyMusicInfo(SongListEvent.class,contentBean.getSong_id(),"state",state);
    }
    @Override
    public int getItemCount() {
        int count = 0;
        try {

            count = musicListDetailBean.getContent().size();
        } catch (NullPointerException e) {
            Log.d("MusicListDetailAdapter", "e:" + e);
        }
        return count;
    }

    public interface OnMusicListItemClickListener {
        void onItemClick(String songId);
    }
}
