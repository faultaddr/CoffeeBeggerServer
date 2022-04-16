package com.faultaddr.coffeebeggerserver.sender;

import com.faultaddr.coffeebeggerserver.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderController {

  @Autowired private SenderService senderService;

  @GetMapping("send")
  public Object send(String receiverId, String msg) {
    senderService.send(receiverId, msg);
    return "OK";
  }
}
