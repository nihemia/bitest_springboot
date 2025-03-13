package com.springboot.exception;


import com.springboot.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


//从com.springboot.controller开始捕获异常
//注意com.中的.是英文状态下的，之前弄成了中文下的。所以报错
@ControllerAdvice(basePackages = "com.springboot.controller")
//全局异常处理器
public class GlobalExceptionHandler {
    private static final Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //统一异常处理@ExceptionHandler,主要用于Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, Exception e){
        log.error("异常信息",e);  //打印日志
        //异常中抛出异常Result.error
        return Result.error("系统异常");  //返回前端
    }

    @ExceptionHandler(CustomException.class)
    //返回
    @ResponseBody
    //捕获自定义异常customError
    public Result customError(HttpServletRequest request, CustomException e)
    {
        return Result.error(e.getMsg());
    }





}
