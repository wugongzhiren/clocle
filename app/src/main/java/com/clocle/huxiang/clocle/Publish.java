package com.clocle.huxiang.clocle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adapter.Picked_photo_adapter;
import com.bean.Pulish_bean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import test.Test_imageloader;
import tool.DecodeSampleBitmapFromUrl;
import tool.Obeject_toJson;
import tool.Progress_dialog;
import tool.SerializableMap;
import tool.Utils;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Publish extends Activity implements View.OnClickListener {

    private EditText publish_text;
    private EditText money_text;
    private Button publish_button;
    private Button chooseimgs;
    public String request_string;
    public Dialog mydialog;
    private Intent intent;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private String img1url;
    private String img2url;
    private String img3url;
    private int imgCount;
private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_layout);
        initviews();

    }

    private void initviews() {
        publish_text = (EditText) findViewById(R.id.publish_text);
        chooseimgs = (Button) findViewById(R.id.chooseimgs);
        money_text = (EditText) findViewById(R.id.money_text);
        publish_button = (Button) findViewById(R.id.publish_button);
        /*img1 = (ImageView) findViewById(R.id.help_img1);
        img2 = (ImageView) findViewById(R.id.help_img2);
        img3 = (ImageView) findViewById(R.id.help_img3);*/
        recyclerView= (RecyclerView) findViewById(R.id.picked_photo);
        publish_text.setOnClickListener(this);
        money_text.setOnClickListener(this);
        publish_button.setOnClickListener(this);
        chooseimgs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_button://发表

                String publishtext = publish_text.getText().toString();//获取发表内容
                int money = Integer.parseInt(money_text.getText().toString());//获取悬赏金额
                int userid = 1;//实验用户id

                Calendar calendar = Calendar.getInstance();
                String date = calendar.get(Calendar.YEAR) + "年"
                        + calendar.get(Calendar.MONTH) + "月"
                        + calendar.get(Calendar.DAY_OF_MONTH) + "日";//测试日期


                Pulish_bean bean = new Pulish_bean(0, userid,
                        publishtext, date, money, null, null, null);
                Obeject_toJson obeject_toJson = new Obeject_toJson();
                request_string = obeject_toJson.publish_tojson(bean);//将bean对象转化为json字符串
                //点击button开启一个线程，线程中的网络请求又是一个异步请求，但是这个网络请求的线程非系统的主线程
                //所以必须通过Asynctask或者handle，或者runonuithread的方式来进行UI操作
                new Thread() {
                    @Override
                    public void run() {


                        //final Publish_request publish_request = new Publish_request(Publish.this, request_string, req_progressBar,intent);
                        Handler handler = new Handler(getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Publish.this, "请求", Toast.LENGTH_SHORT).show();
                                mydialog = new Progress_dialog(Publish.this).createLoadingDialog("111");
                                mydialog.show();
                                if(imgCount==1){
                                publish_requestwithOnephoto();}
                                if(imgCount==2){
                                    publish_requestwithTwophoto();
                                }
                                if(imgCount==3){
                                    publish_requestwithThreephoto();
                                }

                                // goTo_indexc_fg();
                            }
                        });


                    }
                }.start();

                break;
            case R.id.chooseimgs://添加图片
                intent = new Intent(this, Test_imageloader.class);
                startActivityForResult(intent, 401);

                break;

            default:
                break;


        }
    }

    /**
     * 用户选择一张图片调用此方法
     */
    public void publish_requestwithOnephoto() {
        final Intent intent = getIntent();

        //final String result;
        String url = "http://192.168.1.110:8080/clocle/servlet/Post_Clocle_JsonData";
        Map map = new HashMap<>();
        map.put("request_string", request_string);
        /*Map map1=new HashMap<>();
        map1.put("Content-Type","application/octet-stream");*/
        int index = img1url.lastIndexOf("/");

        String imgname = img1url.substring(index);
        OkHttpUtils
                .post()
                .addFile("mFile", imgname, new File(img1url))
                .url(url).tag(this).params(map)
                //头像url信息和其他的文字内容存在publish——text中
                .build()
                .execute(new StringCallback()  {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                        mydialog.cancel();
                        Toast.makeText(Publish.this, "失败，请重试", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onResponse(String s, int i) {
//成功的话更新动态页的listview

                        mydialog.cancel();
                        Toast.makeText(Publish.this, "发表成功", Toast.LENGTH_SHORT).show();
                        setResult(301, intent);//发表成功后销毁PUblish的activity，Mainactivity会回调Onresult...方法
                        Publish.this.finish();
                    }
                });

    }

    /**
     * 用户选择两张图片调用此方法
     */
    public void publish_requestwithTwophoto() {
        final Intent intent = getIntent();

        int index1 = img1url.lastIndexOf("/");
        int index2 = img2url.lastIndexOf("/");
        String imgname = img1url.substring(index1);
        String imgname1 = img2url.substring(index2);

        String url = "http://192.168.1.110:8080/clocle/servlet/Post_Clocle_JsonData";
        Map map = new HashMap<>();
        map.put("request_string", request_string);


        OkHttpUtils
                .post()
                .addFile("mFile",imgname,new File(img1url) )
                .addFile("mFile1", imgname1, new File(img2url))
                .url(url).tag(this).params(map)
                //头像url信息和其他的文字内容存在publish——text中
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                        mydialog.cancel();
                        Toast.makeText(Publish.this, "失败，请重试", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onResponse(String s, int i) {
//成功的话更新动态页的listview

                        mydialog.cancel();
                        Toast.makeText(Publish.this, "发表成功", Toast.LENGTH_SHORT).show();
                        setResult(301, intent);//发表成功后销毁PUblish的activity，Mainactivity会回调Onresult...方法
                        Publish.this.finish();
                    }
                });

    }

    /**
     * 用户选择三张图片调用此方法
     */
    public void publish_requestwithThreephoto() {
        final Intent intent = getIntent();

        int index1 = img1url.lastIndexOf("/");
        int index2 = img2url.lastIndexOf("/");
        int index3 = img3url.lastIndexOf("/");
        String imgname = img1url.substring(index1);
        String imgname1 = img2url.substring(index2);
        String imgname2 = img3url.substring(index3);

        String url = "http://192.168.1.110:8080/clocle/servlet/Post_Clocle_JsonData";
        Map map = new HashMap<>();
        map.put("request_string", request_string);


        OkHttpUtils
                .post()
                .addFile("mFile",imgname,new File(img1url) )
                .addFile("mFile1", imgname1, new File(img2url))
                .addFile("mFile2", imgname2, new File(img3url))
                .url(url).tag(this).params(map)
                //头像url信息和其他的文字内容存在publish——text中
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                        mydialog.cancel();
                        Toast.makeText(Publish.this, "失败，请重试", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onResponse(String s, int i) {
//成功的话更新动态页的listview

                        mydialog.cancel();
                        Toast.makeText(Publish.this, "发表成功", Toast.LENGTH_SHORT).show();
                        setResult(301, intent);//发表成功后销毁PUblish的activity，Mainactivity会回调Onresult...方法
                        Publish.this.finish();
                    }
                });

    }



    /**
     * 接收相册选取后传过来的图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<String> url;
        if (requestCode == 401 && resultCode == 401) {
             url=data.getStringArrayListExtra("url");
            Toast.makeText(this,url.size()+"2",Toast.LENGTH_SHORT).show();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new Picked_photo_adapter(this,url));
        }


       /* DecodeSampleBitmapFromUrl dsbf = new DecodeSampleBitmapFromUrl();

        if (requestCode == 401 && resultCode == 401) {
            img1url = data.getStringExtra("img1");

            try {
                Bitmap bm = dsbf.decodeSampleBitmapFromPath(img1url, img1);
                img1url = Utils.savePhoto(bm, Environment
                        .getExternalStorageDirectory().getAbsolutePath().toString() + "/temp_img/", String
                        .valueOf(System.currentTimeMillis()));
                img1.setImageBitmap(bm);
                imgCount = 1;
                return;
            } catch (NoSuchFieldException e) {

            }

        }
        if (requestCode == 401 && resultCode == 402) {
            img1url = data.getStringExtra("img1");
            img2url = data.getStringExtra("img2");

            try {
                Bitmap bm = dsbf.decodeSampleBitmapFromPath(img1url, img1);
                Bitmap bm1 = dsbf.decodeSampleBitmapFromPath(img2url, img2);
                img1url = Utils.savePhoto(bm, Environment
                        .getExternalStorageDirectory().getAbsolutePath().toString() + "/temp_img/", String
                        .valueOf(System.currentTimeMillis()));
                img2url = Utils.savePhoto(bm1, Environment
                        .getExternalStorageDirectory().getAbsolutePath().toString() + "/temp_img/", String
                        .valueOf(System.currentTimeMillis()));
                img1.setImageBitmap(bm);
                img2.setImageBitmap(bm1);
                imgCount = 2;
                return;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == 401 && resultCode == 403) {
            img1url = data.getStringExtra("img1");
            img2url = data.getStringExtra("img2");
            img3url = data.getStringExtra("img3");

            try {
                Bitmap bm = dsbf.decodeSampleBitmapFromPath(img1url, img1);
                Bitmap bm1 = dsbf.decodeSampleBitmapFromPath(img2url, img2);
                Bitmap bm2 = dsbf.decodeSampleBitmapFromPath(img3url, img3);
                img1url = Utils.savePhoto(bm, Environment
                        .getExternalStorageDirectory().getAbsolutePath().toString() + "/temp_img/", String
                        .valueOf(System.currentTimeMillis()));
                img2url = Utils.savePhoto(bm1, Environment
                        .getExternalStorageDirectory().getAbsolutePath().toString() + "/temp_img/", String
                        .valueOf(System.currentTimeMillis()));
                img3url = Utils.savePhoto(bm2, Environment
                        .getExternalStorageDirectory().getAbsolutePath().toString() + "/temp_img/", String
                        .valueOf(System.currentTimeMillis()));
                img1.setImageBitmap(bm);
                img2.setImageBitmap(bm1);
                img3.setImageBitmap(bm2);
                imgCount = 3;
                return;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }*/
    }
}


