package com.chatHub.controller;

import com.chatHub.entity.Group;
import com.chatHub.service.GroupService;
import com.chatHub.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/7/4 4:43
 **/

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/createGroup")
    public Result createGroup(@RequestBody Group group){
        groupService.createGroup(group);
        return Result.success("chatGroup creates ok!");
    }
}
