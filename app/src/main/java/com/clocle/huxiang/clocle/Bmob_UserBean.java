package com.clocle.huxiang.clocle;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


public class Bmob_UserBean extends BmobUser {
   private BmobFile userphoto;
    private String photoUrl;

    public String getphotoUrl() {
        return photoUrl;
    }

    public void setphotoUrl(String photoUrl) {
        this.photoUrl =photoUrl;
    }

    public BmobFile getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(BmobFile userphoto) {
        this.userphoto = userphoto;
    }
}
