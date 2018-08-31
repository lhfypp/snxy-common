package com.snxy.common.util;

import com.snxy.common.constant.CommonConstant;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class DateUtil {

    /**
     * 时间格式化
     */

    private static final String[] dateArr;

    static {
        List<String> dates = new LinkedList<>();
        dates.add("yyyy-MM");
        dates.add("yyyy-MM-dd");
        dates.add("yyyy-MM-dd HH:mm");
        dates.add("yyyy-MM-dd HH:mm:ss");

        dates.add("yyyy/MM");
        dates.add("yyyy/MM/dd");
        dates.add("yyyy/MM/dd HH:mm");
        dates.add("yyyy/MM/dd HH:mm:ss");

        dateArr = dates.toArray(new String[0]);
    }

    /**
     * 获取当前日期 默认格式: yyyy-MM-dd
     *
     * @return Date
     */
    public static Date getDate() {

        return getDate(CommonConstant.DATE_FORMAT_DEFAULT);
    }

    /**
     * 根据传入格式，获取当前日期
     *
     * @param dateFormat 时间格式化格式
     * @return Date
     */
    public static Date getDate(String dateFormat) {
        LocalDate now = LocalDate.now();
        try {
            return DateUtils.parseDate(now.toString(), dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前日期时间 默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @return Date
     */
    public static Date getDateTime() {
        return getDateTime(CommonConstant.DATE_FORMAT_DATE_TIME);
    }

    /**
     * 根据传入格式，获取当前日期时间
     *
     * @param dateFormat 时间格式化格式
     * @return Date
     */
    public static Date getDateTime(String dateFormat) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat));
        try {
            return DateUtils.parseDate(now, dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期 转化为 字符串 默认格式：yyyy-MM-dd
     *
     * @param date 日期时间
     * @return 格式化后的字符串
     */
    public static String date2Str(Date date) {
        if (date == null) {
            return "";
        }
        return date2Str(date, CommonConstant.DATE_FORMAT_DEFAULT);
    }

    /**
     * 日期 转化为 字符串 默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期时间
     * @return 格式化后的字符串
     */
    public static String dateTime2Str(Date date) {
        return date2Str(date, CommonConstant.DATE_FORMAT_DATE_TIME);
    }

    /**
     * 根据传入格式，将日期时间 转化为 字符串
     *
     * @param date       日期时间
     * @param dateFormat 时间格式化格式
     * @return 格式化后的字符串
     */
    public static String date2Str(Date date, String dateFormat) {
        return DateFormatUtils.format(date, dateFormat);
    }

    /**
     * 将字符串，转为日期时间
     *
     * @param dateStr    字符串
     * @param dateFormat 格式化格式
     * @return 解析后的日期时间
     */
    public static Date str2Date(String dateStr, String dateFormat) {
        if (StringUtil.isBlank(dateStr)) {
            return null;
        }
        Date date = null;
        try {
            if (StringUtil.isNotBlank(dateFormat)) {
                date = DateUtils.parseDate(dateStr, dateFormat);
            } else {
                date = DateUtils.parseDate(dateStr, dateArr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取当前时间几天之后的 long 时间
     *
     * @param days 添加的天数
     * @return long 型时间
     */
    public static long getMillionByTime(int days) {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(days);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 根据 million 获取对应的 LocalDateTime
     *
     * @param million long 型时间
     * @return 对应的 LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByMillon(long million) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(million), ZoneId.systemDefault());
    }


    /**
     * 获取当前时间的字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return String
     */
    public static String getNow() {
        return getNow(CommonConstant.DATE_FORMAT_DATE_TIME);
    }

    public static String getNow(String dateFormat) {
        return DateFormatUtils.format(new Date(), dateFormat);
    }

    public static   Date getDate(Date baseDate,long period, TemporalUnit unit){
        String baseDateStr = DateFormatUtils.format(baseDate,"yyyy-MM-dd HH:mm:ss");
        LocalDateTime baseLocalDateTime = LocalDateTime.parse(baseDateStr,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime transLocalDateTime = baseLocalDateTime.plus(period, unit);
        String minusLocalDateTimeStr = transLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Date transDate = null;
        try {
             transDate = DateUtils.parseDate(minusLocalDateTimeStr,"yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return transDate;
    }


}
