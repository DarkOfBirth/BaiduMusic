package com.example.administrator.baidumusic.main;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.messageevent.SongListEvent;
import com.example.administrator.baidumusic.tools.CommonVH;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/7.
 */
public class SongListAdatper extends RecyclerView.Adapter<CommonVH> {
    private ArrayList<SongListEvent> arrayList;

    public void setArrayList(ArrayList<SongListEvent> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public CommonVH onCreateViewHolder(ViewGroup parent, int viewType) {

        return CommonVH.getViewHolder(parent, R.layout.item_songlist);
    }

    @Override
    public void onBindViewHolder(CommonVH holder, int position) {

            holder.setText(R.id.title_item_songlist,arrayList.get(position).getTitle())
                    .setText(R.id.author_item_songlist,arrayList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }
}
