package com.tiringbring.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.tiringbring.moneymanager.DataController.InitialData;
import com.tiringbring.moneymanager.DataController.MySharedPreferences;
import com.tiringbring.moneymanager.Fragment.FirstStartLanguageFragment;
import com.tiringbring.moneymanager.R;

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
            msp.setIsFirstRun(false);
        }
    }
}
