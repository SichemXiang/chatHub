package com.chatHub.service.Impl;

import com.chatHub.entity.Group;
import com.chatHub.mapper.GroupMapper;
import com.chatHub.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/7/4 4:40
 **/
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public void createGroup(Group group) {
        groupMapper.createGroup(group);
    }
}
