package com.doosy.megaworxx.util;


import android.util.Log;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Util {

    public static String convertToFormat(String val){
        String convertedDate = "";
        DateFormat stringToDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

        DateFormat dateToString = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        try{
            Date date = stringToDateFormat.parse(val);
            convertedDate = dateToString.format(date);
        }catch (Exception e){

        }

        return convertedDate;
    }

    public static String formatDate(String val){
        String convertedDate = "";
        DateFormat stringToDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        //stringToDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        DateFormat dateToString = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try{
            Date date = stringToDateFormat.parse(val);
            convertedDate = dateToString.format(date);
        }catch (Exception e){
            Log.d(Constants.TAG, e.getStackTrace().toString());
        }

        return convertedDate;
    }

    public static String formatTime(String val){
        String convertedDate = "";
        DateFormat stringToDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        //stringToDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        DateFormat dateToString = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        try{
            Date date = stringToDateFormat.parse(val);
            convertedDate = dateToString.format(date);
        }catch (Exception e){
            Log.d(Constants.TAG, e.getStackTrace().toString());
        }

        return convertedDate;
    }

    public static String toJson(Object obj){
        Gson gson = new Gson();
        String json = gson.toJson(obj);

        return json;
    }

    public static final String QuestionTypeSingle = "1292acd8-4523-49fc-a372-e1883cf962ce";
    public static final String QuestionTypeTrueOrFalse= "4e9770a9-8bdc-41ab-84ee-19cca09ac311";
    public static final String QuestionTypeMultipleOption = "ff07c1a4-fdfa-4a45-9bd1-ef7aa1badf36";
}
