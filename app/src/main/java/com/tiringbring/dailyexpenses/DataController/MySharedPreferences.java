package com.tiringbring.dailyexpenses.DataController;

import android.content.Context;

public class MySharedPreferences {
    Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public Long getDayilyLimit() {
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        return pref.getLong("limit_daily_expense", 1000);
    }
    public Long getMonthlyLimit(){
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        return pref.getLong("limit_monthly_expense", 25000);
    }

    public Long getYearlyLimit(){
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        return pref.getLong("limit_yearly_expense", 200000);
    }
    public boolean getIsFirstRun(){
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        return pref.getBoolean("is_first_run", true);
    }
    public boolean getIsNotificationEnabled(){
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        return pref.getBoolean("is_notification_enabled", true);
    }
    public void setIsNotificationEnabled(boolean value) {
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("is_notification_enabled", value);
        editor.commit();
    }
    public void setIsFirstRun(boolean value) {
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("is_first_run", value);
        editor.commit();
    }

    public void setDayilyLimit(Long value) {
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putLong("limit_daily_expense", value);
        editor.commit();
    }
    public void setMonthlyLimit(Long value) {
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putLong("limit_monthly_expense", value);
        editor.commit();
    }
    public void setYearlyLimit(Long value) {
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putLong("limit_yearly_expense", value);
        editor.commit();
    }
    public String getLanguage(){
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        return pref.getString(PreferenceTypes.LANGUAGE, "en");
    }
    public  void setLanguage(String language){
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putString(PreferenceTypes.LANGUAGE, language);
        editor.commit();
    }
}
