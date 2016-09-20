package com.function;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bean.Clocle_help;
import com.bean.Messages;
import com.clocle.huxiang.clocle.Publish;
import com.clocle.huxiang.clocle.R;
import com.constant.Constant;
import com.httpThread.Clocle_help_AsyncTask;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import tool.RecycleViewAdapter;


/**
 * 圈圈帮主页
 * Created by Administrator on 2016/8/13.
 */
public class Clocle_help_activity extends AppCompatActivity {
    private View viewpager1, viewpager2;
    private ViewPager viewPager;
    private List<View> viewList;//view数组
    private List<Messages> pageList;
    private RecyclerView help_recycleview;
    private RecycleViewAdapter adapter;
    private FloatingActionButton publish_action_bar;
    private PopupMenu menu;
    private SwipeRefreshLayout mrefresh;
    public static int deviceWidth;
    public static int deviceHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bmob初始化

        getDeviceWH();
        setContentView(R.layout.clocle_help_layout);
        Bmob.initialize(Clocle_help_activity.this, "fbd7c66a38b160c5677a774971be3294");
        Toast.makeText(this,"测试4",Toast.LENGTH_SHORT).show();
        pageList=new ArrayList<>();
        initView();
        initData();
    }

    private void initView() {

        viewPager = (ViewPager) findViewById(R.id.viewpager_help);

        LayoutInflater inflater = getLayoutInflater();
        viewpager1 = inflater.inflate(R.layout.viewpager_help1, null);
        publish_action_bar = (FloatingActionButton) viewpager1.findViewById(R.id.publish_action_bar);
        menu = new PopupMenu(this, publish_action_bar);
        Menu menuItem = menu.getMenu();
        menuItem.add(Menu.NONE, Menu.FIRST + 0, 0, "悬赏");
        menuItem.add(Menu.NONE, Menu.FIRST + 1, 1, "求悬赏");

        publish_action_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.show();
            }
        });
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case Menu.FIRST + 0:
                        Toast.makeText(Clocle_help_activity.this, "悬赏",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Clocle_help_activity.this, Publish.class);
                        startActivityForResult(intent, 301);
                        break;
                    case Menu.FIRST + 1:
                        Toast.makeText(Clocle_help_activity.this, "求悬赏",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;

                }
                return false;
            }
        });
        viewpager2 = inflater.inflate(R.layout.viewpager_help2, null);
        help_recycleview = (RecyclerView) viewpager1.findViewById(R.id.id_recycleview);
        mrefresh = (SwipeRefreshLayout) viewpager1.findViewById(R.id.help_refresh);

    }

    private void initData() {
        //测试用list

        //new Clocle_help_AsyncTask().execute(Constant.GET_INDEX_JSON);
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(viewpager1);

        viewList.add(viewpager2);

        final PagerAdapter pageradapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        viewPager.setAdapter(pageradapter);
        help_recycleview.setLayoutManager(new LinearLayoutManager(this));

        help_recycleview.setAdapter(null);

        //滚动监听
        help_recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) help_recycleview.getLayoutManager();
                //获取当前recycleview中的Item的总数

                int totalItemCount = layoutManager.getItemCount();
                //获取最后一个item的位置
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
               // if(totalItemCount)
            }
        });
        mrefresh.setColorSchemeColors(Color.BLACK);
        mrefresh.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mrefresh.setRefreshing(true);
                Toast.makeText(Clocle_help_activity.this,"开始测试",Toast.LENGTH_SHORT).show();
                //首次进入，预加载

                //new Clocle_help_AsyncTask(pageList,Clocle_help_activity.this.mrefresh, Clocle_help_activity.this, help_recycleview).execute(Constant.GET_HELP_JSON);
                BmobQuery<Clocle_help> query=new BmobQuery<Clocle_help>("Clocle_help");
                query.addWhereGreaterThan("peopleNum", 0);
                query.setLimit(2);
                query.findObjects(new FindListener<Clocle_help>() {
                    @Override
                    public void done(List<Clocle_help> list, BmobException e) {
                        //
                        Log.i("返回",list.size()+"");
                        Clocle_help clocle_help=list.get(0);
                        Log.i("返回",clocle_help.getContent());
                        help_recycleview.setAdapter(new RecycleViewAdapter(Clocle_help_activity.this,list));
                    }
                });

            }
        });
        mrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新，我绝对要将当前页面的list给传过去
                new Clocle_help_AsyncTask(pageList,mrefresh, Clocle_help_activity.this, help_recycleview).execute(Constant.GET_HELP_JSON);

            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 301 && resultCode == 301) {

            new Clocle_help_AsyncTask(null,mrefresh, this, help_recycleview).execute(Constant.GET_HELP_JSON);
        }

    }

    /**
     * 获取屏幕宽高（像素）
     */
    public void getDeviceWH() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        deviceWidth = dm.widthPixels;
        deviceHeight = dm.heightPixels;
    }
}
