package com.clocle.huxiang.clocle;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

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
 * Created by Administrator on 2016/7/6.
 */
public class Index_fg extends android.support.v4.app.Fragment {
    public   List<Message> messages= new ArrayList<Message>();;//动态的数据
    public ListView messageListview;
    public  Myadpter myadapter;
    private static Context listcontext;
    private ProgressBar indexprogress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fg, container, false);

        indexprogress= (ProgressBar) view.findViewById(R.id.index_progress);
        messageListview = (ListView) view.findViewById(R.id.list_item_index);

        listcontext = getActivity();

        //getJsonlistview();

        /*messages.add(new Message("艾苏北郡的小铁匠", "万能baseadapter实验，成功了", R.mipmap.t1, "7月17日 17:25"));
        messages.add(new Message("落泪星辰", "最新的电影，谁看", R.mipmap.t2, "7月17日 17:25"));
        messages.add(new Message("七厘米蔚蓝", "谁有爱奇艺会员，记得分享哦！！", R.mipmap.t7, "7月17日 17:15"));
        messages.add(new Message("无功至人", "打麻将三缺一，速度的来", R.mipmap.t3, "7月17日 17:25"));
        messages.add(new Message("小盆友", "安徽三熊孩子偷葫芦想当葫芦娃", R.mipmap.t9, "7月17日 17:15"));
        messages.add(new Message("一壶冰啤酒", "一起去喝酒", R.mipmap.t4, "7月17日 17:05"));
        messages.add(new Message("空心菜", "秀一下爪子", R.mipmap.t10, "7月17日 16:25"));
        messages.add(new Message("寻浅", "我要写一段很长的话，很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的长到第4行", R.mipmap.t5, "7月17日 12:25"));
        messages.add(new Message("团团酱", "街拍那么美", R.mipmap.t6, "7月17日 12:15"));
        messages.add(new Message("你说我傻", "你才傻！！！", R.mipmap.t8, "7月17日 12:05"));
       // messageListview.setAdapter(new Index_adapter(messages, listcontext));
        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        myadapter=new Myadpter(listcontext,messages);
        messageListview.setAdapter(myadapter);
        //txt_content.setText(content);*/
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);


    }
    public void getJsonlistview(){
        new Index_AsyncTask().execute(Constant.GET_HELP_JSON);


    }


    /**
     * 首页异步加载的类
     */
    class Index_AsyncTask extends AsyncTask<String, Void, List<Messages>> {
        private Gson mgson = new Gson();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            indexprogress.setVisibility(View.VISIBLE);

        }

        @Override
        protected List<Messages> doInBackground(String... params) {
            return getIndex_Json_data(params[0]);
        }

        private List<Messages> getIndex_Json_data(String url) {
            List<Messages> messagesList = new ArrayList<Messages>();
            String jsondatas = "";
            try {
                jsondatas = readInputStream(new URL(url).openStream());

                messagesList = mgson.fromJson(jsondatas, new TypeToken<List<Messages>>() {
                }.getType());
                Log.i("tag", jsondatas);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return messagesList;
        }

        private String readInputStream(InputStream inputstream) {
            String result = "";

            StringBuffer line = new StringBuffer();
            try {
                String jsondata = "";
                InputStreamReader reader = new InputStreamReader(inputstream, "UTF-8");
                BufferedReader bfreader = new BufferedReader(reader);
                while ((jsondata = bfreader.readLine()) != null) {
                    line.append(jsondata);

                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return line.toString();
        }

        @Override
        //更新UI
        protected void onPostExecute(List<Messages> messages) {
            super.onPostExecute(messages);
            myadapter=new Myadpter(listcontext,messages);
            messageListview.setAdapter(myadapter);
            indexprogress.setVisibility(View.GONE);
        }
    }

    }




