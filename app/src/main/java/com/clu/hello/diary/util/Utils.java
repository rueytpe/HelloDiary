package com.clu.hello.diary.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static final String DISPLAY_DATE_FORMAT = "yyyy MMMM dd EEEE";
    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";

    public static String getTodayStrforDatabase() {
        SimpleDateFormat sdf = new SimpleDateFormat(DB_DATE_FORMAT);
        String strToday = sdf.format(new Date());
        return strToday;
    }

    public static String getTodayStrforView() {
        SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
        String strToday = sdf.format(new Date());
        return strToday;
    }

    public static String changeDateFormat(String oldDateValue, String oldFormat, String newFormat ) {
        SimpleDateFormat sdfOld = new SimpleDateFormat(DB_DATE_FORMAT);
        SimpleDateFormat sdfNew = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
        String newDateValue = oldDateValue;
        try {
            Date date = sdfOld.parse(oldDateValue);
            newDateValue = sdfNew.format(date);
        } catch (Exception e) {

        }
        return newDateValue;

    }
}
