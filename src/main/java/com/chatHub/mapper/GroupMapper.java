package com.chatHub.mapper;

import com.chatHub.entity.Group;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/7/4 4:34
 **/

@Mapper
public interface GroupMapper {

    void createGroup(Group group);

}
