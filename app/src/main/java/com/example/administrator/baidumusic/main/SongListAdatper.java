package com.example.administrator.baidumusic.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.MyApp;
import com.example.administrator.baidumusic.messageevent.PlayMusicEvent;
import com.example.administrator.baidumusic.database.SongListEvent;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.CommonVH;
import com.example.administrator.baidumusic.tools.DBTools;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/7.
 */
public class SongListAdatper extends RecyclerView.Adapter<CommonVH> {
    private ArrayList<SongListEvent> arrayList;
    private OnSongListClick mOnSongListClick;

    // 点击接口
    public void setmOnSongListClick(OnSongListClick mOnSongListClick) {
        this.mOnSongListClick = mOnSongListClick;
    }

    public void setArrayList(ArrayList<SongListEvent> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public CommonVH onCreateViewHolder(ViewGroup parent, int viewType) {

        return CommonVH.getViewHolder(parent, R.layout.item_songlist);
    }

    @Override
    public void onBindViewHolder(CommonVH holder, final int position) {

        holder.setText(R.id.title_item_songlist, arrayList.get(position).getTitle())
                .setText(R.id.author_item_songlist, arrayList.get(position).getAuthor())
                .setImageVisible(R.id.play_icon_item_songlist, arrayList.get(position).getState()
                        == 1 ? View.VISIBLE : View.GONE)
                .setTextColor(R.id.title_item_songlist, arrayList.get(position).getState() == 1 ?
                        MyApp.getmContext().getResources().getColor(R.color.colorTextBlue) :
                        MyApp.getmContext().getResources().getColor(R.color.colorTextBlack));
        // 删除的接口回调
        holder.setViewClick(R.id.delete_item_songlist, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnSongListClick.onDelete(arrayList.get(position).getSongId());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });


        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayMusicEvent playMusicEvent = new PlayMusicEvent();
                playMusicEvent.setSongId(arrayList.get(position).getSongId());

                DBTools.getInstance().modifyMusicInfo(SongListEvent.class, "", "state", AppValues.STOP_STATE);
                DBTools.getInstance().modifyMusicInfo(SongListEvent.class, arrayList.get(position).getSongId(), "state", AppValues.PLAY_STATE);
                EventBus.getDefault().post(playMusicEvent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    public interface OnSongListClick {
        void onDelete(String position);
    }
}
