package com.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bean.Index_list_bean;
import com.clocle.huxiang.clocle.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/13.
 */
public class Index_Left_Fg extends Fragment {
    private ArrayList<Index_list_bean> list;
    private ListView listview;
    private Context mcontext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list=new ArrayList<>();
        list.add(new Index_list_bean(R.mipmap.t6,"圈圈新鲜事"));
        list.add(new Index_list_bean(R.mipmap.t6,"圈圈帮"));
        list.add(new Index_list_bean(R.mipmap.t6,"圈圈跳蚤市场"));
        list.add(new Index_list_bean(R.mipmap.t6,"圈圈交友"));
        list.add(new Index_list_bean(R.mipmap.t6,"圈圈校园直播"));
        list.add(new Index_list_bean(R.mipmap.t6,"礼品兑换中心"));
        mcontext=getActivity();
        View view=inflater.inflate(R.layout.index_left_fg, container, false);
        initview(view);
        return view;
    }

    private void initview(View view) {
        listview= (ListView) view.findViewById(R.id.index_menu);
        listview.setDividerHeight(4);
        listview.setAdapter(new Index_list_Adapter());
    }

    class Index_list_Adapter extends BaseAdapter{


        public Index_list_Adapter(){

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
            Index_list_Viewholder holder=null;
            if(convertView==null){
                convertView=LayoutInflater.from(mcontext).inflate(R.layout.index_listview,parent,false);
                holder=new Index_list_Viewholder();
                holder.mimage= (ImageView) convertView.findViewById(R.id.menu_img);
                holder.mtext= (TextView) convertView.findViewById(R.id.menu_txt);
                convertView.setTag(holder);
            }

            holder= (Index_list_Viewholder) convertView.getTag();
            holder.mimage.setImageResource(list.get(position).getIcon());
            holder.mtext.setText(list.get(position).getMenuName());
            return convertView;
        }
    }
    class Index_list_Viewholder{
        private ImageView mimage;
        private TextView mtext;
    }
}
