package com.clocle.huxiang.clocle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bean.Clocle_help;
import com.common_tool.ImageFactory;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import test.Test_imageloader;
import tool.Progress_dialog;
import tool.Utils;

/**
 * 圈圈帮的悬赏页面（还需要优化）
 * Created by Administrator on 2016/7/26.
 */
public class Publish extends Activity implements View.OnClickListener {

    private EditText publish_text;
    private EditText money_text;
    private Button publish_button;
    private Button chooseimgs;
    public String request_string;
    public Dialog mydialog;
    private int imgCount;
    private int deviceW;
    private int deviceH;
    private static final String addphotourl = "res://com.clocle.huxiang.clocle/" + Uri.parse(R.mipmap.addphoto + "");
    private RecyclerView recyclerView;
    private Picked_photo_adapter picked_photo_adapter;
    private ArrayList<String> url;//要上传照片的URL
    private ArrayList<String> pickedurl;//fresco加载的url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_layout);
        //Bmob.initialize(this, "fbd7c66a38b160c5677a774971be3294");
        Toast.makeText(this, "测试2", Toast.LENGTH_SHORT).show();
        url = new ArrayList<>();
        pickedurl=new ArrayList<>();
        url.add(addphotourl);
        pickedurl.add(addphotourl);
        picked_photo_adapter = new Picked_photo_adapter(this, pickedurl);
        initviews();
        //获取屏幕的宽高
        getDeviceWH();
    }

    private void initviews() {
        publish_text = (EditText) findViewById(R.id.publish_text);

        money_text = (EditText) findViewById(R.id.money_text);
        publish_button = (Button) findViewById(R.id.publish_button);
      /*  img1 = (ImageView) findViewById(R.id.help_img1);
        img2 = (ImageView) findViewById(R.id.help_img2);
        img3 = (ImageView) findViewById(R.id.help_img3);*/
        recyclerView = (RecyclerView) findViewById(R.id.picked_photo);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(picked_photo_adapter);
        publish_text.setOnClickListener(this);
        money_text.setOnClickListener(this);
        publish_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_button://发表
                //首先上传照片
                final Bmob_UserBean bean = BmobUser.getCurrentUser(Bmob_UserBean.class);
                final String publishcontent = publish_text.getText().toString();//获取发表内容
                final int money = Integer.parseInt(money_text.getText().toString());//获取悬赏金额
                //int userid = 1;//实验用户id
                int urlsize = url.size();
                Log.i("tag", "onClick: "+urlsize);
                if (url.size() > 1) {
                    final String urlArr[] = new String[urlsize - 1];
                    for (int i = 0; i < urlArr.length; i++) {
                        File file = new File(Environment
                                .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/temp_img/");
                        //在这里清空文件夹的文件
                      Bitmap bmp=new ImageFactory().getBitmap(url.get(i));


                        String urlImg = Utils.savePhoto(bmp, Environment
                                .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/temp_img/", String
                                .valueOf(System.currentTimeMillis()));
                        urlArr[i] = urlImg;
                       if(bmp != null && !bmp.isRecycled()){
                           bmp.recycle();
                           bmp=null;
                           System.gc();
                        }
                    }
                    final Dialog mydialog=new Progress_dialog(Publish.this).createLoadingDialog("111");
                    BmobFile.uploadBatch(urlArr, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> list, List<String> list1) {
if(list1.size()==urlArr.length){
    Toast.makeText(Publish.this,"图片上传成功",Toast.LENGTH_SHORT).show();

    mydialog.show();
   //插入数据到圈圈帮帖子表
    Clocle_help help=new Clocle_help();
    help.setContent(publishcontent);
    help.setImgs(list1);
    help.setPeopleNum(2);
    help.setTag("羽毛球");
    help.setSum_clocle_money(money);
    help.setBmob_userBean(bean);
    help.save(new SaveListener<String>() {
        @Override
        public void done(String s, BmobException e) {
            if(e==null){
                mydialog.dismiss();
            }
        }
    });
    //help.setBmob_userBean(bean);

}
                        }

                        @Override
                        public void onProgress(int i, int i1, int i2, int i3) {

                        }

                        @Override
                        public void onError(int i, String s) {
                            Toast.makeText(Publish.this,s,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //用户没有选择添加图片
                else {
                    Clocle_help help=new Clocle_help();
                    help.setContent(publishcontent);
                    help.setPeopleNum(2);
                    help.setTag("羽毛球");
                    help.setSum_clocle_money(money);
                    help.setBmob_userBean(bean);
                    final Dialog mydialog=new Progress_dialog(Publish.this).createLoadingDialog("111");
                    help.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                mydialog.dismiss();
                            }
                        }
                    });
                }


              //  Pulish_bean bean = new Pulish_bean(0, userid,
            //  //          publishtext, date, money, null, null, null);
              //  Obeject_toJson obeject_toJson = new Obeject_toJson();
             //   request_string = obeject_toJson.publish_tojson(bean);//将bean对象转化为json字符串
                //点击button开启一个线程，线程中的网络请求又是一个异步请求，但是这个网络请求的线程非系统的主线程
                //所以必须通过Asynctask或者handle，或者runonuithread的方式来进行UI操作
            /*    new Thread() {
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
                                if (imgCount == 1) {
                                    publish_requestwithOnephoto();
                                }
                                if (imgCount == 2) {
                                    publish_requestwithTwophoto();
                                }
                                if (imgCount == 3) {
                                    publish_requestwithThreephoto();
                                }

                                // goTo_indexc_fg();
                            }
                        });


                    }
                }.start();*/

                break;


            default:
                break;


        }
    }

   /* *//**
     * 用户选择一张图片调用此方法
     *//*
    public void publish_requestwithOnephoto() {
        final Intent intent = getIntent();

        //final String result;
        String url = "http://192.168.1.110:8080/clocle/servlet/Post_Clocle_JsonData";
        Map map = new HashMap<>();
        map.put("request_string", request_string);
        *//*Map map1=new HashMap<>();
        map1.put("Content-Type","application/octet-stream");*//*
        int index = img1url.lastIndexOf("/");

        String imgname = img1url.substring(index);
        OkHttpUtils
                .post()
                .addFile("mFile", imgname, new File(img1url))
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

    *//**
     * 用户选择两张图片调用此方法
     *//*
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
                .addFile("mFile", imgname, new File(img1url))
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

    *//**
     * 用户选择三张图片调用此方法
     *//*
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
                .addFile("mFile", imgname, new File(img1url))
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
*/

    /**
     * 接收相册选取后传过来的图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      ArrayList<String> tempurl;

        if (requestCode == 401 && resultCode == 401) {
            tempurl = data.getStringArrayListExtra("url");
            url.clear();
            pickedurl.clear();
            for (int i = 0; i < tempurl.size(); i++) {

                pickedurl.add("file://" + Uri.parse(tempurl.get(i)));
                url.add(Uri.parse(tempurl.get(i))+"");
            }
            url.add(addphotourl);
            pickedurl.add(addphotourl);
            //   url.add("file://"+Uri.parse(pickedurl.get(1)));
            Toast.makeText(this, url.size() + "2", Toast.LENGTH_SHORT).show();

            picked_photo_adapter.notifyDataSetChanged();
            //recyclerView.setAdapter(new Picked_photo_adapter(this,url));
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

    public void getDeviceWH() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        deviceW = dm.widthPixels;
        deviceH = dm.heightPixels;
    }

    class Picked_photo_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private LayoutInflater inflater;

        private int marginleft;
        private int photoW;
        private int type0 = 0;//图片
        private int type1 = 1;//添加图片

        public Picked_photo_adapter(Context context, ArrayList<String> urlList) {


            inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == type0) {
                View itemview = inflater.inflate(R.layout.picked_photo_item, parent, false);

                return new ViewHolder(itemview);
            } else {
                View itemview = inflater.inflate(R.layout.addphoto, parent, false);
                return new ViewHolderWithAddPhoto(itemview);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            holder.itemView.setTag(position);
            if (holder instanceof ViewHolder) {


                ((ViewHolder) holder).mimageButton.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).pickedphotoView.setImageURI(pickedurl.get(position));
            } else if (holder instanceof ViewHolderWithAddPhoto) {
                ((ViewHolderWithAddPhoto) holder).mimageview.setImageResource(R.mipmap.addphoto);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == (pickedurl.size() - 1)) {
                return type1;
            } else {
                return type0;
            }
        }

        @Override
        public int getItemCount() {
            return pickedurl.size();
        }
    }

    class ViewHolderWithAddPhoto extends RecyclerView.ViewHolder {
        private ImageView mimageview;

        public ViewHolderWithAddPhoto(View itemView) {
            super(itemView);
            mimageview = (ImageView) itemView.findViewById(R.id.addphoto);
            int width = mimageview.getLayoutParams().width = (getResources().getDisplayMetrics().widthPixels - 15) / 4;
            mimageview.getLayoutParams().height = width;
            mimageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Publish.this, Test_imageloader.class);
                    startActivityForResult(intent, 401);
                }
            });

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView pickedphotoView;
        public ImageButton mimageButton;

        public ViewHolder(final View itemView) {
            super(itemView);
            pickedphotoView = (SimpleDraweeView) itemView.findViewById(R.id.picked_photo_item);
            //设置pickedphotoView的宽高一样，适应屏幕大小
            int photoW;
            photoW = pickedphotoView.getLayoutParams().width = (getResources().getDisplayMetrics().widthPixels - 15) / 4;
            pickedphotoView.getLayoutParams().height = photoW;
            // Log.i("width" ,mcontext.getResources().getDisplayMetrics().widthPixels+"");
            //marginleft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mcontext.getResources().getDisplayMetrics());
            // Log.i("width1" ,marginleft+"");
            mimageButton = (ImageButton) itemView.findViewById(R.id.photo_clear);
            mimageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickedurl.remove((int) itemView.getTag());
                    picked_photo_adapter.notifyDataSetChanged();
                }
            });
        }
    }


}


