package com.function;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adapter.ChoosePhotoListAdapter;
import com.adapter.Picked_photo_adapter;
import com.clocle.huxiang.clocle.R;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 分享新鲜事的发表页面
 * Created by Administrator on 2016/11/12.
 */

public class Dynamic_publish extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private GridView gridView;
    private List<PhotoInfo> mPhotoList;//已经选择图片
    private Picked_photo_adapter picked_photo_adapter;
    private Button button;
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.clear();
                mPhotoList.addAll(resultList);
                picked_photo_adapter = new Picked_photo_adapter(Dynamic_publish.this, mPhotoList);
                //picked_photo_adapter.notifyDataSetChanged();
                gridView.setAdapter(picked_photo_adapter);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(Dynamic_publish.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoList = new ArrayList<>();
        setContentView(R.layout.dynamic_publish_layout);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_choose_photo);
        button= (Button) findViewById(R.id.dynamic_publish);
        //发表
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.压缩图片保存在临时文件夹

                //2.上传图片

                //3.保存到数据库
            }
        });
        gridView = (GridView) findViewById(R.id.gv_photo_choose);
        mPhotoList=new ArrayList<>();
        //picked_photo_adapter = new Picked_photo_adapter(this, mPhotoList);
        gridView.setAdapter(null);
        //打开图片选择器
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionConfig config = new FunctionConfig.Builder()
                        .setMutiSelectMaxSize(9)
                        .setSelected(mPhotoList)
                        .setEnablePreview(true)
                        .setEnableCamera(true)
                        .build();
                GalleryFinal.openGalleryMuti(1001, config, mOnHanlderResultCallback);
            }
        });
    }
}
