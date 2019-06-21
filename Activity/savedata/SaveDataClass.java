package com.ItaliasCinemas.ajit.Italiascinema.Activity.savedata;

import android.content.Context;
import android.content.SharedPreferences;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info;

public class SaveDataClass {
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_PASSWORD = "USER_PASSWORD";
    private static final String USER_ID= "USER_ID";
    private static final String EMAIL_ID= "EMAIL_ID";
    public String getSetIndexFrom() {
        return setIndexFrom;
    }

    public void setSetIndexFrom(String setIndexFrom) {
        this.setIndexFrom = setIndexFrom;
    }

    String setIndexFrom;

    public String getSetIndexForDownloading() {
        return setIndexForDownloading;
    }

    public void setSetIndexForDownloading(String setIndexForDownloading) {
        this.setIndexForDownloading = setIndexForDownloading;
    }

    String setIndexForDownloading;

    public String getNotificationcount() {
        return notificationcount;
    }

    public void setNotificationcount(String notificationcount) {
        this.notificationcount = notificationcount;
    }

    String notificationcount;
    private static SaveDataClass single_instance = null;

    public static SaveDataClass getInstance()
    {
        if (single_instance == null)
            single_instance = new SaveDataClass();

        return single_instance;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    Info info;
   /* public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String username;
    String password;*/

    public final static String PREFS_NAME = "Italia'sCinema";

    public static void setUserName(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(USER_NAME, value).apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getString(USER_NAME, "");
    }

    public static void setUserPassword(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(USER_PASSWORD, value).apply();
    }

    public static String getUserPassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getString(USER_PASSWORD, "");
    }


    public static void setUserID(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(USER_ID, value).apply();
    }

    public static String getUserID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getString(USER_ID, "");
    }
    public static void setEmailId(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(EMAIL_ID, value).apply();
    }

    public static String getEmailId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getString(EMAIL_ID, "");
    }
}
