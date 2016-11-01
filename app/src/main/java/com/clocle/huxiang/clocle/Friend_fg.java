package com.clocle.huxiang.clocle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.MyfragmentPagerAdapter;
import com.fragment.ConversationlistFg;
import com.fragment.MyfriendFg;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/7.
 */
public class Friend_fg extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<String> titles;//viewpager的标题
    private ArrayList<Fragment> fragments;//装载进viewpager的fragment
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.friend_fg,container,false);
        titles=new ArrayList<>();
        fragments=new ArrayList<>();
        initViews();
        MyfragmentPagerAdapter adapter = new MyfragmentPagerAdapter(getActivity().getSupportFragmentManager(), titles, fragments);

        viewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabLayout.setupWithViewPager(viewPager);
        //使用ViewPager的适配器
        tabLayout.setTabsFromPagerAdapter(adapter);
        return view;
    }

    private void initViews() {
        tabLayout= (TabLayout) view.findViewById(R.id.friend_tab);
        viewPager= (ViewPager) view.findViewById(R.id.friend_viewpager);

        titles.add("消息");
        titles.add("好友");

        fragments.add(new ConversationlistFg());
        fragments.add(new MyfriendFg());

        //tabLayout.addTab();
       /* viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            private String[] mTitles = new String[]{"消息", "好友"};
            @Override
            public Fragment getItem(int position) {
                if(position == 1){
                    return new MyfriendFg();
                }

                return new ConversationlistFg();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);*/
    }
}
