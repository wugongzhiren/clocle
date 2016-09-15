package com.clocle.huxiang.clocle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.Bmob_UserBean;
import com.constant.Constant;
import com.httpThread.Reg_http;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tool.Bg_blur;

/**
 * 用户注册与登录
 * Created by Administrator on 2016/7/12.
 */
public class Reg extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private EditText password;
    private Button reg;
    private TextView phonereg;
    private Button login;
    private ProgressBar progress;
    private RelativeLayout relativeLayout;
    private Bitmap login_bg_bm;
    private ImageView imageView;
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "fbd7c66a38b160c5677a774971be3294");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.login_bg);
        login_bg_bm=Bg_blur.blur(this,bitmap);
        setContentView(R.layout.reg_layout);
        bindView();
         // 不存在设置默认图片
        imageView.setImageBitmap(login_bg_bm);
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
        relativeLayout= (RelativeLayout) findViewById(R.id.reg);
        imageView= (ImageView) findViewById(R.id.bg_imageview);
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
                String nametem=name.getText().toString();
                if (nametem.equals("")) {
                    Toast.makeText(this, "请输入昵称！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String pstem=password.getText().toString();
                if (pstem.equals("")) {
                    Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                progress.setVisibility(View.VISIBLE);
                //httpGetreg();


                Bmob_UserBean user = new Bmob_UserBean();
//注意：不能调用setObjectId("")方法
                user.setUsername(nametem);
                user.setPassword(pstem);
                user.save(new SaveListener<String>() {

                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(Reg.this,"成功",Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
                break;
            case R.id.login_bt:
                String nametem1=name.getText().toString();
                if (nametem1.equals("")) {
                    Toast.makeText(this, "请输入昵称！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String pstem1=password.getText().toString();
                if (pstem1.equals("")) {
                    Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobQuery<Bmob_UserBean> query=new BmobQuery<Bmob_UserBean>();
                query.getObject("b08f6bca4e", new QueryListener<Bmob_UserBean>() {
                    @Override
                    public void done(Bmob_UserBean bmob_userBean, BmobException e) {
                        if(e==null){
                            progress.setVisibility(View.GONE);
                            Intent intent=new Intent(Reg.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(Reg.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //查询多条数据
               /* query.addWhereEqualTo("username",nametem1);
                query.addWhereEqualTo("password",pstem1);
                progress.setVisibility(View.VISIBLE);
                query.findObjects(new FindListener<Bmob_UserBean>() {
                    @Override
                    public void done(List<Bmob_UserBean> list, BmobException e) {
                        if(e==null){
                            progress.setVisibility(View.GONE);
                            Intent intent=new Intent(Reg.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(Reg.this,"登录失败",Toast.LENGTH_SHORT).show();

                        }
                    }
                });*/

                //httpgetlogin();
        }
    }

    /*public void httpgetlogin() {

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
*/
    /*public void httpGetreg() {


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

*/



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
