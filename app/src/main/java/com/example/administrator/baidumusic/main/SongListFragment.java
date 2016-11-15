package com.example.administrator.baidumusic.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.base.MyApp;
import com.example.administrator.baidumusic.messageevent.ClearEvent;
import com.example.administrator.baidumusic.database.SongListEvent;
import com.example.administrator.baidumusic.tools.DBTools;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/7.
 */
public class SongListFragment extends BaseFragment implements View.OnClickListener {

    private ImageView other;
    private RecyclerView recyclerView;
    private TextView clearAll;
    private SongListAdatper adapter;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_songlist;
    }

    @Override
    protected void initView() {
        other = bindView(R.id.other_songlist);
        recyclerView = bindView(R.id.rv_songlist);
        clearAll = bindView(R.id.clear_all_song_list);
        other.setOnClickListener(this);
        clearAll.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        adapter = new SongListAdatper();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApp.getmContext());
        recyclerView.setLayoutManager(layoutManager);
        getData();

        adapter.setmOnSongListClick(new SongListAdatper.OnSongListClick() {
            @Override
            public void onDelete(String songId) {
                DBTools.getInstance().deleteOneMusicInfo(SongListEvent.class,songId);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void getData() {
        DBTools.getInstance().queryMusicInfo(SongListEvent.class, new DBTools.OnQueryMusicInfo<SongListEvent>() {
            @Override
            public void OnQuery(ArrayList<SongListEvent> query) {
                Log.d("SongListFragment", "query.size():" + query.size());
                adapter.setArrayList(query);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.other_songlist:

                MainActivity activity = (MainActivity) getActivity();
                activity.setFlag(false);

                getActivity().onBackPressed();

                break;
            case R.id.clear_all_song_list:
                DBTools.getInstance().deleteAllMusicInfo(SongListEvent.class);
                getData();
                EventBus.getDefault().post(new ClearEvent(false));
                break;
        }
    }
}
