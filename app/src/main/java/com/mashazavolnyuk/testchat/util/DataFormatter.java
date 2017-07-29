package com.mashazavolnyuk.testchat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mashka on 27.07.17.
 */

public class DataFormatter {


    public static String formatString(String oldDta){

        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("HH:mm");
            Date d = sdf.parse(oldDta);
            return output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "?";

    }


    public static Date formatDate(String oldDta){

        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("HH:mm");
            Date d = sdf.parse(oldDta);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }




}
