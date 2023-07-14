package com.chatHub.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/29 23:46
 **/

@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        //Obtain the username parameter passed by the front end from the request
        // 假设用户名以查询参数的形式传递，如 ws://localhost:8080/chatHub?userName=user123
        String query = request.getURI().getQuery();
        String[] parts = query.split("=");
        if (parts.length == 2 && parts[0].equals("userName")) {
            String userName = parts[1];
            //add the username to the properties of the websocket session
            attributes.put("userName",userName);
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {

    }
}
