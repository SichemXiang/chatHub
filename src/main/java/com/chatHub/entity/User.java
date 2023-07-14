package com.chatHub.entity;

import com.chatHub.util.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

import java.util.Date;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/19 4:37
 **/
@Data
public class User {

    private String userName;

    private String password;

    private String email;

    private boolean isOnline ;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date createdAt;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date updatedAt;

}
