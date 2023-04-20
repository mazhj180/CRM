package com.mazhj.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 对日期格式化 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 对日期格式化 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 对日期格式化 HH:mm:ss
     * @param date
     * @return
     */
    public static String formTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
