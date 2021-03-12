package com.doosy.megaworxx.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.Method;

public class Setting {

    public enum ConnectionType {
        None,
        WiFi,
        Mobile,
        Both,
    }

    private Context mContext;

    private final String SETTINGS = "PROMOTER_SETTINGS";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private final String IS_LOGGEDIN = "IS_LOGGEDIN";
    private final String TOKEN = "TOKEN";
    private final String TOKEN_TIME = "TOKEN_TIME";

    //Player settings
    private final String CONNECTION_SETTINGS = "CONNECTION_SETTINGS";
    private final String USER_CONNECTION = "IS_BOTH";
    private String IS_WIFI = "IS_WIFI";
    private String IS_MOBILE = "IS_MOBILE";
    private String USER_ID = "USER_ID";
    private String TODAY_DATE = "TODAY_DATE";
    private String COORDINATES = "COORDINATES";
    private final ConnectivityManager connectivityManager;
    private  NetworkInfo netInfo;

    private ConnectionType type;
    public Setting(Context mContext)
    {
        this.mContext = mContext;
        type = ConnectionType.None;
        connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

    }


    public void clearPlayer(){
        pref = mContext.getSharedPreferences(CONNECTION_SETTINGS, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public void setNetWorkType(ConnectionType connectionType){
        pref = mContext.getSharedPreferences(CONNECTION_SETTINGS, Context.MODE_PRIVATE);
        editor = pref.edit();
        Log.d(Constants.TAG, "Ordinal Settings Index: "+connectionType.ordinal());
        editor.putInt(USER_CONNECTION, connectionType.ordinal());
        editor.apply();
    }


    public void logOut(){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.clear();
        editor.apply();
    }


    public Boolean isLoggedIn(){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_LOGGEDIN,false);
    }

    public void setIsLoggedIn(Boolean value){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean(IS_LOGGEDIN,value);
        editor.apply();
    }

    public void saveCoordinates(String coordinates){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(COORDINATES, coordinates);
        editor.apply();
    }

    public String getsCoordinates(){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return pref.getString(COORDINATES,"");
    }

    public void setTokenAlive(int time){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putInt(TOKEN_TIME, time);
        editor.apply();
    }

    public void setUserId(String userId){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(USER_ID, userId);
        editor.apply();
    }

    public void setTodayDate(String date){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(TODAY_DATE, date);
        editor.apply();
    }


    public String getTodayDate(){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return pref.getString(TODAY_DATE,"");
    }

    public String getToken(){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return "Bearer "+ pref.getString(TOKEN,"none");
    }

    public String getUserId(){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return pref.getString(USER_ID,"none");
    }

    public void setToken(String value){
        pref = mContext.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString(TOKEN,value);
        editor.apply();
    }

    public ConnectionType getConnectionType(){
        netInfo = connectivityManager.getActiveNetworkInfo();
        if(netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI &&
                netInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            type = ConnectionType.Both;
        else if(netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI)
            type = ConnectionType.WiFi;
        else if(netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            type = ConnectionType.Mobile;

        return type;
    }

    public boolean isOnline() {
        netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public boolean isWiFiOn(){
        WifiManager wifi = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            return  true;
        }
        return false;
    }

    public  boolean isDataOn(){
        boolean mobileDataEnabled = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(connectivityManager.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean)method.invoke(connectivityManager);
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
        return mobileDataEnabled;
    }
}
