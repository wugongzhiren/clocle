package com.bean;

/**
 * Created by Administrator on 2016/7/9.
 * 这个类是首页动态的bean类
 */
public class Message {
    public Message(String name, String message, int pic) {
        this.name = name;
        this.message = message;
        this.pic = pic;
    }

    private String name;//昵称
    private String message;//消息内容
    private int pic;//头像

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
