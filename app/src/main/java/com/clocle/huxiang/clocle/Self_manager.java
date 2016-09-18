package com.clocle.huxiang.clocle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.Call;
import tool.Utils;

/**
 * 个人中心界面
 * Created by Administrator on 2016/7/16.
 */
public class Self_manager extends Activity implements View.OnClickListener {
    private TextView edit_text;//个人中心的编辑
    private ImageView change_photo;
    protected static Uri tempUri;
    private String userPhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_manager);
        Bmob.initialize(this, "fbd7c66a38b160c5677a774971be3294");
        bindViews();
    }

    private void bindViews() {
        edit_text = (TextView) findViewById(R.id.edit_text);
        change_photo = (ImageView) findViewById(R.id.self_photo);
        edit_text.setOnClickListener(this);
        change_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.self_photo:
                choosePhoto_dialog();
                break;
            case R.id.edit_text:
                Toast.makeText(this, "跳转至个人资料卡", Toast.LENGTH_SHORT);
                Intent intent = new Intent(Self_manager.this, Self_Info.class);
                startActivity(intent);
        }

    }

    protected void choosePhoto_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    //裁剪图片
    protected void startPhotoZoom(Uri uri) {
        Log.i("tag", "裁剪");
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    /**
     * 保存裁剪之后的图片数据,将裁剪后的照片设置在图像页面
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Log.i("tag", "保存裁剪");
        Bundle extras = data.getExtras();
        if (extras != null) {

            final Bitmap photo = Utils.toRoundBitmap((Bitmap) extras.getParcelable("data"), tempUri); // 这个时候的图片已经被处理成圆形的了
            String imagePath = Utils.savePhoto(photo, Environment
                    .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/myphoto/", "myphoto");
            change_photo.setImageBitmap(photo);
          File file = new File(imagePath);

            final BmobFile bmobFile = new BmobFile(file);
           final Bmob_UserBean newuser = new Bmob_UserBean();
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        //返回上传成功后的图片URL
                        userPhotoUrl = bmobFile.getFileUrl();
                        newuser.setphotoUrl(userPhotoUrl);
                        Log.i("shangchuangtag", userPhotoUrl);
                        Bmob_UserBean userBean = BmobUser.getCurrentUser(Bmob_UserBean.class);
                        newuser.update(userBean.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(Self_manager.this, "头像更换成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Self_manager.this, "失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Self_manager.this, "头像上传失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//将上传成功后的图片url插入到user表


/*
            newuser.setUsername("糖糖没有果2");
            newuser.setphotoUrl("http://bmob-cdn-6342.b0.upaiyun.com/2016/09/18/f7451a7915a94788bd73c68f8e7e4bc7.png");

            Bmob_UserBean userBean = BmobUser.getCurrentUser(Bmob_UserBean.class);
            newuser.update(userBean.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(Self_manager.this, "头像更换成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Self_manager.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

          /*  new Thread() {
                @Override
                public void run() {


                    uploadPic(photo);//上传到服务器


                }
            }.start();*/

        }
    }

    /**
     * 保存png图片
     * 上传处理后的照片到服务器
     *
     * @param bitmap
     */

    private void uploadPic(Bitmap bitmap) {
        String url = "http://192.168.1.110:8080/clocle/servlet/File_Upload";
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle_img/", String
                .valueOf(System.currentTimeMillis()));
        File file = new File(imagePath);
        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .url(url)
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(String s, int i) {

                    }
                });
/*File myphoto=new File(imagePath);
        MultipartBody body = new MultipartBody.Builder("AaB03x")
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", null, new MultipartBody.Builder("BbC04y")
                        .addPart(Headers.of("Content-Disposition", "form-data; filename=\"myphoto.png\""),
                                RequestBody.create(MediaType.parse("image/png"), myphoto))
                        .build())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });*/












        /*if (imagePath!=null) {
            File myphoto=new File(imagePath);

            OkHttpUtils
                    .post()
                    .addFile("myphoto","myphoto.png",myphoto)
                    .url(url).build()
                    .execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int i) {

                }

                @Override
                public void onResponse(String s, int i) {

                }
            });
        }*/
    }
    /**
     * 将bitmap转化为file文件
     *//*
        public File bitmapTo_File(Bitmap bm, String fileName) throws IOException {
            String path = getSDPath() +"/clocleImg/";
            File dirFile = new File(path);
            if(!dirFile.exists()){
                dirFile.mkdir();
            }
            File myCaptureFile = new File(path + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            return myCaptureFile;
        }

    *//**
     * 判断SD卡是否存在，存在获取根目录
     * @return
     *//*
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }*/
}

