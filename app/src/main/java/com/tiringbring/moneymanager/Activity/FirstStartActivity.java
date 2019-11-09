package com.tiringbring.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.tiringbring.moneymanager.DataController.InitialData;
import com.tiringbring.moneymanager.DataController.MySharedPreferences;
import com.tiringbring.moneymanager.Fragment.FirstStartLanguageFragment;
import com.tiringbring.moneymanager.Notification.Notification;
import com.tiringbring.moneymanager.R;

import java.util.Calendar;

public class FirstStartActivity extends AppCompatActivity {
    private FrameLayout flFragmentFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        flFragmentFrame = (FrameLayout)findViewById(R.id.flFragmentFrame);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flFragmentFrame, new FirstStartLanguageFragment())
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .commit();
    }


    public void onFirstRun() {
        MySharedPreferences msp = new MySharedPreferences(getApplicationContext());
        if(msp.getIsFirstRun()){
            InitialData.CreateCategories(getApplicationContext());
            setNotification();
            msp.setIsFirstRun(false);
        }
    }
    private void setNotification() {
        //Long alertTime = new GregorianCalendar().getTimeInMillis()+10*1000;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 21);
        calendar.set(Calendar.SECOND, 0);
        Intent alertIntent = new Intent(getApplicationContext(), Notification.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(getApplicationContext(), 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT));
    }
}
