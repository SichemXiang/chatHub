package com.chatHub.config;

import com.chatHub.util.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xsz
 * @Description: Secure your endpoints by creating an AuthInterceptor to validate the token
 * @DateTime: 2023/6/28 21:14
 **/

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if(token != null && token.startsWith("Bearer ")){
            token = token.substring(7); // Remove "Bearer " prefix

            boolean isValidToken = tokenService.validateToken(token);
            boolean isTokenExpired = tokenService.isTokenExpired(token);

            if(isValidToken){
                if(!isTokenExpired){
                    return true;
                }
            }
        }
        //if token is invalid or not provided, return an error response;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Sorry, invalid token");
        return false;
    }
}
