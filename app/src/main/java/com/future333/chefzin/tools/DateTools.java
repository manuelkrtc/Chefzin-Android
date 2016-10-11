package com.future333.chefzin.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by manuel on 4/10/16.
 */
public class DateTools {

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

}
