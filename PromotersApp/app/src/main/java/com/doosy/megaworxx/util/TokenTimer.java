package com.doosy.megaworxx.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class TokenTimer {

    //Shared preference settings
    private final String PROFILE_SETTINGS = "TIMER_SETTINGS";

    //Variable settings
    private final String COUNT = "COUNT";
    private final String COUNT_CONTROLLER = "COUNT_CONTROLLER";
    private final String TIME = "TIMER";

    //Shared preference settings
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public TokenTimer(Context context)
    {
        this.context=context;
    }

    public void clearSettings(){
        pref = context.getSharedPreferences(PROFILE_SETTINGS,MODE_PRIVATE);
        editor= pref.edit();
        editor.clear();
        editor.apply();
    }

    public Boolean isTokenAlive(){
        long currentTime = System.currentTimeMillis() + (60 * 1000);

        int secondUntilFinished = (int) (getTime() - System.currentTimeMillis());

        /*
        int sec = (int)(secondUntilFinished / 1000) % 60;
        int min = (int)(secondUntilFinished / 1000) / 60;
        Log.d(Constants.TAG, "Min: " +min+ " : Sec: " + sec);*/

        if(currentTime > getTime())
            return false;

            return true;
    }

    public void setTime(int minutes)
    {
        pref = context.getSharedPreferences(PROFILE_SETTINGS,MODE_PRIVATE);
        editor= pref.edit();
        editor.putLong(TIME, System.currentTimeMillis() + ((60 * 1000) * minutes));
        editor.apply();
    }

    private long getTime()
    {
        pref = context.getSharedPreferences(PROFILE_SETTINGS, MODE_PRIVATE);
        long time = pref.getLong(TIME, System.currentTimeMillis());

        return time;
    }

}
