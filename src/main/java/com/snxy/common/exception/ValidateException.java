package com.snxy.common.exception;


public class ValidateException extends BizException {

    private static final long serialVersionUID = 6098040855392451491L;

    private String errCode = "1";
    private String errMsg;

    public ValidateException() {
    }

    public ValidateException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ValidateException(String message) {
        super(message);
        this.errMsg = message;
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
        this.errMsg = message;
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }
}
