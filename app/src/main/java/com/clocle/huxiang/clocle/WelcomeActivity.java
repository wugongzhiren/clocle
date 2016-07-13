package com.clocle.huxiang.clocle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import tool.GetuserInfo;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/4.
 */
public class WelcomeActivity extends AppCompatActivity {
    private final long SPLASH_LENGTH = 2000;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

            public void run() {
                GetuserInfo getuserInfo=new GetuserInfo(WelcomeActivity.this);
                Map infomap=getuserInfo.getUserinfo();

                if((infomap.get("user_name")==null)&(infomap.get("password")==null)){

                }
                Intent intent = new Intent(WelcomeActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_LENGTH);//2秒后跳转至应用主界面MainActivity

    }
}
