package com.clocle.huxiang.clocle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.Constant;
import com.httpThread.Reg_http;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用户注册与登录
 * Created by Administrator on 2016/7/12.
 */
public class Reg extends Activity implements View.OnClickListener {
    private EditText name;
    private EditText password;
    private Button reg;
    private TextView phonereg;
    private Button login;
    private ProgressBar progress;
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_layout);
        bindView();
       /* SMSSDK.initSDK(this, "14f6eb5d87954", "1d4ec79b64b6f02275d0509b6123729c");
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
// 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

// 提交用户信息
                    // registerUser(country, phone);
                }
            }
        });
        registerPage.show(this);*/
        //
    }

    private void bindView() {
        progress= (ProgressBar) findViewById(R.id.reg_progress);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        reg = (Button) findViewById(R.id.reg_bt);
        phonereg = (TextView) findViewById(R.id.phone_reg);
        login = (Button) findViewById(R.id.login_bt);
        name.setOnClickListener(this);
        login.setOnClickListener(this);
        password.setOnClickListener(this);
        reg.setOnClickListener(this);
        phonereg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_reg:
                Intent regintent = new Intent(this, Reg_login.class);
                startActivity(regintent);
                break;
            case R.id.reg_bt:
                if (name.getText().toString() == null|name.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入昵称！", Toast.LENGTH_SHORT);
                    return;
                }
                if (password.getText().toString() == null|password.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT);
                    return;
                }
                progress.setVisibility(View.VISIBLE);
                httpGetreg();

                break;
            case R.id.login_bt:
                if (name.getText().toString() == null|name.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入昵称！", Toast.LENGTH_SHORT);
                    return;
                }
                if (password.getText().toString() == null|password.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT);
                    return;
                }
                progress.setVisibility(View.VISIBLE);
                httpgetlogin();
        }
    }

    public void httpgetlogin() {

        final Request request = new Request.Builder().url("http://192.168.1.111:8080/clocle/servlet/Login_servlet?user_name=" + name.getText().toString() + "&user_password=" + password.getText().toString()).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {


            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(Reg.this, MainActivity.class);
                            progress.setVisibility(View.GONE);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else{
                    progress.setVisibility(View.GONE);

                    Toast.makeText(Reg.this, "登录失败", Toast.LENGTH_SHORT);

                }
            }
        });
    }

    public void httpGetreg() {


        final Request request1 = new Request.Builder().url("http://192.168.1.111:8080/clocle/servlet/Reg_servlet?user_name=" + name.getText().toString() + "&user_password=" + password.getText().toString()).build();
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setVisibility(View.GONE);
                            finish();
                        }
                    });
                }
            }
        });





    /*call1.enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {

        }

        @Override
        public void onResponse(Response response) throws IOException {
            Toast.makeText(Reg.this, "成功", Toast.LENGTH_SHORT);
                          *//*if(response.isSuccessful()){
                             android.os.Handler handler =new android.os.Handler();

                          handler.post(new Runnable() {
                              @Override
                              public void run() {
                                  Toast.makeText(Reg.this,"登录成功",Toast.LENGTH_SHORT).show();
                                  Intent intent =new Intent(Reg.this,MainActivity.class);
                                  startActivity(intent);
                              }
                          });
                          }
                          else {
    Toast.makeText(Reg.this,"登录失败，请重试！",Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
                  break;*//*


            //  case


        *//*String username = name.getText().toString();
        String psword = password.getText().toString();
        System.out.print(username);
        Reg_http reg_http = new Reg_http(Constant.localhost, username, psword);
        reg_http.start();*//*


        }});*/

    /*public String httpgetThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
               return httpGet();

            }
        }).start();

    }*/
    }
}