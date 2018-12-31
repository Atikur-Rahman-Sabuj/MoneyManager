package com.tiringbring.dailyexpenses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.btnButton);
        final Button button2  = (Button) findViewById(R.id.btnSubmit);
        final TextView txtView = (TextView) findViewById(R.id.txtView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtView.setText("Clicked");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtView.setText(String.valueOf(v.getId())+" "+button2.getId());
            }
        });
    }
}
