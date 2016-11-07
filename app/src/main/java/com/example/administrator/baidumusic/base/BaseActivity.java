package com.example.administrator.baidumusic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dllo on 16/10/21.
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定布局
        setContentView(getLayout());

        // 初始化组件

        initViews();
        // 初始化数据
        initData();
    }
    // 简化强转的过程
    protected <T extends View> T bindView(int id){
        return (T) findViewById(id);
    }
    protected <T extends View > T bindView(View view, int id){
        return (T) view.findViewById(id);
    }

    protected abstract void initData();

    protected abstract void initViews();

    protected abstract int getLayout();



}
