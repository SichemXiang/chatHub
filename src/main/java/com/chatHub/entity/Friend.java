package com.chatHub.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/29 5:29
 **/

@Data
public class Friend {
    private int id;

    private String userName1;

    private String userName2;

    private int status;

    private LocalDateTime createdAt;

}
