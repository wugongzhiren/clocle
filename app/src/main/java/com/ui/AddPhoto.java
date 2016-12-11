package com.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.Base_activity;
import com.clocle.huxiang.clocle.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/11.
 */

public class AddPhoto extends Base_activity {
    private  RecyclerView mrecycleView;
    private ArrayList<String> url=new ArrayList<>();//要上传照片的URL
    private static final String addphotourl = "res://com.clocle.huxiang.clocle/" + Uri.parse(R.mipmap.addphoto + "");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.album_layout);
        mrecycleView= (RecyclerView) findViewById(R.id.album_rv);
        mrecycleView.setLayoutManager(new GridLayoutManager(this, 4));
        url.add(addphotourl);
    }
}
