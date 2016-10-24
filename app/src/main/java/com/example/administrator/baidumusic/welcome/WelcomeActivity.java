package com.example.administrator.baidumusic.welcome;


import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.example.administrator.baidumusic.R;
import com.example.administrator.baidumusic.base.BaseActivity;
import com.example.administrator.baidumusic.main.MainActivity;
import com.example.administrator.baidumusic.tools.AppValues;
import com.example.administrator.baidumusic.tools.SingleVolley;


public class WelcomeActivity extends BaseActivity {
    private ImageView ivWelcome;
    @Override
    protected void initData() {
        SingleVolley.getInstance().getImage(AppValues.WELCOME_IMAGE_URL,ivWelcome);
        Counter counter = new Counter(5000,1000);
        counter.start();
    }

    @Override
    protected void initViews() {
        ivWelcome = (ImageView) findViewById(R.id.iv_welcome);
    }

    @Override
    protected int getLayout() {
        return R.layout.welcome;
    }

    class Counter extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
