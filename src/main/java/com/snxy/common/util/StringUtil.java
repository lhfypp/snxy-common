package com.snxy.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    public static final String SPACE = " ";

    public static final String EMPTY = "";

    public static final int INDEX_NOT_FOUND = -1;

    // 手机号码正则表达式
    private static final String REGEX_MOBILE = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";

    private static final Pattern PATTERN_MOBILE = Pattern.compile(REGEX_MOBILE);

    // url正则表达式
    private static final String REGEX_URL = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";

    private static final Pattern PATTERN_URL = Pattern.compile(REGEX_URL);

    /**
     * 简体中文的正则表达式
     */
    private static final String REGEX_SIMPLE_CHINESE = "^[\u4E00-\u9FA5]+$";

    /**
     * 字母数字的正则表达式
     */
    private static final String REGEX_ALPHANUMERIC = "[a-zA-Z0-9]+";

    /**
     * 整数或浮点数的正则表达式
     */
    private static final String REGEX_NUMERIC = "(\\+|-){0,1}(\\d+)([.]?)(\\d*)";

    private static final Pattern PATTERN_NUMERIC = Pattern.compile(REGEX_NUMERIC);

    private static final String REGEX_POSITIVE_NUMERIC = "(\\+){0,1}(\\d+)([.]?)(\\d*)";

    private static final Pattern PATTERN_POSITIVE_NUMERIC = Pattern.compile(REGEX_POSITIVE_NUMERIC);

    private static final String REGEX_AMOUNT = "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";

    private static final Pattern PATTERN_AMOUNT = Pattern.compile(REGEX_AMOUNT);

    private static final String REGEX_NUM = "[0-9]+";

    private static final Pattern PATTERN_NUM = Pattern.compile(REGEX_NUM);

    /**
     * 身份证号码的正则表达式
     */
    private static final String REGEX_ID_CARD = "(\\d{14}|\\d{17})(\\d|x|X)";

    /**
     * 电子邮箱的正则表达式
     */
    private static final String REGEX_EMAIL = ".+@.+\\.[a-z]+";

    private static final Pattern PATTERN_REPLACE_WHITE = Pattern.compile("\\s*|\t|\r|\n");

    @Deprecated
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    @Deprecated
    public static boolean isNotEmpty(final CharSequence cs) {
        return !StringUtil.isEmpty(cs);
    }


    /**
     * 检查字符串是否为 空 或 空白字符
     *
     * @param cs 检查的字符串
     * @return boolean
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !StringUtil.isBlank(cs);
    }

    /**
     * 去除字符串两边空白字符
     *
     * @param str 传入的字符串
     * @return String
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    public static String trimAll(final String str) {
        if (isBlank(str)) {
            return null;
        }
        return str.replaceAll("\\s*", "");
    }

    /**
     * 正则判断手机号码格式是否正确
     *
     * @param mobile 手机号码
     * @return boolean
     */
    public static boolean checkMobile(String mobile) {
        if (StringUtil.isBlank(mobile)) {
            return false;
        }
        Matcher matcher = PATTERN_MOBILE.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 正则判断URL地址格式时候正确
     *
     * @param url url地址
     * @return boolean
     */
    public static boolean checkUrl(String url) {
        if (StringUtil.isBlank(url)) {
            return false;
        }
        Matcher matcher = PATTERN_URL.matcher(url);
        return matcher.matches();
    }

    public static boolean checkIsNumic(String number) {
        if (StringUtil.isBlank(number)) {
            return false;
        }
        Matcher matcher = PATTERN_NUMERIC.matcher(number);
        return matcher.matches();
    }

    public static boolean checkIsPositiveNumic(String number) {
        if (StringUtil.isBlank(number)) {
            return false;
        }
        Matcher matcher = PATTERN_POSITIVE_NUMERIC.matcher(number);
        return matcher.matches();
    }

    public static boolean checkIsNum(String number) {
        if (StringUtil.isBlank(number)) {
            return false;
        }
        Matcher matcher = PATTERN_NUM.matcher(number);
        return matcher.matches();
    }


    public static boolean checkIsAmount(String amount) {
        if (StringUtil.isBlank(amount)) {
            return false;
        }

        if (!StringUtil.checkIsNumic(amount)) {
            return false;
        }

        double d = Double.parseDouble(amount);
        if (d == 0) {
            return false;
        }
        Matcher matcher = PATTERN_AMOUNT.matcher(amount);
        return matcher.matches();
    }

    /**
     * 将空格、制表符、换行等替换成 ""
     */
    public static String replaceWhite(String str) {
        if (StringUtil.isBlank(str)) {
            return null;
        }

        Matcher matcher = PATTERN_REPLACE_WHITE.matcher(str);
        return matcher.replaceAll("");
    }

    public static String byte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte aBuf : buf) {
            String hex = Integer.toHexString(aBuf & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexStr2Byte(String hexStr) {
        if (hexStr == null || hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static boolean equals(String source, String target) {
        return CheckUtil.equals(source, target);
    }

}
