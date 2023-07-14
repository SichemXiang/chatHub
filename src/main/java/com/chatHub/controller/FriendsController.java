package com.chatHub.controller;

import com.chatHub.service.FriendsService;
import com.chatHub.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/28 7:27
 **/

@RestController
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    private FriendsService friendsService;

    @GetMapping("/searchFriends")
    public Result searchFriends(@RequestParam("searchKeyword") String searchKeyword){
        return Result.success(friendsService.searchFriends(searchKeyword));
    }


}
