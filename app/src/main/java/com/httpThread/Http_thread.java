package com.httpThread;

import java.util.logging.Handler;

/**处理http请求
 * Created by Administrator on 2016/7/11.
 */
public class Http_thread extends Thread{
    private String url;//地址
    private Handler handler;
    @Override
    public void run() {
        super.run();
    }
}
