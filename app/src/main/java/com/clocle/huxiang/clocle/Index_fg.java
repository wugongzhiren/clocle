package com.clocle.huxiang.clocle;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bean.Dynamic;
import com.bean.Message;
import com.bean.Messages;
import com.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tool.Myadpter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by Administrator on 2016/7/6.
 */
public class Index_fg extends android.support.v4.app.Fragment {
    public   List<Message> messages= new ArrayList<Message>();;//动态的数据
    public  Myadpter myadapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fg, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.main_rv);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager( 2 , StaggeredGridLayoutManager. VERTICAL ); //两列，纵向排列
        recyclerView.setLayoutManager(mLayoutManager) ;
        List<Dynamic> dynamics=new ArrayList<>();
        Dynamic dynamic=new Dynamic();
        dynamic.setCommentCount(21);
        dynamic.setDynamicContent("吉普赛的情人很好听");


        dynamics.add(new Dynamic());


        return view;



    }



    }









