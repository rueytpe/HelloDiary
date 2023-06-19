package com.clu.hello.diary.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static final String DISPLAY_DATE_FORMAT = "yyyy MMMM dd EEEE";
    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String FILTER_YEAR_FORMAT = "yyyy";
    public static final String FILTER_MONTH_FORMAT = "yyyy-MM";

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

    public static String[] getDiaryFilterItems() {
        SimpleDateFormat sdfYear = new SimpleDateFormat(FILTER_YEAR_FORMAT);
        SimpleDateFormat sdfMonth = new SimpleDateFormat(FILTER_MONTH_FORMAT);
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        String strYear = sdfYear.format(now);
        String strMonth = sdfMonth.format(now);

        cal.add(Calendar.MONTH, -1);
        String strLastMonth = sdfMonth.format(cal.getTime());

        cal.add(Calendar.MONTH, -1);
        String strTwoMonthAgo = sdfMonth.format(cal.getTime());
        String[] result = {strYear, strMonth, strLastMonth, strTwoMonthAgo};

        return result;
    }
}
