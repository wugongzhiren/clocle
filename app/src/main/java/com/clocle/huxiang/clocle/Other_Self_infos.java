package com.clocle.huxiang.clocle;

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

import com.constant.Constant;


import java.util.ArrayList;
import java.util.List;

import tool.Viewpager_adapter;

/**
 * Created by Administrator on 2016/8/21.
 */
public class Other_Self_infos extends AppCompatActivity {
    private ViewPager mviewpager;
    private List<View> views;
    private List<String> tabnameList;
    private View tabview1;
    private View tabview2;
    private View tabview3;
    private LayoutInflater inflater;
private TabLayout mtablayout;
    private RecyclerView recycleview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        inflater = getLayoutInflater();
        views = new ArrayList<>();
        tabnameList = new ArrayList<>();
        initviews();



        recycleview1.setLayoutManager(new LinearLayoutManager(this));
       // new Clocle_help_AsyncTask(null,null, this, recycleview1).execute(Constant.GET_HELP_JSON);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("");
        //通过CollapsingToolbarLayout修改字体颜色
        // mCollapsingToolbarLayout.s
        //mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        // mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
    }

    private void initviews() {
        tabview1 = inflater.inflate(R.layout.otherinfo_view1, null);
        recycleview1= (RecyclerView) tabview1.findViewById(R.id.other_info_recyclerView1);
        tabview2 = inflater.inflate(R.layout.otherinfo_view2, null);
        tabview3 = inflater.inflate(R.layout.otherinfo_view3, null);
        views.add(tabview1);
        views.add(tabview2);
        views.add(tabview3);
        tabnameList.add("TA的动态");
        tabnameList.add("详细资料");
        tabnameList.add("TA的相册");
        mviewpager = (ViewPager) findViewById(R.id.other_info_viewpager);
        mtablayout= (TabLayout) findViewById(R.id.other_info_tabs);
        mtablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mviewpager.setCurrentItem(tab.getPosition());//点击哪个就跳转哪个界面
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mtablayout.setTabMode(TabLayout.MODE_FIXED);
        mtablayout.addTab(mtablayout.newTab().setText(tabnameList.get(0)));
        mtablayout.addTab(mtablayout.newTab().setText(tabnameList.get(1)));
        mtablayout.addTab(mtablayout.newTab().setText(tabnameList.get(2)));
        Viewpager_adapter myAdapter = new Viewpager_adapter(views, tabnameList,null);
        mviewpager.setAdapter(myAdapter);
        mtablayout.setupWithViewPager(mviewpager);
        mtablayout.setTabsFromPagerAdapter(myAdapter);
        //mtablayout.setupWithViewPager(mviewpager);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.other_info_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //mToolbar.setMenu(null,null);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
