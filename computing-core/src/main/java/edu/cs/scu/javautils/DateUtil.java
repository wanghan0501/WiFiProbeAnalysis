package edu.cs.scu.javautils;

import edu.cs.scu.constants.TimeConstants;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * <p>
 * Created by Wang Han on 2017/3/30 20:24.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */

public class DateUtil {

    // 得到log记录器
    private static final Logger logger = Logger.getLogger(DateUtil.class);
    private static final SimpleDateFormat ENGLISH_TIME_FORMAT = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取现在时间（yyyy-MM-dd HH:mm:ss）
     *
     * @return 此刻时间
     */
    public static synchronized String getToday() {
        return TIME_FORMAT.format(new Date());
    }

    /**
     *
     * @param time
     * @param timeConstants
     * @return
     */
    public static synchronized String parseTime(String time,TimeConstants timeConstants) {
        try {
            switch (timeConstants){
                case ENGLISH_TIME_FORMAT:
                    return ENGLISH_TIME_FORMAT.format(ENGLISH_TIME_FORMAT.parse(time));
                case TIME_FORMAT:
                    return TIME_FORMAT.format(ENGLISH_TIME_FORMAT.parse(time));
                case DATE_FORMAT:
                    return DATE_FORMAT.format(TIME_FORMAT.parse(time));
            }

        } catch (ParseException e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return null;
    }

    /**
     *
     * @param time1
     * @param time2
     * @param timeConstants
     * @return
     */
    public static synchronized boolean before(String time1, String time2,TimeConstants timeConstants) {
        try {
            Date dateTime1 = null;
            Date dateTime2 = null;
            switch (timeConstants){
                case ENGLISH_TIME_FORMAT:
                    dateTime1 = ENGLISH_TIME_FORMAT.parse(time1);
                    dateTime2 = ENGLISH_TIME_FORMAT.parse(time2);
                    break;
                case TIME_FORMAT:
                    dateTime1 = TIME_FORMAT.parse(time1);
                    dateTime2 = TIME_FORMAT.parse(time2);
                    break;
            }
            if (dateTime1.before(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return false;
    }


    /**
     * 判断一个时间是否在另一个时间之后
     *
     * @param time1
     * @param time2
     * @param timeConstants
     * @return
     */
    public static synchronized boolean after(String time1, String time2,TimeConstants timeConstants) {
        try {
            Date dateTime1 = null;
            Date dateTime2 = null;
            switch (timeConstants){
                case ENGLISH_TIME_FORMAT:
                    dateTime1 = ENGLISH_TIME_FORMAT.parse(time1);
                    dateTime2 = ENGLISH_TIME_FORMAT.parse(time2);
                    break;
                case TIME_FORMAT:
                    dateTime1 = TIME_FORMAT.parse(time1);
                    dateTime2 = TIME_FORMAT.parse(time2);
                    break;
            }
            if (dateTime1.after(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return false;
    }

    /**
     * 判断第二个时间是否在第一个时间之后
     *
     * @param time1
     * @param time2
     * @param timeConstants 解析的时间类型，枚举变量
     * @param intervalMillisecond 间隔时常，单位毫秒
     * @return
     */
    public static synchronized boolean after(String time1, String time2,TimeConstants timeConstants, long intervalMillisecond) {
        try {
            Date dateTime1 = null;
            Date dateTime2 = null;
            switch (timeConstants){
                case ENGLISH_TIME_FORMAT:
                    dateTime1 = ENGLISH_TIME_FORMAT.parse(time1);
                    dateTime2 = ENGLISH_TIME_FORMAT.parse(time2);
                    break;
                case TIME_FORMAT:
                    dateTime1 = TIME_FORMAT.parse(time1);
                    dateTime2 = TIME_FORMAT.parse(time2);
                    break;
            }
            if (dateTime2.getTime() - dateTime1.getTime() >= intervalMillisecond) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            System.err.println(e.getStackTrace());
        }

        return false;
    }
}

