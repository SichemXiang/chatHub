package com.chatHub.util;

import lombok.Data;

/**
 * @Author: xsz
 * @Description: 登录向后端发送的账号和密码类
 * @DateTime: 2023/6/21 17:55
 **/
@Data
public class LoginVo {
    private String userName;
    private String password;
}
