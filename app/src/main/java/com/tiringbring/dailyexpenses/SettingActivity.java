package com.tiringbring.dailyexpenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        TextView tvDayLimitPanel = (TextView)findViewById(R.id.tvDayLimitPanel);
        TextView tvDayLimitValue = (TextView)findViewById(R.id.tvDayLimitValue);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        long dailyLimit = pref.getLong("limit_daily_expense", 20000);
        tvDayLimitValue.setText(String.valueOf(dailyLimit));
        View dialogView = getLayoutInflater().inflate(R.layout.setting_limit_dialouge, null);


        tvDayLimitValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this).create();
                alertDialog.setTitle("Set Limit");
                //alertDialog.setIcon("Icon id here");
                alertDialog.setCancelable(false);
                //Constant.alertDialog.setMessage("Your Message Here");


                final EditText etLimit = (EditText) dialogView.findViewById(R.id.etLimit);
                etLimit.setText(String.valueOf(dailyLimit));

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long value = Long.parseLong(etLimit.getText().toString());
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putLong("limit_daily_expense", value);
                        editor.commit();
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




    }
}
