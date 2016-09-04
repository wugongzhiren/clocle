
package com.clocle.huxiang.clocle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.constant.Constant;

import com.toolview.Edit_Activity;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import tool.Pop_activity;
import tool.Self_info_adapter;


/**
 * Created by Administrator on 2016/7/18.
 */

public class Self_Info extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private List<com.bean.Self_Info> self_infos;
    public ListView selflistView;
    private Button saveinfo;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder;
    private Self_info_adapter adapter;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_info);

        saveinfo= (Button) findViewById(R.id.save_info);
        saveinfo.setOnClickListener(this);
        selflistView= (ListView) findViewById(R.id.list_info);
        selflistView.setOnItemClickListener(this);
        self_infos=new ArrayList<com.bean.Self_Info>();
        self_infos.add(new com.bean.Self_Info("昵称","许朝颜"));
        self_infos.add(new com.bean.Self_Info("性别","男"));
        self_infos.add(new com.bean.Self_Info("生日","1993年10月12日"));
        self_infos.add(new com.bean.Self_Info("星座","天秤座"));
        self_infos.add(new com.bean.Self_Info("学校","安徽中医药大学"));
        self_infos.add(new com.bean.Self_Info("学院","信息工程学院"));
        self_infos.add(new com.bean.Self_Info("专业","信息管理与信息系统"));
        self_infos.add(new com.bean.Self_Info("电话号码","18168875570"));
        adapter=new Self_info_adapter(this,self_infos);
        selflistView.setAdapter(adapter);

       // postSelf_info();
    }
    /*
public void postSelf_info(){
        String url="http://http://192.168.1.111:8080/clocle/servlet/Reg";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(Self_Info.this,s,Toast.LENGTH_LONG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Self_Info.this,"失败",Toast.LENGTH_LONG);
            }
        });
        request.setTag("get");
        VolleyApplication.getQueue().add(request);
    }*/


    @Override
    public void onClick(View v) {
        //UpdateList(0);
        Toast.makeText(this,"提交资料",Toast.LENGTH_SHORT).show();
        //网络请求
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder().url("http://192.168.1.111:8080/clocle/servlet/Reg").build();
        Call call = okHttpClient.newCall(request);}
//请求加入调度
      /*  call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
            }

            @Override
            public void onResponse(final Response response) throws IOException
            {
                //String htmlStr =  response.body().string();
            }
        });*/



    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        Toast.makeText(Self_Info.this, "你点击了"+(position+1)+"项", Toast.LENGTH_SHORT).show();
        //Intent intentedit=new Intent(this, Edit_Activity.class);
        //startActivity(intentedit);
        View itemview=selflistView.getChildAt(position);
        switch (position){
            case 0:
                intent=new Intent(this, Pop_activity.class);
                intent.putExtra("requestcode","100");
                startActivityForResult(intent,100);
                break;
            case 1:
                intent=new Intent(this, Pop_activity.class);
                intent.putExtra("requestcode","101");
                startActivityForResult(intent,101);
                break;
            case 2:
                intent=new Intent(this, Pop_activity.class);
                intent.putExtra("requestcode","102");
                startActivityForResult(intent,102);
                break;
            case 3:
                intent=new Intent(this, Pop_activity.class);
                intent.putExtra("requestcode","103");
                startActivityForResult(intent,103);
                break;
            case 4:
                intent=new Intent(this, Pop_activity.class);
                intent.putExtra("requestcode","104");
                startActivityForResult(intent,104);
                break;
            case 5:
                intent=new Intent(this, Pop_activity.class);
                intent.putExtra("requestcode","105");
                startActivityForResult(intent,105);
                break;
            case 6:
                intent=new Intent(this, Pop_activity.class);
                intent.putExtra("requestcode","106");
                startActivityForResult(intent,106);
                break;
            case 7:
                intent=new Intent(this, Pop_activity.class);
                intent.putExtra("requestcode","107");
                startActivityForResult(intent,107);
                break;


        }



    }

    public void UpdateList(int position,String args){
        self_infos.remove(position);
        switch (position){
            case 0:
                self_infos.add(position,new com.bean.Self_Info("昵称",args) );
                break;
            case 1:
                self_infos.add(position,new com.bean.Self_Info("性别",args) );
                break;
            case 2:
                self_infos.add(position,new com.bean.Self_Info("生日",args) );
                break;
            default:
                break;

        }

        adapter.notifyDataSetChanged();
    }
//从调用的activity得到数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==100){
            //data.getStringExtra("nickname");
            UpdateList(0,data.getStringExtra("nickname"));

        }
        else if (requestCode==101&&resultCode==101){
            //data.getStringExtra("sex");
            UpdateList(1,data.getStringExtra("sex"));
        }
        else if (requestCode==102&&resultCode==102){
            //data.getStringExtra("sex");
            UpdateList(2,data.getStringExtra("date"));
        }
    }
}

