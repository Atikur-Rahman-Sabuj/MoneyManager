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
import android.widget.EditText;
import android.widget.TextView;

import com.tiringbring.dailyexpenses.DataController.MySharedPreferences;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        TextView tvDayLimitPanel = (TextView)findViewById(R.id.tvDayLimitPanel);
        TextView tvDayLimitValue = (TextView)findViewById(R.id.tvDayLimitValue);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        long dailyLimit = new MySharedPreferences(getApplicationContext()).getDayilyLimit();
        tvDayLimitValue.setText(String.valueOf(dailyLimit));



        tvDayLimitValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.setting_limit_dialouge, null);
                AlertDialog alertDialog = new AlertDialog.Builder(SettingActivity.this).create();
                alertDialog.setTitle("Set Limit");
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




    }


}
