package com.chatHub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/28 6:04
 **/

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/chatHub")// 注册WebSocket处理程序
                .addInterceptors(new ChatHandshakeInterceptor())
                .setAllowedOrigins("*");
    }

}
