package com.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.Base_activity;
import com.adapter.Album_Rv_ItemAdapter;
import com.clocle.huxiang.clocle.R;

import java.io.File;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;

/**
 * 查看相册界面（包括个人和他人）
 * Created by Administrator on 2016/11/28.
 */

public class Album extends Base_activity {
    private RecyclerView albumRv;
    private Button button;
    protected static Uri tempUri;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatus();
        setContentView(R.layout.album_layout);
        button= (Button) findViewById(R.id.dynamic_publish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Album.this);
                builder.setTitle("设置头像");
                String[] items = {"选择本地照片", "拍照"};
                builder.setNegativeButton("取消", null);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://从本地选取
                                Intent openAlbumIntent = new Intent(
                                        Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                startActivityForResult(openAlbumIntent, 0);
                                break;
                            case 1: // 拍照
                                Intent openCameraIntent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                tempUri = Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "myphoto.jpg"));
                                // 指定照片保存路径（SD卡），myphoto.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                startActivityForResult(openCameraIntent, 1);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        albumRv= (RecyclerView) findViewById(R.id.album_rv);
        albumRv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        albumRv.setAdapter(new Album_Rv_ItemAdapter(null ,this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case 1:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case 0:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case 2:
                    if (data != null) {
                        //上传图片
                    }
                    break;
            }
        }
    }
    //裁剪图片
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            //Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        //intent.putExtra("aspectX", 1);
        //intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        //intent.putExtra("outputX", 150);
        //intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }
}
