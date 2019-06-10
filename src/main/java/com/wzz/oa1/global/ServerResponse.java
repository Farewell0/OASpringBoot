package com.wzz.oa1.global;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * 用泛型构造的服务端响应对象
 * @param <T>
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)  //保证序列化json的时候,如果是null的对象,key也会消失
public class ServerResponse<T> implements Serializable {

    @JsonSerialize(using = MySerializerUtils.class)
    private int status;

    private String msg;

    private T data;

    private ServerResponse(){}
    private ServerResponse(int status) {
        this.status = status;
    }
    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }
    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore //使之不在json序列化结果当中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }
    public String getMsg(){
        return msg;
    }
    public T getData(){
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }
    public static <T> ServerResponse<T> createBySuccess(String msg, T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage){
        return new ServerResponse<T>(errorCode, errorMessage);
    }

//        public static void main(String[] args) {
//        ServerResponse sr1 = new ServerResponse(1, new Object());
//        ServerResponse sr2 = new ServerResponse(2, "abc");
//        System.out.println("console");
//    }
}