package test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bean.Clocle_help;
import com.clocle.huxiang.clocle.Bmob_UserBean;
import com.clocle.huxiang.clocle.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
Toast.makeText(this,"ceshi",Toast.LENGTH_SHORT).show();
        Bmob.initialize(this, "fbd7c66a38b160c5677a774971be3294");
        BmobQuery<Clocle_help> query=new BmobQuery<Clocle_help>("Clocle_help");
       // query.addWhereGreaterThan("peopleNum", 0);
        query.setLimit(2);
        query.findObjects(new FindListener<Clocle_help>() {
            @Override
            public void done(List<Clocle_help> list, BmobException e) {
                //
                Log.i("返回",list.size()+"");
                Clocle_help clocle_help=list.get(0);
                Log.i("返回",clocle_help.getContent());
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
