package com.faultaddr.coffeebeggerserver.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinGameForm {

  private String id;

  private String avatar;
  private String city;
  private String gender;
  private String province;
  private String countryCode;
  private String nickName;
}
