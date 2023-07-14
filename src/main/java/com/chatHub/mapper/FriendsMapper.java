package com.chatHub.mapper;

import com.chatHub.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/28 7:24
 **/

@Mapper
public interface FriendsMapper {

    //模糊搜索好友
    List<User> searchFriends(String searchKeyword);

    //send friend request
    void createFriendship(@Param("userName1") String userName1, @Param("userName2") String userName2);

    //accept friend request
    void acceptFriendship(@Param("userName1") String userName1, @Param("userName2") String userName2);

    //decline friend request
    void declineFriendship(@Param("userName1") String userName1, @Param("userName2") String userName2);




}
