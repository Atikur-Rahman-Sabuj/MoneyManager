package com.tiringbring.dailyexpenses.DataController;

import android.content.Context;

public class MySharedPreferences {
    Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public Long getDayilyLimit() {
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        return pref.getLong("limit_daily_expense", 2000);
    }

    public void setDayilyLimit(Long value) {
        android.content.SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putLong("limit_daily_expense", value);
        editor.commit();
    }
}
