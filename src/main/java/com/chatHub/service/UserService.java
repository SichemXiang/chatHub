package com.chatHub.service;

import com.chatHub.entity.User;
import com.chatHub.util.LoginVo;


/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/19 4:46
 **/

public interface UserService {


    boolean login(LoginVo loginVo);

    void insertUser(User user);

    boolean isExits(User user);


}
