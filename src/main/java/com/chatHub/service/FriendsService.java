package com.chatHub.service;

import com.chatHub.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/28 7:29
 **/

public interface FriendsService {

    List<User> searchFriends(String searchKeyword);

    boolean sendFriendRequest(String senderName, String receiverName) throws JsonProcessingException;

    void acceptFriendRequest(String senderName, String receiverName) throws JsonProcessingException;

    void declineFriendRequest(String senderName, String receiverName) throws JsonProcessingException;
}
