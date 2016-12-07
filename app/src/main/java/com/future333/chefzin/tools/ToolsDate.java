package com.future333.chefzin.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by manuel on 4/10/16.
 */
public class ToolsDate {

    public static String dateFormat(String dateInit){
        try {
            SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateObj =  curFormater.parse(dateInit);
            SimpleDateFormat postFormater = new SimpleDateFormat("hh:mm a");
            String newDate = postFormater.format(dateObj);
            return newDate;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Calendar stringToCalentar(String date){
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            calendar.setTime(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    //----------------------------------------------------------------------------------------------
    /*** Verifico si la hora del dispositivo esta dentro de un rango */
    public static boolean checkHourRange(String hourOn, String hourOff){
        if(checkHourHigher(stringToCalentar(hourOn)) && checkHourLess(stringToCalentar(hourOff)))
            return true;
        return false;
    }

    /*** Verifico si la hora ingresada es mayor a la del dispositivo */
    public static boolean checkHourHigher(Calendar hourOn){
        Calendar hourCurrent = Calendar.getInstance();

        if(hourOn.get(Calendar.HOUR_OF_DAY) < hourCurrent.get(Calendar.HOUR_OF_DAY))
            return true;

        if(hourOn.get(Calendar.HOUR_OF_DAY) == hourCurrent.get(Calendar.HOUR_OF_DAY))
            if(hourOn.get(Calendar.MINUTE) <= hourCurrent.get(Calendar.MINUTE))
                return true;

        return false;
    }

    /*** Verifico si la hora ingresada es menor a la del dispositivo */
    public static boolean checkHourLess(Calendar hourOff){
        Calendar hourCurrent = Calendar.getInstance();

        if(hourOff.get(Calendar.HOUR_OF_DAY) > hourCurrent.get(Calendar.HOUR_OF_DAY))
            return true;

        if(hourOff.get(Calendar.HOUR_OF_DAY) == hourCurrent.get(Calendar.HOUR_OF_DAY))
            if(hourOff.get(Calendar.MINUTE) > hourCurrent.get(Calendar.MINUTE))
                return true;

        return false;
    }
}
