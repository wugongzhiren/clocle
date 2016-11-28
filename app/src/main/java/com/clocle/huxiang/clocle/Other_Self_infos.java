package com.clocle.huxiang.clocle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import com.constant.Constant;


import java.util.ArrayList;
import java.util.List;

import tool.Bg_blur;
import tool.Viewpager_adapter;

/**
 * Created by Administrator on 2016/8/21.
 */
public class Other_Self_infos extends Base_activity {
    private ViewPager mviewpager;
    private List<View> views;
    private List<String> tabnameList;
    private View tabview1;
    private View tabview2;
    private View tabview3;
    private LayoutInflater inflater;
private TabLayout mtablayout;
    private RecyclerView recycleview1;
    private ImageView other_self_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatus();
         Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.t6);
        //index_photo=BitmapFactory.decodeResource(getResources(),R.mipmap.reg);
        Bitmap bm=Bg_blur.blur(this,bitmap);
      /*  getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }*/

        setContentView(R.layout.other_self_info);
        other_self_bg= (ImageView) findViewById(R.id.other_self_bg);
        other_self_bg.setImageBitmap(bm);
        inflater = getLayoutInflater();
        views = new ArrayList<>();
        tabnameList = new ArrayList<>();
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


}
