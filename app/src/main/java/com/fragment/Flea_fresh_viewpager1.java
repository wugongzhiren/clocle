package com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bean.Flea_market;
import com.clocle.huxiang.clocle.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
public class Flea_fresh_viewpager1 extends Fragment {
    public static Flea_fresh_viewpager1 newInstance(Context context, Bundle bundle) {
        Flea_fresh_viewpager1 newFragment = new Flea_fresh_viewpager1();
        newFragment.setArguments(bundle);
        return newFragment;
    }

    private RecyclerView mRecyclerView;
private List<Flea_market> flea_marketList;//从服务器获取的二手信息

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.flea_viewpager1, container,false);
        flea_marketList=new ArrayList<>();
        //从服务器获取数据
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));

    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private Context mContext;

        public RecyclerViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flea_rv, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
            holder.img_flea_rv.setAdapter();

        }

        @Override
        public int getItemCount() {
            return flea_marketList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public SimpleDraweeView userPhoto;//头像
            public TextView flea_nickname;//昵称
            public TextView strdate;//日期
            public TextView flea_money;
            public RecyclerView img_flea_rv;

            public ViewHolder(View itemview) {
                super(itemview);
                flea_nickname = (TextView) itemview.findViewById(R.id.flea_nickname);
                flea_money= (TextView) itemview.findViewById(R.id.flea_money);
                img_flea_rv= (RecyclerView) itemview.findViewById(R.id.flea_rv_imgs);
            }
        }
    }
}
