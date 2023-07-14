package com.chatHub.controller;

import com.chatHub.entity.User;
import com.chatHub.service.UserService;
import com.chatHub.util.LoginVo;
import com.chatHub.util.Result;
import com.chatHub.util.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/21 17:43
 **/

@RestController
@RequestMapping("/user")
@Api(value = "用户使用接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        if(userService.login(loginVo)){
            String token = tokenService.generateToken(loginVo.getUserName());
            return Result.success(token);
        }else{
            return Result.error("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public Result insertUser(@RequestBody User user){
        if(!userService.isExits(user)){
            userService.insertUser(user);
            return Result.success("注册成功！");
        }else {
            return Result.error("The account already exists");
        }

    }


}
