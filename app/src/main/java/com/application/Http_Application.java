package com.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;


/*import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.wxlib.util.SysUtil;*/
import com.clocle.huxiang.clocle.Bmob_UserBean;
import com.common_tool.FrescoImageLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.finalteam.galleryfinal.BuildConfig;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Http_Application extends Application {
    private OkHttpClient okHttpClient;
    private static Context context;
    private static Bmob_UserBean bean;


    @Override
    public void onCreate() {
        super.onCreate();
        bean=new Bmob_UserBean();
        bean.setObjectId("fa02b47eb3");
        context = getApplicationContext();
        //fresco初始化
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(this,config);
        //okHttp3配置
        okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        //其他配置
        OkHttpUtils.initClient(okHttpClient);
        //ImageLoad配置
        ImageLoaderConfiguration config1 = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(config1);
        initGalleryFinal();
/*//阿里云旺配置
        final String APP_KEY = "23441577";
//必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
//第一个参数是Application Context
//这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess()) {
            YWAPI.init(this, APP_KEY);
        }*/
//必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;

    }

    /**
     * 配置图片选择器
     */
    private void initGalleryFinal() {

//设置主题
//ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
//配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
        .setMutiSelectMaxSize(9)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

//配置imageloader
        cn.finalteam.galleryfinal.ImageLoader imageloader = new FrescoImageLoader(this);
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    public static Context getContext() {
        return context;
    }
    public static Bmob_UserBean getuser() {
        return bean;
    }

}
