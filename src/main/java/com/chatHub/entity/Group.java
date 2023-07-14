package com.chatHub.entity;

import com.chatHub.util.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/7/4 4:32
 **/

@Data
public class Group {

    private int groupId;

    private String groupName;

    private String creator;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date createdAt;
}
