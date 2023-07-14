package com.chatHub.exception;

import com.chatHub.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/6/21 19:38
 **/
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public Result exception(RuntimeException e){
        e.printStackTrace();
        log.error("系统运行时异常--->{}",e.getMessage());
        return Result.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result exception(AccessDeniedException e){
        log.error("权限不足--->{}",e.getMessage());
        return Result.error("权限不足，请联系管理员");
    }



}
