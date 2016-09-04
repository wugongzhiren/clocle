package test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bean.Bmob_UserBean;
import com.clocle.huxiang.clocle.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by Administrator on 2016/9/3.
 */
public class Test_Bmob extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_bmob);
        //第一：默认初始化
        Bmob.initialize(this, "fbd7c66a38b160c5677a774971be3294");
        Bmob_UserBean user = new Bmob_UserBean();
//注意：不能调用gameScore.setObjectId("")方法
       user.setUsername("无功至人");
        user.setPassword("123456");
        user.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(Test_Bmob.this,"成功",Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }
    }
