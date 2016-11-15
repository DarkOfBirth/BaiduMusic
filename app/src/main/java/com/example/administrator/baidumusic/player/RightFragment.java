package com.example.administrator.baidumusic.player;

import android.content.Context;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.messageevent.LrcEvent;
import com.example.administrator.baidumusic.messageevent.MusicProgressEvent;
import com.example.administrator.baidumusic.messageevent.ProgerssHandEvent;
import com.example.administrator.baidumusic.player.lrcshow.DefaultLrcBuilder;
import com.example.administrator.baidumusic.player.lrcshow.ILrcBuilder;
import com.example.administrator.baidumusic.player.lrcshow.ILrcViewListener;
import com.example.administrator.baidumusic.player.lrcshow.LrcRow;
import com.example.administrator.baidumusic.player.lrcshow.LrcView;
import com.example.administrator.baidumusic.tools.AppValues;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class RightFragment extends BaseFragment {
    private LrcView mLrcView;
    private ILrcBuilder builder;
    private String lrcString;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        builder = new DefaultLrcBuilder();
        EventBus.getDefault().register(RightFragment.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_right_player;
    }

    @Override
    protected void initView() {
        mLrcView = bindView(R.id.lrcView);




        mLrcView.setListener(new ILrcViewListener() {
            //当歌词被用户上下拖动的时候回调该方法,从高亮的那一句歌词开始播放
            public void onLrcSeeked(int newPosition, LrcRow row) {

                EventBus.getDefault().post(new ProgerssHandEvent((int) row.time));
            }
        });
    }

    @Override
    protected void initData() {
        //解析歌词构造器
        builder = new DefaultLrcBuilder();


        //解析歌词返回LrcRow集合
        List<LrcRow> rows = builder.getLrcRows(lrcString);
        //将得到的歌词集合传给mLrcView用来展示

        mLrcView.setLrc(rows);



    }

    private void LrcRefresh(){
        List<LrcRow> rows = builder.getLrcRows(lrcString);
        //将得到的歌词集合传给mLrcView用来展示

        mLrcView.setLrc(rows);
    }

    // 传值 歌词的跳转
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MusicProgressEvent event) {
        if (AppValues.PROGRESS_AUTO == event.getType()) {
            mLrcView.seekLrcToTime(event.getCurrentPosittion());

            LrcRefresh();
        }
    }

    // 传值 接收歌词
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessageEvent(LrcEvent event) {

        lrcString = event.getContent();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(RightFragment.this);
    }
}

