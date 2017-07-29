package com.mashazavolnyuk.testchat.util;

import android.text.format.DateUtils;

import java.util.Date;

/**
 * Created by mashka on 28.07.17.
 */

public class TimeChecker {

    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }

    public static boolean isToday(Date d) {
        return DateUtils.isToday(d.getTime());
    }

    public static boolean isAtThisDay(Date old, Date newDate){
        if(old.getYear()==newDate.getYear() && old.getMonth()==newDate.getMonth()&& old.getDay()==newDate.getDay())
            return true;
        return false;

    }

}
