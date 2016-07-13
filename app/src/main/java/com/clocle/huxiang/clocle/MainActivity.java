package com.clocle.huxiang.clocle;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.view.SlidingMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SlidingMenu slidingMenu;
    private TextView toptext;
    private TextView tapdongtai;
    private TextView tabfaxian;
    private TextView tabfriend;
    private TextView tabfankui;
    private FragmentManager manager;
    private Index_fg fg1;
    private Faxian_fg fg2;
    private Friend_fg fg3;
    private fankui_fg fg4;
    private Button toptag;
private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
mcontext=this;
        setContentView(R.layout.activity_main);

        slidingMenu = (SlidingMenu) findViewById(R.id.slidemenu);
        manager = getFragmentManager();
        bindviews();
        tapdongtai.performClick();//模仿一次点击，聚焦在index
        //Toast.makeText(this,"滑动",Toast.LENGTH_SHORT).show();
    }

    //基于回调的click方法，布局中onclick属性关联的方法
    public void openmenu(View view) {
        slidingMenu.openMenu();
    }

    private void bindviews() {
        //初始化底部导航菜单
        tapdongtai = (TextView) findViewById(R.id.txt_dongtai);
        tabfriend = (TextView) findViewById(R.id.txt_friend);
        tabfaxian = (TextView) findViewById(R.id.txt_faxian);
        tabfankui = (TextView) findViewById(R.id.txt_fankui);
        toptext = (TextView)findViewById(R.id.toptext);//这是顶部的学校名字的实例化对象
        toptag=(Button)findViewById(R.id.tag);
        //设置监听器
        tapdongtai.setOnClickListener(this);
        tabfriend.setOnClickListener(this);
        tabfaxian.setOnClickListener(this);
        tabfankui.setOnClickListener(this);


    }

    //重置所有文本的选中状态
    private void setSelected(){
        tabfankui.setSelected(false);
        tabfaxian.setSelected(false);
        tabfriend.setSelected(false);
        tapdongtai.setSelected(false);
    }
    //隐藏所有的fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = manager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_dongtai:
                setSelected();
                tapdongtai.setSelected(true);
                if(fg1==null){
                    fg1=new Index_fg();
                    fTransaction.add(R.id.ly_content,fg1);//第一个参数是Fragmentayout布局文件
                    //fg1实例化时便会调用Indexfg的oncreateview方法绘制第一个fg的用户界面
                }
                else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.txt_faxian:
                setSelected();
                toptext.setText("发现更多精彩");
                toptag.setText("礼品兑换中心");
                tabfaxian.setSelected(true);
                if(fg2==null){
                    fg2=new Faxian_fg();
                    fTransaction.add(R.id.ly_content,fg2);//ly_content是承载fg的
                }
                else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.txt_friend:
                setSelected();
                tabfriend.setSelected(true);
                if(fg3==null){
                    fg3=new Friend_fg();
                    fTransaction.add(R.id.ly_content,fg3);
                }
                else {
                    fTransaction.show(fg3);
                }
                break;
            case R.id.txt_fankui:
                setSelected();
                toptext.setText("反馈");
                tabfankui.setSelected(true);
                if(fg4==null){
                    fg4=new fankui_fg();
                    fTransaction.add(R.id.ly_content,fg4);
                }
                else {
                    fTransaction.show(fg4);
                }
                break;

        }
        fTransaction.commit();
    }
}
