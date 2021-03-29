package com.zhb.nettychat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        return time;
    }

    public String getCurrentWeek() {
        Date date = new Date();
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        TimeUtils timeUtils = new TimeUtils();
        String str = timeUtils.getCurrentWeek();
    }
}
