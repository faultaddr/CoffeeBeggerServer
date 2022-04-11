package com.faultaddr.coffeebeggerserver.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputMessage extends Message {
    private String gameId;
    private String userId;
    private MessageType type;
}
