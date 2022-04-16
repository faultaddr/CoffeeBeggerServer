package com.faultaddr.coffeebeggerserver.service;

import com.faultaddr.coffeebeggerserver.listener.RedisChannel;
import com.faultaddr.coffeebeggerserver.sender.MsgDTO;
import com.faultaddr.coffeebeggerserver.websocket.WebSocketSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

@Service
public class SenderService {

  @Autowired private JdbcTemplate jdbcTemplate;

  @Autowired private RedisTemplate redisTemplate;

  public void send(String receiverId, String msg) {
    // 消息落地
    jdbcTemplate.update(
        "INSERT INTO sys_msg(`sender_id`, `receiver_id`, `msg`, `create_time`, `is_read`) VALUES (?,?,?,?,?)",
        -1,
        receiverId,
        msg,
        System.currentTimeMillis() / 1000,
        0);

    Session session = WebSocketSupport.getSession(String.valueOf(receiverId));

    if (session != null) {
      // 本地存在session 则直接推送
      WebSocketSupport.push(session, msg);

    } else {
      // 将消息推送到指定的Redis管道
      redisTemplate.convertAndSend(RedisChannel.WS_SEND_MSG, new MsgDTO(receiverId, msg));
    }
  }
}
