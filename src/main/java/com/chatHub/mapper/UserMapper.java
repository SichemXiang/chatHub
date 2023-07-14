package com.chatHub.mapper;

import com.chatHub.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/19 4:41
 **/
@Mapper

public interface UserMapper {

    //根据用户名查找密码
    User findUserByUserName(String userName);

    void insertUser(User user);

    //check a user if exits
    Integer findUserNumber(String userName);

    Integer findUserById(int userId);






}
