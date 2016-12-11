package com.clocle.huxiang.clocle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapter.Grid_view_MyFg_adapter;
import com.view.MyGridView;

/**
 * 我的
 * Created by Administrator on 2016/7/7.
 */
public class My_fg extends Fragment {
    private MyGridView myGridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fankui,container,false);
        myGridView= (MyGridView) view.findViewById(R.id.gridview);
        myGridView.setAdapter(new Grid_view_MyFg_adapter(getActivity()));
        return view;
    }
}
