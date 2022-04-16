package com.faultaddr.coffeebeggerserver.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OutputMessage extends Message {
  private String gameId;
  private String result;
  private MessageType type;
  private String time;
}
