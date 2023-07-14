package com.boomcat.boomcat.result;

import lombok.Data;

@Data
public class Result<T> {
    String message;
    Integer code;
    T data;
    private static Integer SUSS=1000;
    private static Integer ERROR=4000;

    Result(String msg,Integer code){
        setCode(code);
        setMessage(msg);
    }
    Result(){
        this("请求成功",SUSS);
    }
    //for quick success
    public static <T> Result<T>success(){
        return new Result<>();
    }
    //success with data
    public static <T>Result<T>success(T data){
        Result<T> result = new Result<T>();
        result.setData(data);
        return result;
    }
    public static <T> Result<T>error(){
        return new Result<>("请求失败",ERROR);
    }
}
