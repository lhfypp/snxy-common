package com.snxy.common.response;

import lombok.Data;

import java.io.Serializable;


@Data
public class ResultData<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    // 默认的成功码 0   ，自定义的成功码 100*  1001
    public static final int SUCCESS = 0;
    // 默认的错误码为1 ，自定义的错误码 400*   如4001
    public static final int FAIL = 1;
    public static final int NO_PERMISSION = 4003;

    private boolean result = true;
    private String msg = "success";
    private int code = SUCCESS;
    private T data;

    public ResultData() {

    }

    public ResultData(T data) {
        this.data = data;
    }

    public ResultData(String successMsg, T data) {
        this.msg = successMsg;
        this.data = data;
    }

    public ResultData(Integer successCode,String successMsg, T data) {
        this.code = successCode;
        this.msg = successMsg;
        this.data = data;
    }

    public ResultData(String errMsg) {
        this.result = false;
        this.msg = errMsg;
        this.code = FAIL;
    }

    public ResultData(String errMsg, Integer errCode) {
        this.result = false;
        this.msg = errMsg;
        this.code = errCode;
    }


    public static <T> ResultData<T> success(T data) {

        return new ResultData<>(data);
    }

    public static <T> ResultData<T> success(String successMsg, T data) {
        return new ResultData<>(successMsg, data);
    }
    public static <T> ResultData<T> success(Integer successCode,String successMsg, T data) {return new ResultData<>(successCode,successMsg, data);}

    public static <T> ResultData<T> fail(String errMsg) {
        return new ResultData<>(errMsg);
    }

    public static <T> ResultData<T> fail(String errMsg, Integer errCode) {
        return new ResultData<>(errMsg, errCode);
    }
}
