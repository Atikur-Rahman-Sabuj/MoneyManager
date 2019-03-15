package com.tiringbring.dailyexpenses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tiringbring.dailyexpenses.DataController.MySharedPreferences;

public class SettingActivity extends AppCompatActivity {

    private Switch switchNotification;
    private boolean isNotificationEnabled;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        TextView tvDayLimitPanel = (TextView)findViewById(R.id.tvDayLimitPanel);
        final TextView tvDayLimitValue = (TextView)findViewById(R.id.tvDayLimitValue);
        final TextView tvMonthlyLimitValue = (TextView) findViewById(R.id.tvMonthlyLimitValue);
        final TextView tvYearlyLimitValue = (TextView)findViewById(R.id.tvYearlyLimitValue);
        switchNotification = (Switch) findViewById(R.id.switchNotification);
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(getApplicationContext());
        isNotificationEnabled = mySharedPreferences.getIsNotificationEnabled();
        switchNotification.setChecked(isNotificationEnabled);

        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mySharedPreferences.setIsNotificationEnabled(isChecked);
            }
        });

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final long dailyLimit = new MySharedPreferences(getApplicationContext()).getDayilyLimit();
        final long monthlyLimit = new MySharedPreferences(getApplicationContext()).getMonthlyLimit();
        final long yearlyLimit = new MySharedPreferences(getApplicationContext()).getYearlyLimit();
        tvDayLimitValue.setText(String.valueOf(dailyLimit));
        tvMonthlyLimitValue.setText(String.valueOf(monthlyLimit));
        tvYearlyLimitValue.setText(String.valueOf(yearlyLimit));

        tvDayLimitValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.setting_limit_dialouge, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this).create();
                alertDialog.setTitle("Set Daily Limit");
                //alertDialog.setIcon("Icon id here");
                alertDialog.setCancelable(true);
                //alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                //Constant.alertDialog.setMessage("Your Message Here");


                final EditText etLimit = (EditText) dialogView.findViewById(R.id.etLimit);
                etLimit.setText(String.valueOf(dailyLimit));

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long value = Long.parseLong(etLimit.getText().toString());
                        new MySharedPreferences(getApplicationContext()).setDayilyLimit(value);
                        tvDayLimitValue.setText(String.valueOf(value));
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialog.dismiss();
                    }
                });


                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });
        tvMonthlyLimitValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.setting_limit_dialouge, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this).create();
                alertDialog.setTitle("Set Monthly Limit");
                //alertDialog.setIcon("Icon id here");
                alertDialog.setCancelable(true);
                //alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                //Constant.alertDialog.setMessage("Your Message Here");


                final EditText etLimit = (EditText) dialogView.findViewById(R.id.etLimit);
                etLimit.setText(String.valueOf(monthlyLimit));

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long value = Long.parseLong(etLimit.getText().toString());
                        new MySharedPreferences(getApplicationContext()).setMonthlyLimit(value);
                        tvMonthlyLimitValue.setText(String.valueOf(value));
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialog.dismiss();
                    }
                });


                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });
        tvYearlyLimitValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.setting_limit_dialouge, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this).create();
                alertDialog.setTitle("Set Yearly Limit");
                //alertDialog.setIcon("Icon id here");
                alertDialog.setCancelable(true);
                //alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                //Constant.alertDialog.setMessage("Your Message Here");


                final EditText etLimit = (EditText) dialogView.findViewById(R.id.etLimit);
                etLimit.setText(String.valueOf(yearlyLimit));

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long value = Long.parseLong(etLimit.getText().toString());
                        new MySharedPreferences(getApplicationContext()).setYearlyLimit(value);
                        tvYearlyLimitValue.setText(String.valueOf(value));
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialog.dismiss();
                    }
                });


                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });




    }


}