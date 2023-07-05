package com.numble.toss.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String SEND_TIME = "yyyyMMddHHmmssSSS";
    public static String nowDate(){

        Date date = new Date();
        return date.toString();
    }
    public static String retDate(String format){

        String retDate = null;
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);


        return simpleDateFormat.format(nowDate);
    }
}
