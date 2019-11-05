package com.tiringbring.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.tiringbring.moneymanager.DataController.InitialData;
import com.tiringbring.moneymanager.DataController.MySharedPreferences;
import com.tiringbring.moneymanager.R;

public class SplashScreenActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        mainLayout.startAnimation(animation);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                MySharedPreferences msp = new MySharedPreferences(getApplicationContext());
                if(msp.getIsFirstRun()){
                    Intent mainIntent = new Intent(getApplicationContext(), FirstStartActivity.class);
                    startActivity(mainIntent);
                }else {
                    Intent mainIntent = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(mainIntent);
                }
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
