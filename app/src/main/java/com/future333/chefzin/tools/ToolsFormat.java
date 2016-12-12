package com.future333.chefzin.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;

/**
 * Created by manuel on 5/10/16.
 */
public class ToolsFormat {

    public static String string_to_md5(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++)
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String int_to_price(int number){
        return "$" + NumberFormat.getIntegerInstance().format(number);
    }

    public static int[] arrayString_to_arrayInt(String[] arrayString){
        int[] arrayInt = new int[arrayString.length];
        for(int i = 0;i < arrayString.length;i++)
            arrayInt[i] = Integer.parseInt(arrayString[i]);

        return arrayInt;
    }
}
