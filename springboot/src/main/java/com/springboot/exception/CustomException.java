package com.springboot.exception;

public class CustomException extends RuntimeException{
    private String msg;

    public CustomException(String msg) {
        super(msg);
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
