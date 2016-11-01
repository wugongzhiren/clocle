package com.im;

import android.util.Log;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;

import static android.content.ContentValues.TAG;

/**接收消息监听器的实现，
 * 所有接收到的消息、通知、
 * 状态都经由此处设置的监听器处理。包括私聊消息、
 * 讨论组消息、群组消息、聊天室消息以及各种状态。
 * Created by Administrator on 2016/10/29.
 */
public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
    @Override
    public boolean onReceived(Message message, int i) {

        MessageContent messageContent = message.getContent();

        if (messageContent instanceof TextMessage) {//文本消息
            TextMessage textMessage = (TextMessage) messageContent;
            Log.d(TAG, "onReceived-TextMessage:" + textMessage.getContent());
        } else {
            Log.d(TAG, "onReceived-其他消息，自己来判断处理");
        }

        return false;
    }
}
