/*
package com.adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;

import com.application.Http_Application;
import com.clocle.huxiang.clocle.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import test.Test_imageloader;

*/
/**
 * Created by Administrator on 2016/9/7.
 *//*

public class Picked_photo_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> urlList;
    private Context mcontext;
    private int marginleft;
    private int photoW;
    private static int type0=0;//图片
    private static int type1=1;//添加图片

    public Picked_photo_adapter(Context context, ArrayList<String> urlList) {
        this.mcontext = context;
        this.urlList = urlList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
if(viewType==type0){
        View itemview = inflater.inflate(R.layout.picked_photo_item, parent, false);

        return new ViewHolder(itemview);}
        else {
    View itemview=inflater.inflate(R.layout.addphoto,parent,false);
    return new ViewHolderWithAddPhoto(itemview);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if(holder instanceof ViewHolder){


        ((ViewHolder) holder).mimageButton.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).pickedphotoView.setImageURI(urlList.get(position)); }
        else if(holder instanceof ViewHolderWithAddPhoto){
            ((ViewHolderWithAddPhoto) holder).mimageview.setImageResource(R.mipmap.addphoto);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==(urlList.size()-1)){
            return type1;
        }
        else {
            return type0;
        }
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }
class ViewHolderWithAddPhoto extends RecyclerView.ViewHolder{
private ImageView mimageview;

    public ViewHolderWithAddPhoto(View itemView) {
        super(itemView);
        mimageview= (ImageView) itemView.findViewById(R.id.addphoto);
       int width= mimageview.getLayoutParams().width=(mcontext.getResources().getDisplayMetrics().widthPixels-15)/4;
    mimageview.getLayoutParams().height=width;
        mimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Http_Application.getContext(), Test_imageloader.class);
                mcontext.s
            }
        });

    }
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
                    urlList.remove(itemView.getTag());
                    Picked_photo_adapter.this.notifyDataSetChanged();
                }
            });
        }
    }


}
*/
