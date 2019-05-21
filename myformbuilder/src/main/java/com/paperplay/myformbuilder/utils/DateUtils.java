package com.paperplay.myformbuilder.utils;


import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ahmed Yusuf on 22/11/18.
 */
public class DateUtils {
    public static String dateformat = "dd-MM-yyyy";

    /**
     * counting of ages
     * @param dates
     * @return [month][year]
     * @throws ParseException
     */
    public static int[] getAge(String dates) throws ParseException{
        int[] result = new int[2];
        //parse date string
        SimpleDateFormat sd = new SimpleDateFormat(dateformat);
        Date currentDate = null;
        int b_year = 0, b_month = 0, b_day = 0;
        currentDate = sd.parse(dates);
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        b_year = cal.get(Calendar.YEAR);
        b_month = cal.get(Calendar.MONTH);
        b_month++; //Beware, months start at 0, not 1.
        b_day = cal.get(Calendar.DAY_OF_MONTH);

        //get birth year and month
        LocalDate dob = new LocalDate(b_year, b_month, b_day);
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthOfYear();
        int day = now.getDayOfMonth();
        LocalDate date = new LocalDate(year, month, day);

        Period period = new Period(dob, date, PeriodType.yearMonthDay());
        result[0] = period.getMonths();
        result[1] = period.getYears();
        return result;
    }

    public static String getTodayDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String changeToReadableDateFormat(String date){
        String result = date;
        try {
            SimpleDateFormat input = new SimpleDateFormat(dateformat);
            Date dateValue = input.parse(date);
            SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
            result = String.valueOf(output.format(dateValue));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getTodayTime(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static void main(String[] args) {
        try {
            System.out.println(getAge("2016-01-08")[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
