package com.chenxinjian.smartstock.server.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by apple on 2016/11/14.
 */
public class DateUtil extends DateUtils {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT_YEAR = "yyyy";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_MONTH = "MONTH";
    public static final String DEFAULT_YEAR = "YEAR";
    public static final String DEFAULT_DATE = "DAY";
    public static final String DEFAULT_HOUR = "HOUR";
    public static final String DEFAULT_MINUTE = "MINUTE";
    public static final String DEFAULT_SECOND = "SECOND";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH-mm";
    public static final String DEFAULT_DATETIME_FORMAT_SEC = "yyyy/MM/dd HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT_SEC2 = "yyyy-MM-dd HH:mm:ss";
    public static final String[] WEEKS = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static String getTomorrow() throws Exception {
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT_SEC2);
        String nowDate = sf.format(date);
        System.out.println(nowDate);
        //通过日历获取下一天日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(sf.parse(nowDate));
        cal.add(Calendar.DAY_OF_YEAR, +1);
        String nextDate_1 = sf.format(cal.getTime());
        return nextDate_1;
    }
}
