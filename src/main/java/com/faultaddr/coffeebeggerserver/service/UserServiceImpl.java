package com.faultaddr.coffeebeggerserver.service;

import com.faultaddr.coffeebeggerserver.entity.MUserEntity;
import com.faultaddr.coffeebeggerserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;
  /**
   * @param avatar
   * @return
   */
  @Override
  public MUserEntity getUserByAvatar(String avatar) {
    return userRepository.findMUserEntityByAvatar(avatar);
  }

  /**
   * @param id
   * @return
   */
  @Override
  public MUserEntity getUserById(int id) {
    return null;
  }
}
