package com.example.administrator.baidumusic.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;

/**
 * Created by dllo on 16/10/21.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         SMSSDK.initSDK(mContext, "190849eaa54c4", "7647277fba159c9b599bd51095fb6008");
        // SMSSDK.initSDK(mContext, "190a72647e252", "598abdc50ddd7013a172726943cb4821");
        Bmob.initialize(mContext, "272912a3169d92925879b4c0c0dc5cb9");
        initData();
    }

    protected <T extends View> T bindView(int id) {
        return (T) getView().findViewById(id);
    }

    protected <T extends View> T bindView(View view, int id) {
        return (T) view.findViewById(id);
    }

    protected abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData();
}
