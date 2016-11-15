package com.example.administrator.baidumusic.main.login;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseFragment;
import com.example.administrator.baidumusic.database.User;
import com.example.administrator.baidumusic.main.MainActivity;
import com.example.administrator.baidumusic.messageevent.LoginInfoEvent;
import com.example.administrator.baidumusic.tools.DBTools;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by Administrator on 2016/11/15.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private TextView register;
    private EditText user;
    private EditText password;
    private TextView login;
    private ImageView back;

    @Override
    protected int getLayout() {
        return R.layout.frag_login;
    }

    @Override
    protected void initView() {
        register = bindView(R.id.register);
        user = bindView(R.id.user);
        password = bindView(R.id.password);
        back = bindView(R.id.back_login);
        login = bindView(R.id.login);
        user.setFocusable(true);



        back.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.unShowBottom();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
//打开注册页面
                RegisterPage registerPage = new RegisterPage();

                registerPage.setRegisterCallback(new EventHandler() {
                    @Override
                    public void beforeEvent(int i, Object o) {
                        super.beforeEvent(i, o);
                    }

                    public void afterEvent(int event, int result, Object data) {
// 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            user.setText(phone);
                            int len = phone.length();
                            String pwd = phone.substring(len / 2);
                            Log.d("Sysout", pwd);
                            password.setText(pwd);

// 提交用户信息（此方法可以不调用）
                            registerUser(phone, pwd);
                        }
                    }
                });
                registerPage.show(mContext);

                break;

            case R.id.back_login:
                getActivity().onBackPressed();
                break;
            case R.id.login:
                Log.d("LoginFragment", "user.getText():" + user.getText());

                if (user.getText().toString().equals("")) {
                    Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.getText().equals("")) {
                    Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    login(user.getText().toString(), password.getText().toString());
                }
                //registerUser(user.getText().toString(), password.getText().toString());
                break;
        }
    }

    private void registerUser(final String phone, final String pwd) {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(phone);
        bmobUser.setPassword(pwd);
        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(mContext, "注册成功, 密码为后手机号后六位", Toast.LENGTH_SHORT).show();
                    user.setText(phone);
                    password.setText(pwd);
                    // login(phone, pwd);
                } else {
                    // 注册失败
                    Log.d("LoginFragment", e.getMessage());
                }
            }
        });

    }

    private void login(final String phone, final String pwd) {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(phone);
        bmobUser.setPassword(pwd);
        bmobUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new LoginInfoEvent(phone));
                    User user = new User();
                    user.setLogin(true);
                    user.setUserName(phone);
                    user.setUserPwd(pwd);
                    DBTools.getInstance().insertMusciInfo(user);
                    //getActivity().onBackPressed();
                    getActivity().onBackPressed();

                } else {
                    // 登录失败
                    Toast.makeText(mContext, "登录失败\n用户名与密码不匹配", Toast.LENGTH_SHORT).show();
                    LoginFragment.this.user.setHint("手机号/用户名/邮箱");
                    LoginFragment.this.password.setHint("密码");
                    Log.d("LoginFragment", e.getMessage());
                }
            }
        });
    }
}
