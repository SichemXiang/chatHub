package com.chatHub.config;

import com.chatHub.service.FriendsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/28 6:18
 **/

@Configuration
@ComponentScan("com.chatHub.config") // 指定要扫描的包路径
@EnableWebSocket
public class ChatWebSocketHandler implements WebSocketHandler {

    private static final List<WebSocketSession> sessions = new ArrayList<>();
    private static FriendsService friendsService;
    //spring管理单例的bean但是websocket是对对象，因此不能直接注入
    @Autowired
    public void setFriendsService(FriendsService friendsService){
        ChatWebSocketHandler.friendsService = friendsService;
    }

    public static List<WebSocketSession> getSessions(){
        return sessions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session); // 添加新连接到用户列表
    }

    // 创建 ObjectMapper 对象，用于解析 JSON
    ObjectMapper objectMapper = new ObjectMapper();

    //编写处理接收到的WebSocket消息的逻辑
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //处理接收到的消息
        String payload = message.getPayload().toString();
        String senderName = extractSenderName(payload);
        String receiverName = extractReceiverName(payload);

        //Determine whether the received message is a friend request
        if(isFriendRequest(payload)){

            if(isAcceptFriendRequest(payload)){
                friendsService.acceptFriendRequest(senderName, receiverName);
                    // Friend request accepted, notify the sender
                session.sendMessage(new TextMessage("You have accepted the friend request from " + receiverName));

            }else if(isDeclineFriendRequest(payload)){
                friendsService.declineFriendRequest(senderName, receiverName);
                // Friend request declined, notify the sender
                session.sendMessage(new TextMessage("You have declined the friend request from " + senderName));

            } else{
                boolean requestSent = friendsService.sendFriendRequest(senderName, receiverName);

                if (requestSent) {
                    // 好友请求成功发送，通知发送方
                    session.sendMessage(new TextMessage("Friend request has been sent to  " + receiverName));
                } else {
                    // 无法发送好友请求，通知发送方
                    session.sendMessage(new TextMessage("Failed to send friend request"));
                }
            }

        }else{
            try {
                boolean isSenderFound = false;
                /* message sent to receiver */
                for (WebSocketSession sessionOfSessions : sessions) {
                    String userName = (String) sessionOfSessions.getAttributes().get("userName");
                    if (userName.equals(receiverName)) {
                        isSenderFound = true;
                        sessionOfSessions.sendMessage(new TextMessage(extractMessageContent(payload)));
                        break;
                    }
                }
                if (!isSenderFound) {
                    // Handle case where sender is not found
                    System.err.println("Sender not found: " + senderName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //处理由于传输错误而导致的WebSocket连接异常
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)  {
        sessions.remove(session); // 从用户列表中移除连接
    }

    //用于指示是否支持部分消息传输，你可以根据需要返回相应的值
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    //check whether the request is friend quest
    public boolean isFriendRequest(String payload) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(payload);
        return jsonNode.get("action").asText().equals("friend_request");

    }

    //Whether to accept friend requests
    public boolean isAcceptFriendRequest(String payload) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(payload);
        return jsonNode.get("request_type").asText().equals("accept_friend");
    }

    //whether to decline friend requests
    public boolean isDeclineFriendRequest(String payload) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(payload);
        return jsonNode.get("request_type").asText().equals("decline_friend");
    }

    //extract the sender  name
    public String extractSenderName(String payload) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(payload);
        return jsonNode.get("senderName").asText();

    }

    //extract the receiver name
    public String extractReceiverName(String payload) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(payload);
        return jsonNode.get("receiverName").asText();
    }

    //Extract message content
    public String extractMessageContent(String payload) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(payload);
        return jsonNode.get("content").asText();
    }

}
