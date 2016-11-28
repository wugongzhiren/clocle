package com.function;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.adapter.Dynamic_Comment_Adapter;
import com.adapter.Dynamic_Rv_Adapter;
import com.adapter.Rv_single_imgs_adapter;
import com.bean.Dynamic;
import com.bean.Dynamic_Comment;
import com.clocle.huxiang.clocle.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
    private List<Dynamic_Comment> dynamic_commentList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dynamic_detail_layout);

        dynamic_commentList=new ArrayList<>();
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

        //加载评论列表
        BmobQuery<Dynamic_Comment> query=new BmobQuery<>();
        query.addWhereEqualTo("dynamicID", mdynamic.getObjectId());
        query.setLimit(1000);
        query.include("commentuser");
        query.findObjects(new FindListener<Dynamic_Comment>() {
            @Override
            public void done(List<Dynamic_Comment> list, BmobException e) {
                if(e==null){
                    dynamic_commentList.addAll(list);
                    commentRv.setAdapter(new Dynamic_Comment_Adapter(Dynamic_Detail.this,dynamic_commentList));
                }
            }
        });

        photo.setImageURI(mdynamic.getUser().getphotoUrl());
        content.setText(mdynamic.getDynamicContent());
        nickname.setText(mdynamic.getUser().getUsername());
        detail_imgs_rv.setAdapter(new Rv_single_imgs_adapter(mdynamic.getImgs(),this));

    }
}
