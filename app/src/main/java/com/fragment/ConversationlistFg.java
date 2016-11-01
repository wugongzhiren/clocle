package com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.Conversation_list_Adapter;
import com.clocle.huxiang.clocle.R;

import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**会话列表fragment
 * Created by Administrator on 2016/10/29.
 */
public class ConversationlistFg extends Fragment {
    private RecyclerView coversationlistRv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("tag","ConversationlistFg-onCreateView:");
        View view=inflater.inflate(R.layout.coversationlist,container,false);
        //会话列表recycleview
        coversationlistRv= (RecyclerView) view.findViewById(R.id.coversationlist_rv);
        coversationlistRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                Log.d("tag",conversations.get(0).getReceivedTime()+"");

                coversationlistRv.setAdapter(new Conversation_list_Adapter(getActivity(),conversations));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        return view;
    }

}
