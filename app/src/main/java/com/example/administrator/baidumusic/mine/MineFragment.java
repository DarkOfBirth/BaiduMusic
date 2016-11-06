package com.example.administrator.baidumusic.mine;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.mine.localmusic.LocalMusicFragment;

/**
 * Created by dllo on 16/10/21.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View localMusic;

    @Override
    protected int getLayout() {
        return R.layout.frag_mine;
    }

    @Override
    protected void initView() {
        localMusic = bindView(R.id.local_music_ll);

        localMusic.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.local_music_ll:
           FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_fl,new LocalMusicFragment());
                transaction.addToBackStack(null);
                transaction.commit();
        }
    }
}
