package com.chatHub.service.Impl;

import com.chatHub.entity.User;
import com.chatHub.mapper.UserMapper;
import com.chatHub.service.UserService;
import com.chatHub.util.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/19 4:47
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean login(LoginVo loginVo) {
        User user = userMapper.findUserByUserName(loginVo.getUserName());
        if(user == null){
            return false;
        }else{
            return loginVo.getPassword().equals( user.getPassword());
        }
    }

    @Override
    public void insertUser(User user) {
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userMapper.insertUser(user);
    }

    @Override
    public boolean isExits(User user) {
        return userMapper.findUserNumber(user.getUserName()) > 0;
    }




}
