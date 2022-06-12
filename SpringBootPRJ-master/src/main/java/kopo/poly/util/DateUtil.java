package kopo.poly.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 날짜, 시간 출력하기
     * @param fm 날짜 출력 형식
     * @return date
     */
    public static String getDateTime(String fm) {

        Date today = new Date();
        System.out.println(today);

        SimpleDateFormat date = new SimpleDateFormat(fm);

        return date.format(today);
    }

    /**
     * 날짜, 시간 출력하기
     * @return 기본값은 년.월.일
     */
    public static String getDateTime() {
        return getDateTime("yyyy.MM.dd");

    }

    public static String getDateTimeHMS() {
        return getDateTime("yyyy.MM.dd.HH.mm.ss");

    }

    public static String addDate(int day) throws Exception {

        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

        Calendar cal = Calendar.getInstance();

        Date today = new Date();

        cal.setTime(today);

        cal.add(Calendar.DATE, day);
        cal.add(Calendar.MONTH , -1);


        return date.format(cal.getTime());

    }
    public static String addDateYMD(int day) throws Exception {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();

        Date today = new Date();

        cal.setTime(today);

        cal.add(Calendar.DATE, day);

        return date.format(cal.getTime());


    }

    public static String addDateYMD(String theDay,int addNum) throws Exception {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();

        int year = Integer.parseInt(theDay.split("-")[0]);
        int month = Integer.parseInt(theDay.split("-")[1]);
        int day = Integer.parseInt(theDay.split("-")[2]);

        cal.set(year, month-1, day);

        cal.add(Calendar.DATE, addNum);

        return date.format(cal.getTime());

    }

}
