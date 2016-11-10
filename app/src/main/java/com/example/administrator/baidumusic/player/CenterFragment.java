package com.example.administrator.baidumusic.player;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.databean.MusicItemBean;
import com.example.administrator.baidumusic.tools.SingleVolley;

/**
 * Created by Administrator on 2016/11/9.
 */

public class CenterFragment extends BaseFragment {
    private ImageView image,mv;
    private TextView title, author;
    @Override
    protected int getLayout() {
        return R.layout.frag_center_player;
    }

    @Override
    protected void initView() {
        image = bindView(R.id.image_center_player);
        title = bindView(R.id.title_center_player);
        author = bindView(R.id.author_center_player);
        mv = bindView(R.id.mv_center_player);
    }

    @Override
    protected void initData() {
        MusicItemBean musicItemBean = (MusicItemBean) getArguments().getSerializable("bundle");
        if(musicItemBean== null){
            return;
        }


        title.setText(musicItemBean.getSonginfo().getTitle());
        author.setText(musicItemBean.getSonginfo().getAuthor());
        mv.setVisibility(musicItemBean.getSonginfo().getHas_mv() == 1 ? View.VISIBLE: View.INVISIBLE);
        SingleVolley.getInstance().getImage(musicItemBean.getSonginfo().getPic_big(),image);
    }
}
