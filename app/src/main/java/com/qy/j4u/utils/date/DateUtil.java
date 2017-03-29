package com.qy.j4u.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * Created by abc on 2016/11/18.
 */

public class DateUtil {

    /**
     * 根据指定的格式来返回当前日期
     * @param pattern   格式
     * @return  当前日期字符串形式
     */
    public static String getCurrentDate(String pattern) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


}
