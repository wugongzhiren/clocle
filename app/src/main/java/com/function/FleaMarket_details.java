package com.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.Base_activity;
import com.clocle.huxiang.clocle.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/10/16.
 */
public class FleaMarket_details extends Base_activity {
    public LinearLayout imgsLiner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flea_market_details_layout);
        imgsLiner= (LinearLayout) findViewById(R.id.flea_imgs_details);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200);
        ImageView imgsview;
        for (int i=0;i<10;i++){
            imgsview=new SimpleDraweeView(this);
            imgsview.setImageResource(R.mipmap.login_bg);
            imgsview.setLayoutParams(lp);
            imgsLiner.addView(imgsview);
        }

    }
}
