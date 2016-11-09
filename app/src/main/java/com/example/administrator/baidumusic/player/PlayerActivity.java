package com.example.administrator.baidumusic.player;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseActivity;
import com.example.administrator.baidumusic.effect.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class PlayerActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private ViewPager vp;
    private List<Fragment> fragmentList;

    @Override
    protected int getLayout() {
        return R.layout.activity_player;
    }

    @Override
    protected void initViews() {

        back = bindView(R.id.back_player);
        vp = bindView(R.id.vp_player);
        back.setOnClickListener(this);
        //vp.setPageTransformer();
    }

    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        LeftFragment leftFragment = new LeftFragment();
        CenterFragment centerFragment = new CenterFragment();
        RightFragment rightFragment = new RightFragment();
        fragmentList.add(leftFragment);
        fragmentList.add(centerFragment);
        fragmentList.add(rightFragment);
        PlayerViewPagerAdapter adapter = new PlayerViewPagerAdapter(getSupportFragmentManager());
        adapter.setFragmentList(fragmentList);
        vp.setPageTransformer(true, new ZoomOutPageTransformer());
        vp.setAdapter(adapter);
        vp.setCurrentItem(1);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_player:

                onBackPressed();
                break;
        }
    }
}
