package com.training.date;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * george 2018/9/14 14:39
 */
public class TestCalendar {
    public static void main(String[] args) {
        getBeginOfYesterday();
    }
    public static List<String> getDayBetween(Date beginDate, Date endDate) {
        List<String> result = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(beginDate);
        max.setTime(endDate);
        Calendar curr = min;

        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(5, 1);
        }

        return result;
    }

    public static Date getEndOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getEndOfToday());
        cal.add(5, -1);
        return cal.getTime();
    }

    public static Date getEndOfToday() {
        Calendar cal = new GregorianCalendar();
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 999);
        return cal.getTime();
    }

    public static Date getBeginOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getBeginOfToday());
        cal.add(5, -1);
        return cal.getTime();
    }

    private static Date getBeginOfToday() {
        Calendar cal = new GregorianCalendar();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }
}
