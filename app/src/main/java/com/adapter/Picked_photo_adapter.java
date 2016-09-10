package com.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.clocle.huxiang.clocle.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/7.
 */
public class Picked_photo_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> urlList;
    private Context mcontext;
    private int marginleft;
    private int photoW;

    public Picked_photo_adapter(Context context, ArrayList<String> urlList) {
        this.mcontext = context;
        this.urlList = urlList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = inflater.inflate(R.layout.picked_photo_item, parent, false);

        return new ViewHolder(itemview);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).mimageButton.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).pickedphotoView.setImageURI(urlList.get(position));
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView pickedphotoView;
        public ImageButton mimageButton;

        public ViewHolder(final View itemView) {
            super(itemView);
            pickedphotoView = (SimpleDraweeView) itemView.findViewById(R.id.picked_photo_item);
            //设置pickedphotoView的宽高一样，适应屏幕大小
            photoW=pickedphotoView.getLayoutParams().width=(mcontext.getResources().getDisplayMetrics().widthPixels-15)/4;
            pickedphotoView.getLayoutParams().height=photoW;
            Log.i("width" ,mcontext.getResources().getDisplayMetrics().widthPixels+"");
            //marginleft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mcontext.getResources().getDisplayMetrics());
           // Log.i("width1" ,marginleft+"");
            mimageButton = (ImageButton) itemView.findViewById(R.id.photo_clear);
            mimageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    urlList.remove(1);
                    Picked_photo_adapter.this.notifyDataSetChanged();
                }
            });
        }
    }


}
