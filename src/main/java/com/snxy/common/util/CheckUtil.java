package com.snxy.common.util;

import com.snxy.common.exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CheckUtil {

    private static final Logger logger = LoggerFactory.getLogger(CheckUtil.class);

    private CheckUtil() {

    }

    public static void isTrue(boolean condition) {
        isTrue(condition, null, null);
    }

    /**
     * 根据条件判断，为 false 则抛出异常信息
     *
     * @param condition boolean 类型条件
     * @param errMsg    异常信息
     */
    public static void isTrue(boolean condition, String errMsg) {
        isTrue(condition, errMsg, null);
    }

    /**
     * 根据条件判断，为 false 则抛出异常信息
     *
     * @param condition boolean 类型条件
     * @param errMsg    异常信息
     * @param param     错误参数的值
     */
    public static void isTrue(boolean condition, String errMsg, Object param) {
        if (!condition) {
            if (param != null) {
                logger.info("param error:[{}] actual:[{}]", errMsg, param);
            }
            fail(errMsg);
        }
    }

    public static void fail(String errMsg) {
        if (StringUtil.isBlank(errMsg)) {
            errMsg = "Illegal param";
        }
        throw new ValidateException(errMsg);
    }

    /**
     * 判断两个对象是否相等，包括为null的情况
     *
     * @param expected 期望值
     * @param actual   实际值
     * @return Boolean
     */
    public static boolean equals(Object expected, Object actual) {
        return equalsWithNull(expected, actual);
    }

    private static boolean equalsWithNull(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        }
        return expected.equals(actual);
    }

    public static boolean isExpired(long expireTime) {
        return expireTime - System.currentTimeMillis() <= 0;
    }


}
