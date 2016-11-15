package com.example.administrator.baidumusic.main;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.database.User;
import com.example.administrator.baidumusic.main.login.LoginFragment;
import com.example.administrator.baidumusic.messageevent.LoginInfoEvent;
import com.example.administrator.baidumusic.tools.DBTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/24.
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout login;
    private TextView user;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(MoreFragment.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_more;
    }

    @Override
    protected void initView() {
        login = bindView(R.id.login);
        user = bindView(R.id.user_more);

        login.setOnClickListener(this);
        login.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器
                builder.setTitle("退出登录"); //设置标题
                builder.setMessage("是否确认退出?"); //设置内容

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBTools.getInstance().deleteAllMusicInfo(User.class);
                        user.setText("立即登录\n登录后,自动为您同步歌曲");
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        DBTools.getInstance().queryMusicInfo(User.class, new DBTools.OnQueryMusicInfo<User>() {
            @Override
            public void OnQuery(ArrayList<User> query) {
                Log.d("MoreFragment", "query:" + query.size());
                if (query.size()!= 0 && query.get(0).isLogin()) {
                   user.setText(query.get(0).getUserName());

                }

            }
        });

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.showBottom();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:

                DBTools.getInstance().queryMusicInfo(User.class, new DBTools.OnQueryMusicInfo<User>() {
                    @Override
                    public void OnQuery(ArrayList<User> query) {
                        Log.d("MoreFragment", "query:" + query.size());
                        if ( query.size() == 0 || !query.get(0).isLogin()) {
                            LoginFragment loginFragment = new LoginFragment();

                            MainActivity activity = (MainActivity) getActivity();
                            activity.jumpFragment(loginFragment);
                            activity.unShowBottom();

                        }

                    }
                });


                break;
        }
    }


    // 用户信息传递
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginInfoEvent event) {

        user.setText(event.getUserName().toString());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(MoreFragment.this);
    }
}
