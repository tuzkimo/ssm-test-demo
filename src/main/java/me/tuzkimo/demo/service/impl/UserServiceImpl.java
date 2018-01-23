package me.tuzkimo.demo.service.impl;

import me.tuzkimo.demo.core.AbstractService;
import me.tuzkimo.demo.dao.UserMapper;
import me.tuzkimo.demo.model.User;
import me.tuzkimo.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2018-01-23
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {

  @Resource
  private UserMapper userMapper;
}
