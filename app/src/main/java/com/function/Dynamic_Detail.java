package com.function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.adapter.Rv_single_imgs_adapter;
import com.bean.Dynamic;
import com.clocle.huxiang.clocle.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 动态详情页面
 * Created by Administrator on 2016/11/20.
 */

public class Dynamic_Detail extends AppCompatActivity{
    private SimpleDraweeView photo;
    private TextView content;
    private TextView nickname;
    private RecyclerView detail_imgs_rv;
    private RecyclerView commentRv;//评论
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_detail_layout);
        //头像
        photo= (SimpleDraweeView) findViewById(R.id.index_rv_photo_detail);
        //内容
        content= (TextView) findViewById(R.id.detail_content_tv);
        //昵称
        nickname= (TextView) findViewById(R.id.dynamic_name_tv_detail);
        //图片
        detail_imgs_rv= (RecyclerView) findViewById(R.id.detail_imgs_rv);
        //线性布局
        detail_imgs_rv.setLayoutManager(new LinearLayoutManager(this));
        //评论RV
        commentRv= (RecyclerView) findViewById(R.id.dynamic_detail_comment_rv);
        commentRv.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        Dynamic mdynamic=(Dynamic) intent.getSerializableExtra("dynamic");
        photo.setImageURI(mdynamic.getUser().getphotoUrl());
        content.setText(mdynamic.getDynamicContent());
        nickname.setText(mdynamic.getUser().getUsername());
        detail_imgs_rv.setAdapter(new Rv_single_imgs_adapter(mdynamic.getImgs(),this));

    }
}
