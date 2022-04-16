package com.faultaddr.coffeebeggerserver.sender;

import java.io.Serializable;

public class MsgDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String receiverId;

  private String msgBody;

  public MsgDTO() {}

  public MsgDTO(String receiverId, String msgBody) {
    this.receiverId = receiverId;
    this.msgBody = msgBody;
  }

  public String getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(String receiverId) {
    this.receiverId = receiverId;
  }

  public String getMsgBody() {
    return msgBody;
  }

  public void setMsgBody(String msgBody) {
    this.msgBody = msgBody;
  }
}
