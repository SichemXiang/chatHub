package com.chatHub.util;

import lombok.Data;

/**
 * @Author: xsz
 * @Description: The same type returned by the backend to the frontend
 * @DateTime: 2023/6/19 22:15
 **/
@Data
public class Result {
    private boolean status;
    private String message;
    private Object data;

    public Result() {
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setStatus(true);
        result.setMessage("Success");
        result.setData(data);
        return result;
    }

    public static Result error(String message){
        Result result = new Result();
        result.setStatus(false);
        result.setMessage(message);
        return result;
    }
}
