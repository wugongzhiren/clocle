package com.clocle.huxiang.clocle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.Base_activity;
import com.common_tool.ImageFactory;
import com.constant.Constant;
import com.facebook.drawee.view.SimpleDraweeView;


import java.util.ArrayList;
import java.util.List;

import tool.Bg_blur;
import tool.ImageLoadFresco;
import tool.Viewpager_adapter;

/**
 * Created by Administrator on 2016/8/21.
 */
public class Other_Self_infos extends Base_activity {
private SimpleDraweeView userphoto;
private Bmob_UserBean bean;
    private RecyclerView recycleview;
    private ImageView other_self_bg;
    private Handler handle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap)msg.obj;
            if(bitmap!=null){
                String filepath=ImageFactory.savePhoto(bitmap, Environment
                        .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/temp_img/","userphoto");
                Bitmap userphotoBm=BitmapFactory.decodeFile(filepath);
                //Bg_blur.blur(Other_Self_infos.this,bitmap)
                //Bg_blur.blur(Other_Self_infos.this,bitmap);
                other_self_bg.setImageBitmap(Bg_blur.blur(Other_Self_infos.this,userphotoBm));
                //other_self_bg.setImageBitmap(userphotoBm);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatus();
        //得到用户信息
        bean=(Bmob_UserBean) getIntent().getSerializableExtra("userbean");
         //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.t6);
        //index_photo=BitmapFactory.decodeResource(getResources(),R.mipmap.reg);
       // Bitmap bm=Bg_blur.blur(this,bitmap);
        setContentView(R.layout.other_self_info);
        initView();
        other_self_bg= (ImageView) findViewById(R.id.other_self_bg);
        //other_self_bg.setImageBitmap(bm);
       // initviews();



        //recycleview1.setLayoutManager(new LinearLayoutManager(this));
       // new Clocle_help_AsyncTask(null,null, this, recycleview1).execute(Constant.GET_HELP_JSON);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
       // CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
       // mCollapsingToolbarLayout.setTitle("");
        //通过CollapsingToolbarLayout修改字体颜色
        // mCollapsingToolbarLayout.s
        //mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        // mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
    }

    private void initView() {
        //用户头像
        userphoto= (SimpleDraweeView) findViewById(R.id.photo);
        userphoto.setImageURI(bean.getphotoUrl());
        ImageLoadFresco.getFrescoCacheBitmap(handle, Uri.parse(bean.getphotoUrl()),this);
    }

    private void getUserDataFromServer(){
        //从服务器加载数据
    }


}
