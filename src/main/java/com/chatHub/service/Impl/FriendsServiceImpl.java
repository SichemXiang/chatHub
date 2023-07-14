package com.chatHub.service.Impl;

import com.chatHub.config.ChatWebSocketHandler;
import com.chatHub.entity.User;
import com.chatHub.mapper.FriendsMapper;
import com.chatHub.mapper.UserMapper;
import com.chatHub.service.FriendsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/28 7:29
 **/

@Service
public class FriendsServiceImpl implements FriendsService {

    private final FriendsMapper friendsMapper;

    private final UserMapper userMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    List<WebSocketSession> sessions = ChatWebSocketHandler.getSessions();

    public FriendsServiceImpl(FriendsMapper friendsMapper, UserMapper userMapper) {
        this.friendsMapper = friendsMapper;
        this.userMapper = userMapper;
    }


    @Override
    public List<User> searchFriends(String searchKeyword) {
        return friendsMapper.searchFriends(searchKeyword);
    }

    @Override
    public boolean sendFriendRequest(String senderName, String receiverName) throws JsonProcessingException {
        // 检查senderName和receiverName是否有效
        System.out.println("---------------");
        if(userMapper.findUserNumber(senderName) <= 0 || userMapper.findUserNumber(receiverName) <= 0){
            return false;
        }
        friendsMapper.createFriendship(senderName,receiverName);

        // 构建 payload 对象
        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("action", "friend_request");
        payloadMap.put("senderName", senderName);
        payloadMap.put("receiverName", receiverName);
        payloadMap.put("request_type", "add_friend");

        // 将 payload 对象转换为字符串
        String payload = objectMapper.writeValueAsString(payloadMap);

        try {
            boolean senderFound = false;
            // 将好友请求消息发送给接收方
            for (WebSocketSession session : sessions) {
                String userName = (String) session.getAttributes().get("userName");
                if (userName.equals(receiverName)) {
                    session.sendMessage(new TextMessage(payload));
                    return true;
                }
            }
            if (!senderFound) {
                // Handle case where sender is not found
                System.err.println("Sender not found: " + senderName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public void acceptFriendRequest(String senderName, String receiverName) {
        //update filed 'status' values 1
        friendsMapper.acceptFriendship(receiverName,senderName);


        try {
            boolean senderFound = false;
            // 将好友添加成功消息发送给发送方
            for (WebSocketSession session : sessions) {
                String userName = (String) session.getAttributes().get("userName");
                if (userName.equals(receiverName)) {
                    senderFound = true;
                    session.sendMessage(new TextMessage(senderName+" approved your friend request"));
                    break;
                }
            }
            if (!senderFound) {
                // Handle case where sender is not found
                System.err.println("Sender not found: " + senderName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declineFriendRequest(String senderName, String receiverName)  {
        //update filed 'status' values 2
        friendsMapper.declineFriendship(receiverName,senderName);

        try {
            boolean senderFound = false;
            // 将好友添加成功消息发送给发送方
            for (WebSocketSession session : sessions) {
                String userName = (String) session.getAttributes().get("userName");
                if (userName.equals(receiverName)) {
                    senderFound = true;
                    session.sendMessage(new TextMessage(senderName+" declined your friend request"));
                    break;
                }
            }
            if (!senderFound) {
                // Handle case where sender is not found
                System.err.println("Sender not found: " + receiverName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
