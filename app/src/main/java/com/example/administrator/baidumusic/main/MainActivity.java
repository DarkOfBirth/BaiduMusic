package com.example.administrator.baidumusic.main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private FragmentTransaction transaction;

    @Override
    protected void initData() {

        FragmentManager manager = getSupportFragmentManager();

        transaction = manager.beginTransaction();
        Fragment mainFragment = new MainFragment();
        transaction.add(R.id.main_fl,mainFragment);

        transaction.commit();

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
