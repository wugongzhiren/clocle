package com.clocle.huxiang.clocle;


import android.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.Message;
import com.gesture.MygestureListener;




public class MainActivity extends AppCompatActivity {


    private TextView tapdongtai;
    private TextView tabfaxian;
    private TextView tabfriend;
    private TextView tabfankui;
    private FragmentManager manager;
    public Index_fg fg1;
    private Faxian_fg fg2;
    private Friend_fg fg3;
    private fankui_fg fg4;
    private SlidingPaneLayout paneLayout;
    private MygestureListener gesture;
    private GestureDetector detector;
    private Boolean needOpenMenu;
    private FragmentTabHost tabHost;

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        inflater = getLayoutInflater();
        Toast.makeText(this, "测试", Toast.LENGTH_SHORT).show();
       /* gesture=new MygestureListener();
       detector=new GestureDetector(this,gesture);*/
        // manager = getFragmentManager();
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(),
                R.id.index_fragment);
        // 2. 新建TabSpec
        TabHost.TabSpec spec1 = tabHost.newTabSpec("TAB1");
        View view1 = inflater.inflate(R.layout.index_bottom, null);
        TextView tabtext1 = (TextView) view1.findViewById(R.id.tabtext);
        ImageView tabimg1 = (ImageView) view1.findViewById(R.id.tabimg);
        tabimg1.setImageResource(R.mipmap.tab1);
        tabtext1.setText("新鲜事");
        //spec需要一个indicator,indic需要view
        spec1.setIndicator(view1);
        tabHost.addTab(spec1, Index_fg.class, null);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("TAB2");
        View view2 = inflater.inflate(R.layout.index_bottom, null);
        TextView tabtext2 = (TextView) view2.findViewById(R.id.tabtext);
        ImageView tabimg2 = (ImageView) view2.findViewById(R.id.tabimg);
        tabimg2.setImageResource(R.mipmap.friend);
        tabtext2.setText("好友");
        //spec需要一个indicator,indic需要view
        spec2.setIndicator(view2);

        // 3. 添加TabSpec

        tabHost.addTab(spec2, Friend_fg.class, null);


        TabHost.TabSpec spec3 = tabHost.newTabSpec("TAB3");
        View view3 = inflater.inflate(R.layout.index_bottom, null);
        TextView tabtext3 = (TextView) view3.findViewById(R.id.tabtext);
        ImageView tabimg3 = (ImageView) view3.findViewById(R.id.tabimg);
        tabimg3.setImageResource(R.mipmap.faxian);
        tabtext3.setText("发现");
        //spec需要一个indicator,indic需要view
        spec3.setIndicator(view3);
        tabHost.addTab(spec3, Faxian_fg.class, null);


        TabHost.TabSpec spec4 = tabHost.newTabSpec("TAB4");
        View view4 = inflater.inflate(R.layout.index_bottom, null);
        TextView tabtext4 = (TextView) view4.findViewById(R.id.tabtext);
        ImageView tabimg4 = (ImageView) view4.findViewById(R.id.tabimg);
        tabimg4.setImageResource(R.mipmap.fankui);
        tabtext4.setText("反馈");
        //spec需要一个indicator,indic需要view
        spec4.setIndicator(view4);

        // 3. 添加TabSpec

        tabHost.addTab(spec4, fankui_fg.class, null);

       /* // 2. 新建TabSpec
        spec = tabhost.newTabSpec(TAB_CONTACT);
        contactIndicator = new TabIndicatorView(this);
        contactIndicator.setTabIcon(R.drawable.tab_icon_contact_normal,
                R.drawable.tab_icon_contact_focus);
        contactIndicator.setTabTitle("通讯录");
        contactIndicator.setTabUnreadCount(10);
        spec.setIndicator(contactIndicator);
        // 3. 添加TabSpec
        tabhost.addTab(spec, ContactFra.class, null);

        // 2. 新建TabSpec
        spec = tabhost.newTabSpec(TAB_DISCOVER);
        discoverIndicator = new TabIndicatorView(this);
        discoverIndicator.setTabIcon(R.drawable.tab_icon_discover_normal,
                R.drawable.tab_icon_discover_focus);
        discoverIndicator.setTabTitle("发现");
        discoverIndicator.setTabUnreadCount(10);
        spec.setIndicator(discoverIndicator);
        // 3. 添加TabSpec
        tabhost.addTab(spec, DiscoverFra.class, null);

        // 2. 新建TabSpec
        spec = tabhost.newTabSpec(TAB_ME);
        meIndicator = new TabIndicatorView(this);
        meIndicator.setTabIcon(R.drawable.tab_icon_me_normal,
                R.drawable.tab_icon_me_focus);
        meIndicator.setTabTitle("我");
        meIndicator.setTabUnreadCount(10);
        spec.setIndicator(meIndicator);
        // 3. 添加TabSpec
        tabhost.addTab(spec, MeFra.class, null);

        // 去掉分割线
        tabhost.getTabWidget().setDividerDrawable(android.R.color.white);

        // 初始化 tab选中
        tabhost.setCurrentTabByTag(TAB_CHAT);
        chatIndicator.setTabSelected(true);

        // 设置tab切换的监听
        tabhost.setOnTabChangedListener(this);*/
        bindviews();
        // tapdongtai.performClick();//模仿一次点击，聚焦在index
        //Toast.makeText(this,"滑动",Toast.LENGTH_SHORT).show();
    }


    private void bindviews() {
        paneLayout = (SlidingPaneLayout) findViewById(R.id.index_drawer);
        paneLayout.setParallaxDistance(200);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == 301) {
            fg1.messages.add(0, new Message("胡翔", "测试", R.mipmap.t8, "9:03"));
            fg1.myadapter.notifyDataSetChanged();
        }

    }

}
