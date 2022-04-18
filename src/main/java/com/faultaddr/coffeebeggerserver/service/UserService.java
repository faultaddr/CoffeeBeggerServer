package com.faultaddr.coffeebeggerserver.service;

import com.faultaddr.coffeebeggerserver.entity.MUserEntity;

public interface UserService {
  MUserEntity getUserByAvatar(String avatar);

  MUserEntity getUserById(int id);
}
