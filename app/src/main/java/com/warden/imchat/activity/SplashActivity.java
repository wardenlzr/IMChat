package com.warden.imchat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.warden.imchat.R;
import com.warden.imchat.utils.Util;

public class SplashActivity extends BaseActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        handler.postDelayed(task, 1500);
    }

    private Handler handler = new Handler() {
    };

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if (Util.getLoginStatic(context)) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
            finish();
            handler.removeCallbacks(task);

        }
    };
}
