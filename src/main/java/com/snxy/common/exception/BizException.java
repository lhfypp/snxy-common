package com.snxy.common.exception;


public class BizException extends RuntimeException {

    private static final long serialVersionUID = 7843709071444057731L;

    private String errCode;
    private String errMsg;

    public BizException() {
    }

    public BizException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BizException(String message) {
        super(message);
        this.errMsg = message;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.errMsg = message;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
