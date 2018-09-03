package com.snxy.common.response;

import com.alibaba.fastjson.JSON;
import com.snxy.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by 24398 on 2018/9/2.
 */
@Slf4j
public class DataHelper {

    public static <T> T parseResponseData(ResultData<T> resultDatat) {
        if (resultDatat == null) {
            throw new BizException("服务调用失败，无返回值");
        }
        if (!resultDatat.isResult()) {
            throw new BizException(String.valueOf(resultDatat.getCode()), resultDatat.getMsg());
        }
        T t = resultDatat.getData();
        if (t == null) {
            throw new BizException("无响应数据");
        }
        return t;
    }

    /**
     * 解析ResultBean返回data数据
     */
    public static <T> T parseResponseData(ResultData resultData, Class<T> clazz) {
        if (resultData == null) {
            throw new BizException("服务调用失败，无返回值");
        }
        if (!resultData.isResult()) {
            throw new BizException(String.valueOf(resultData.getCode()), resultData.getMsg());
        }
        Object data = resultData.getData();
        if (data == null) {
            throw new BizException("无响应数据");
        }
        T t = null;
        try {
            t = JSON.parseObject(JSON.toJSONString(data), clazz);
        } catch (Exception e) {
            log.error("data:[{}],不能解析为传入的类型:[{}]", JSON.toJSONString(data), clazz.toString());
            throw new BizException("解析返回数据异常");
        }
        return t;
    }

    /**
     * 解析ResultData调用是否成功
     *
     * @param resultData     调用返回结果
     * @param throwException 是否抛出异常
     * @return Boolean
     */
    public static Boolean parseResponse(ResultData resultData, Boolean throwException) {
        if (throwException) {
            return parseResponseAndThrowException(resultData);
        }
        return resultData != null && resultData.isResult();
    }

    private static Boolean parseResponseAndThrowException(ResultData resultData) {
        if (resultData == null) {
            throw new BizException("服务调用失败，无返回值");
        }
        if (!resultData.isResult()) {
            throw new BizException(String.valueOf(resultData.getCode()), resultData.getMsg());
        }
        return Boolean.TRUE;
    }

    public static <T> T parseResponseWithoutThrowException(ResultData<T> resultData) {
        if (resultData == null) {
            return null;
        }
        return resultData.getData();
    }
}
