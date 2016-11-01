package com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.clocle.huxiang.clocle.R;

import java.util.List;

import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;

/**
 * Created by Administrator on 2016/11/1.
 */

public class Conversation_list_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;

    private List<Conversation> datas;
    private Context mcontext;

    public Conversation_list_Adapter(Context context, List<Conversation> conversations) {
        this.mcontext = context;
        this.datas = conversations;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.conversationlistitem, parent, false);
        return new conversation_list_VH(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof conversation_list_VH) {

            ((conversation_list_VH) holder).latestMs.setText(((TextMessage) datas.get(position).getLatestMessage()).getContent());
        ((conversation_list_VH) holder).chat_photo.setImageResource(R.mipmap.t7);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class conversation_list_VH extends RecyclerView.ViewHolder {
        private ImageView chat_photo;
        private TextView latestMs;
        private TextView time;

        public conversation_list_VH(View itemView) {
            super(itemView);
            chat_photo = (ImageView) itemView.findViewById(R.id.chatuser_photo);
            latestMs = (TextView) itemView.findViewById(R.id.messageContent);
            time = (TextView) itemView.findViewById(R.id.chat_time);
        }
    }
}
