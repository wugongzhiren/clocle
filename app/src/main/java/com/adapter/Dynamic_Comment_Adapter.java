package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bean.Dynamic_Comment;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */

public class Dynamic_Comment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater inflater;
    private List<Dynamic_Comment> list;
    public Dynamic_Comment_Adapter(Context context,List<Dynamic_Comment> listcomment){
        this.list=listcomment;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
