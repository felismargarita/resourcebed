package com.felis.resource;

public class Result {
    private String msg;
    private Integer code;
    private Object info;

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Result(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public Result(Integer code,String msg,Object info){
        this.code = code;
        this.msg = msg;
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
