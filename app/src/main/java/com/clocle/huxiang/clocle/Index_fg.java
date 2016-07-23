package com.clocle.huxiang.clocle;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bean.Message;

import tool.Index_adapter;
import tool.Myadpter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 */
public class Index_fg extends Fragment {
    private List<Message> messages;//动态的数据
    private ListView messageListview;
    private Index_adapter myadapter;
    private Context listcontext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fg, container, false);

        messageListview = (ListView) view.findViewById(R.id.list_item_index);
        listcontext = getActivity();
        messages = new ArrayList<Message>();

        messages.add(new Message("艾苏北郡的小铁匠", "万能baseadapter实验，成功了", R.mipmap.t1, "7月17日 17:25", R.mipmap.qiangdan));
        messages.add(new Message("落泪星辰", "最新的电影，谁看", R.mipmap.t2, "7月17日 17:25", R.mipmap.qiangdan));
        messages.add(new Message("七厘米蔚蓝", "谁有爱奇艺会员，记得分享哦！！", R.mipmap.t7, "7月17日 17:15", R.mipmap.qiangdan));
        messages.add(new Message("无功至人", "打麻将三缺一，速度的来", R.mipmap.t3, "7月17日 17:25", R.mipmap.qiangdan));
        messages.add(new Message("小盆友", "安徽三熊孩子偷葫芦想当葫芦娃", R.mipmap.t9, "7月17日 17:15", R.mipmap.qiangdan));
        messages.add(new Message("一壶冰啤酒", "一起去喝酒", R.mipmap.t4, "7月17日 17:05", R.mipmap.qiangdan));
        messages.add(new Message("空心菜", "秀一下爪子", R.mipmap.t10, "7月17日 16:25", R.mipmap.qiangdan));
        messages.add(new Message("寻浅", "我要写一段很长的话，很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的长到第4行", R.mipmap.t5, "7月17日 12:25", R.mipmap.qiangdan));
        messages.add(new Message("团团酱", "街拍那么美", R.mipmap.t6, "7月17日 12:15", R.mipmap.qiangdan));
        messages.add(new Message("你说我傻", "你才傻！！！", R.mipmap.t8, "7月17日 12:05", R.mipmap.qiangdan));
       // messageListview.setAdapter(new Index_adapter(messages, listcontext));
        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        messageListview.setAdapter(new Myadpter(listcontext,messages));
        //txt_content.setText(content);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);


    }


}

