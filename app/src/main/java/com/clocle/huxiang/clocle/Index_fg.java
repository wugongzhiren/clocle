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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 */
public class Index_fg extends Fragment {
    private List<Message> messages;
    private ListView messageListview;
    private Index_adapter myadapter;
    private Context listcontext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fg, container, false);

        messageListview = (ListView) view.findViewById(R.id.list_item);
        listcontext=getActivity();
        messages = new ArrayList<Message>();
        for (int i = 0; i < 10; i++) {
            messages.add(new Message("用户"+i, "7月" + i + "号真热", R.mipmap.ic_launcher));
        }
        messageListview.setAdapter(new Index_adapter(messages,listcontext));
        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        //txt_content.setText(content);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);



    }


}

