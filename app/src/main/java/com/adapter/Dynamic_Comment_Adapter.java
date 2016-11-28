package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bean.Dynamic_Comment;
import com.clocle.huxiang.clocle.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */

public class Dynamic_Comment_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<Dynamic_Comment> list;

    public Dynamic_Comment_Adapter(Context context, List<Dynamic_Comment> listcomment) {
        this.list = listcomment;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.dynamic_comment_layout, parent, false);
        return new Dynamic_Comment_VH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Dynamic_Comment_VH) {
            ((Dynamic_Comment_VH) holder).dynamic_comment_nickname.setText(list.get(position).getCommentuser().getUsername());
            ((Dynamic_Comment_VH) holder).dynamic_comment_content.setText(list.get(position).getComment());
            ((Dynamic_Comment_VH) holder).dynamic_comment_item_photo.setImageURI(list.get(position).getCommentuser().getphotoUrl());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Dynamic_Comment_VH extends RecyclerView.ViewHolder {
        private SimpleDraweeView dynamic_comment_item_photo;
        private TextView dynamic_comment_nickname;
        private TextView dynamic_comment_re;//回复
        private TextView dynamic_comment_content;

        public Dynamic_Comment_VH(View itemView) {
            super(itemView);
            dynamic_comment_item_photo = (SimpleDraweeView) itemView.findViewById(R.id.dynamic_comment_item_photo);
            dynamic_comment_nickname = (TextView) itemView.findViewById(R.id.dynamic_comment_nickname);
            dynamic_comment_re = (TextView) itemView.findViewById(R.id.dynamic_comment_re);
            dynamic_comment_content = (TextView) itemView.findViewById(R.id.dynamic_comment_content);
        }
    }
}
