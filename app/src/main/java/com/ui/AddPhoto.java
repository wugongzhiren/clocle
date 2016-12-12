package com.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.Base_activity;
import com.adapter.Picked_photo_adapter;
import com.adapter.Selected_Photo_Adapter;
import com.clocle.huxiang.clocle.Publish;
import com.clocle.huxiang.clocle.R;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/12/11.
 */

public class AddPhoto extends Base_activity {
    private  RecyclerView mrecycleView;
    private List<PhotoInfo> selectImg;
    private Selected_Photo_Adapter picked_photo_adapter;
    private ArrayList<String> url=new ArrayList<>();//要上传照片的URL
    private static final String addphotourl = "res://com.clocle.huxiang.clocle/" + Uri.parse(R.mipmap.addphoto + "");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url.add(addphotourl);
        selectImg= (List<PhotoInfo>) getIntent().getSerializableExtra("selectImgs");
        setContentView(R.layout.album_layout);
        for(int i=0;i<selectImg.size();i++){
            url.add(selectImg.get(i).getPhotoPath());
        }
        mrecycleView= (RecyclerView) findViewById(R.id.album_rv);
        mrecycleView.setLayoutManager(new GridLayoutManager(this, 3));

        picked_photo_adapter = new Selected_Photo_Adapter(this, url);
        mrecycleView.setAdapter(picked_photo_adapter);
    }
}
