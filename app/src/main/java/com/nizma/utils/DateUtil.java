package com.nizma.utils;

/**
 * Created by WZW on 2016/9/21.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * 日期格式化 <br/>
 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'
 * @author 何剑波
 * 2016年5月11日`
 */
public class DateUtil {
    private static Logger LOGGER = Logger.getLogger("");
    /**
     * 默认 时间格式 'yy/MM/dd HH:mm:ss'
     */
    public static final String DEFAULT_DATE_FORMAT = "yy/MM/dd HH:mm:ss";

    private static GregorianCalendar GC = null;

    private static void initGC() {
        if (GC == null) {
            GC = new GregorianCalendar();
        }
    }

    /**
     * 默认日期格式化
     * 'yy/MM/dd HH:mm:ss'
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        return dateFormat(DEFAULT_DATE_FORMAT, date);
    }

    /**
     * 按要求格式化日期
     * @param format 日期格式
     * @param date	日期
     * @return
     */
    public static String dateFormat(String formatStr, Date date) {
        return new SimpleDateFormat(formatStr).format(date);
    }

    public static Date toDate(String dateStr, String formatStr) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.parse(dateStr);
    }

    /**
     * 根据时间格式，比较时间大小
     * @param dateStr1	日期1
     * @param dateStr2	日期2
     * @param formatStr 日期格式
     * @return 返回值 状态：-1 dateStr1 小于 dateStr2， 0 等于，1 dateStr1 大于 dateStr2
     */
    public static int dateContrast(String dateStr1, String dateStr2, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date1;
        Date date2;
        try {
            date1 = format.parse(dateStr1);
            date2 = format.parse(dateStr2);
            return date1.compareTo(date2);
        } catch (ParseException e) {
           // LOGGER.error("日期比较失败失败：" + e);
        }
        return 0;
    }

    /**
     * *java中对日期的加减操作
     *gc.add(1,-1)表示年份减一.
     *gc.add(2,-1)表示月份减一.
     *gc.add(3.-1)表示周减一.
     *gc.add(5,-1)表示天减一.
     *以此类推应该可以精确的毫秒吧.没有再试.大家可以试试.
     *GregorianCalendar类的add(int field,int amount)方法表示年月日加减.
     *field参数表示年,月.日等.
     *amount参数表示要加减的数量
     * @param formatStr	时间格式
     * @param date
     * @param amount	参数表示要加减的数量
     * @param field	参数表示年,月.日等.
     * @return
     */
    public static String computeDate(String formatStr, Date date, int amount, int field) {
        initGC();
        GC.setTime(date);
        GC.add(field, amount);
        GC.set(GC.get(Calendar.YEAR), GC.get(Calendar.MONTH), GC.get(Calendar.DATE));
        return new SimpleDateFormat(formatStr).format(GC.getTime());
    }

    /**
     * 计算两个日期之间区间值值
     * @param date1
     * @param date2
     * @param interval 如(1000 * 60 * 60 * 24)
     * @return	返回两日期之间的区间值
     */
    public static long calculateDays(Date date1, Date date2, long interval) {
        return (date1.getTime() - date2.getTime()) / interval;
    }
    /**
     * 返回这个数值是不是空
     */
    public static boolean isNull(String str){
        if(str==null || str=="" ||str.length()==0){
            return true;
        }
        return false;
    }
}
