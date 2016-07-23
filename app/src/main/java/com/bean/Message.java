package com.bean;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/9.
 * 这个类是首页动态的bean类
 */
public class Message {
    private String name;//昵称
    private String message;//消息内容
    private int pic;//头像

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getQiangdan() {
        return qiangdan;
    }

    public void setQiangdan(int qiangdan) {
        this.qiangdan = qiangdan;
    }

    private  String datetime;//时间
    private int qiangdan;//抢单图片

    public Message(String name, String message, int pic,String datetime,int qiangdan) {
        this.name = name;
        this.message = message;
        this.pic = pic;
        this.datetime=datetime;
        this.qiangdan=qiangdan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }


}
