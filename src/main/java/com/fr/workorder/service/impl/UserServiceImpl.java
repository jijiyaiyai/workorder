package com.fr.workorder.service.impl;

import com.fr.workorder.entity.User;
import com.fr.workorder.mapper.UserMapper;
import com.fr.workorder.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
