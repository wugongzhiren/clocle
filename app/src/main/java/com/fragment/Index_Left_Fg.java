package com.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.Index_list_bean;
import com.clocle.huxiang.clocle.Bmob_UserBean;
import com.clocle.huxiang.clocle.R;
import com.clocle.huxiang.clocle.Self_manager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.function.Clocle_help_activity;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

/**
 * Created by Administrator on 2016/9/13.
 */
public class Index_Left_Fg extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ArrayList<Index_list_bean> list;
    private ListView listview;
    private Context mcontext;
    private SimpleDraweeView index_photo;//用户头像
    private TextView nickname;//昵称
    private TextView signature;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Ceshi11", Toast.LENGTH_SHORT).show();
        list = new ArrayList<>();
        list.add(new Index_list_bean(R.mipmap.t6, "大学圈"));
        list.add(new Index_list_bean(R.mipmap.t6, "圈圈新鲜事"));
        list.add(new Index_list_bean(R.mipmap.t6, "圈圈帮"));
        list.add(new Index_list_bean(R.mipmap.t6, "圈圈跳蚤市场"));
        list.add(new Index_list_bean(R.mipmap.t6, "圈圈交友"));
        list.add(new Index_list_bean(R.mipmap.t6, "圈圈校园直播"));
        list.add(new Index_list_bean(R.mipmap.t6, "圈圈校园期刊"));
        list.add(new Index_list_bean(R.mipmap.t6, "礼品兑换中心"));
        mcontext = getActivity();
        Bmob_UserBean currentuser = BmobUser.getCurrentUser(Bmob_UserBean.class);
        View view = inflater.inflate(R.layout.index_left_fg, container, false);
        signature = (TextView) view.findViewById(R.id.signature);//签名

        nickname = (TextView) view.findViewById(R.id.leftfg_nickname);
        nickname.setText(currentuser.getUsername());
        if (currentuser.getSignature() == null) {
            signature.setText("美美的人都有签名奥！");
        } else {
            signature.setText(currentuser.getSignature());
        }
        index_photo = (SimpleDraweeView) view.findViewById(R.id.index_photo);
        File photo = new File(Environment
                .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/myphoto", "myphoto.png");
        if (photo.exists()) {
            index_photo.setImageURI("file://" + Environment
                    .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/myphoto/myphoto.png");
            //本地没有从服务器下载,并且保存在本地
            Log.i("服务器头像",currentuser.getphotoUrl());
            BmobFile myphoto = new BmobFile("myphoto.png", "", currentuser.getphotoUrl());
            File filephoto = new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/myphoto", myphoto.getFilename());
            myphoto.download(filephoto, new DownloadFileListener() {
                @Override
                public void onProgress(Integer integer, long l) {

                }

                @Override
                public void done(String s, BmobException e) {
                    Toast.makeText(getActivity(), "头像从服务器下载成功，已经保存在/clocle/myphoto中", Toast.LENGTH_SHORT).show();
                    index_photo.setImageURI("file://" + Environment
                            .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/myphoto/myphoto.png");
                }

            });
        } else {
            //本地没有从服务器下载,并且保存在本地
            BmobFile myphoto = new BmobFile("myphoto.png", "", currentuser.getphotoUrl());
            File filephoto = new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/myphoto", myphoto.getFilename());
            myphoto.download(filephoto, new DownloadFileListener() {
                @Override
                public void onProgress(Integer integer, long l) {

                }

                @Override
                public void done(String s, BmobException e) {
                    Toast.makeText(getActivity(), "头像从服务器下载成功，已经保存在/clocle/myphoto中", Toast.LENGTH_SHORT).show();
                    index_photo.setImageURI("file://" + Environment
                            .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/myphoto/myphoto.png");
                }

            });
           // index_photo.setImageURI("res://com.clocle.huxiang.clocle/" + Uri.parse(R.mipmap.reg + ""));
        }

        initview(view);
        return view;
    }

    private void initview(View view) {

        listview = (ListView) view.findViewById(R.id.index_menu);
        listview.setDividerHeight(4);
        listview.setAdapter(new Index_list_Adapter());
        listview.setOnItemClickListener(this);
        index_photo.setImageURI(BmobUser.getCurrentUser(Bmob_UserBean.class).getphotoUrl());
        index_photo.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                break;
            case 1:
                break;

            case 2:
                Intent intent = new Intent(getActivity(), Clocle_help_activity.class);
                startActivity(intent);
                break;

            case 3:
                break;

            case 4:
                break;


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_photo:
                Intent intent = new Intent(getActivity(), Self_manager.class);
                startActivityForResult(intent,001);
        }
    }

    class Index_list_Adapter extends BaseAdapter {


        public Index_list_Adapter() {

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Index_list_Viewholder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mcontext).inflate(R.layout.index_listview, parent, false);
                holder = new Index_list_Viewholder();
                holder.mimage = (ImageView) convertView.findViewById(R.id.menu_img);
                holder.mtext = (TextView) convertView.findViewById(R.id.menu_txt);
                convertView.setTag(holder);
            }

            holder = (Index_list_Viewholder) convertView.getTag();
            holder.mimage.setImageResource(list.get(position).getIcon());
            holder.mtext.setText(list.get(position).getMenuName());
            return convertView;
        }
    }

    class Index_list_Viewholder {
        private ImageView mimage;
        private TextView mtext;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==001&resultCode==001){
            //从服务器下载,并且保存在本地
            Bmob_UserBean bean=BmobUser.getCurrentUser(Bmob_UserBean.class);
            Log.i("tagnewphoto",bean.getphotoUrl());
            BmobFile myphoto = new BmobFile("myphoto.png", "", bean.getphotoUrl());
            File filephoto = new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/myphoto", myphoto.getFilename());
            myphoto.download(filephoto, new DownloadFileListener() {
                @Override
                public void onProgress(Integer integer, long l) {

                }

                @Override
                public void done(String s, BmobException e) {
                    Toast.makeText(getActivity(), "上传成功，头像从服务器下载成功，已经保存在/clocle/myphoto中", Toast.LENGTH_SHORT).show();
                    index_photo.setImageURI("file://" + Environment
                            .getExternalStorageDirectory().getAbsolutePath().toString() + "/clocle/myphoto/myphoto.png");
                }

            });
        }
    }
}
