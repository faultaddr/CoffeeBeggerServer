package com.faultaddr.coffeebeggerserver.listener.impl;

import com.faultaddr.coffeebeggerserver.listener.RedisChannel;
import com.faultaddr.coffeebeggerserver.listener.RedisMsgReceiver;
import com.faultaddr.coffeebeggerserver.sender.MsgDTO;
import com.faultaddr.coffeebeggerserver.websocket.WebSocketSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMsgReceiver implements RedisMsgReceiver {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getChannelName() {
        return RedisChannel.WS_SEND_MSG;
    }

    @Override
    public void receiveMsg(String message) {
        MsgDTO msg = (MsgDTO) redisTemplate.getValueSerializer().deserialize(message.getBytes());
        WebSocketSupport.tryPush(msg);
    }
}
