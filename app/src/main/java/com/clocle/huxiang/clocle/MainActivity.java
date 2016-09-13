package com.clocle.huxiang.clocle;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.Message;
import com.gesture.MygestureListener;
import com.view.SlidingMenu;

public class MainActivity extends Activity implements View.OnClickListener {


    private TextView tapdongtai;
    private TextView tabfaxian;
    private TextView tabfriend;
    private TextView tabfankui;
    private FragmentManager manager;
    public Index_fg fg1;
    private Faxian_fg fg2;
    private Friend_fg fg3;
    private fankui_fg fg4;
 private DrawerLayout index_drawer;
    private MygestureListener gesture;
    private GestureDetector detector;
    private Boolean needOpenMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        Toast.makeText(this,"测试",Toast.LENGTH_SHORT).show();
        gesture=new MygestureListener();
       detector=new GestureDetector(this,gesture);
        manager = getFragmentManager();

        bindviews();
        tapdongtai.performClick();//模仿一次点击，聚焦在index
        //Toast.makeText(this,"滑动",Toast.LENGTH_SHORT).show();
    }



    private void bindviews() {
        index_drawer= (DrawerLayout) findViewById(R.id.index_drawer);
       // index_drawer.openDrawer(Gravity.LEFT);
        //初始化底部导航菜单
        tapdongtai = (TextView) findViewById(R.id.txt_dongtai);
        tabfriend = (TextView) findViewById(R.id.txt_friend);
        tabfaxian = (TextView) findViewById(R.id.txt_faxian);
        tabfankui = (TextView) findViewById(R.id.txt_fankui);
       /* img1 = (TextView) findViewById(R.id.self_center);
        toptext = (TextView) findViewById(R.id.toptext);//这是顶部的学校名字的实例化对象
        toptag = (Button) findViewById(R.id.tag);//首页发表按钮*/
        //设置监听器
        tapdongtai.setOnClickListener(this);
        tabfriend.setOnClickListener(this);
        tabfaxian.setOnClickListener(this);
        tabfankui.setOnClickListener(this);
       /* toptag.setOnClickListener(new View.OnClickListener() {//发表
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Publish.class);
                startActivityForResult(intent, 301);
            }
        });*/
      /*  //用户点击我的个人中心时触发
        img1.setOnClickListener(new View.OnClickListener() {//我的个人中心
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Self_manager.class);
                startActivity(intent);
            }
        });
*/

    }

    //重置所有文本的选中状态
    private void setSelected() {
        tabfankui.setSelected(false);
        tabfaxian.setSelected(false);
        tabfriend.setSelected(false);
        tapdongtai.setSelected(false);
    }

    //隐藏所有的fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) fragmentTransaction.hide(fg1);
        if (fg2 != null) fragmentTransaction.hide(fg2);
        if (fg3 != null) fragmentTransaction.hide(fg3);
        if (fg4 != null) fragmentTransaction.hide(fg4);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = manager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()) {
            case R.id.txt_dongtai:
                setSelected();
                tapdongtai.setSelected(true);
                if (fg1 == null) {
                    fg1 = new Index_fg();
                    fTransaction.add(R.id.ly_content, fg1);//第一个参数是Fragmentayout布局文件
                    //fg1实例化时便会调用Indexfg的oncreateview方法绘制第一个fg的用户界面
                } else {
                    fTransaction.show(fg1);
                }
                break;
            case R.id.txt_faxian:
                setSelected();

                tabfaxian.setSelected(true);
                if (fg2 == null) {
                    fg2 = new Faxian_fg();
                    fTransaction.add(R.id.ly_content, fg2);//ly_content是承载fg的
                } else {
                    fTransaction.show(fg2);
                }
                break;
            case R.id.txt_friend:
                setSelected();
                tabfriend.setSelected(true);
                if (fg3 == null) {
                    fg3 = new Friend_fg();
                    fTransaction.add(R.id.ly_content, fg3);
                } else {
                    fTransaction.show(fg3);
                }
                break;
            case R.id.txt_fankui:
                setSelected();

                tabfankui.setSelected(true);
                if (fg4 == null) {
                    fg4 = new fankui_fg();
                    fTransaction.add(R.id.ly_content, fg4);
                } else {
                    fTransaction.show(fg4);
                }
                break;

        }
        fTransaction.commit();
    }

    //用户点击注册按钮跳转到注册页面
    public void reg(View view) {
        Intent intent = new Intent(this, Reg.class);
        startActivity(intent);

    }

    public Fragment getFg1() {
        return this.fg1;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == 301) {
            fg1.messages.add(0, new Message("胡翔", "测试", R.mipmap.t8, "9:03"));
            fg1.myadapter.notifyDataSetChanged();
        }

    }
/**
 * 事件的处理
 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=0;
        float y=0;
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            x=event.getX();
        }
        if (event.getAction()==MotionEvent.ACTION_UP){
            y=event.getY();
        }
        if (Math.abs(y-x)>10){
            index_drawer.openDrawer(Gravity.LEFT);
        }
        Log.i("tag","开始事件处理");
     /* if (!detector.onTouchEvent(event)){
          needOpenMenu=true;
          index_drawer.openDrawer(Gravity.LEFT);
      }*/
        return false;

    }
}
