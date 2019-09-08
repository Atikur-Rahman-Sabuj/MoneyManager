package com.tiringbring.dailyexpenses.Utility;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

public class ResourceManager {
    public static void changeLanguage(Context context, String languageCode){
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            conf.setLocale(new Locale((languageCode.toLowerCase())));
        }else {
            conf.locale = new Locale(languageCode.toLowerCase());
        }
        res.updateConfiguration(conf, dm);
    }
}
