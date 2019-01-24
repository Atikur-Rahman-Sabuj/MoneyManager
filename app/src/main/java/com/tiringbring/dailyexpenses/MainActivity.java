package com.tiringbring.dailyexpenses;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import RoomDb.Expense;
import RoomDb.ExpenseDatabase;

public class MainActivity extends AppCompatActivity {
    public static ExpenseDatabase myAppRoomDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button)findViewById(R.id.btnButton);
        final Button btnShowExpenses = (Button) findViewById(R.id.btnShowExpenseList);
        final Button btnSetting = (Button) findViewById(R.id.btnSetting);
        final Button btnShowChart = (Button) findViewById(R.id.btnShowChart);
        final TextView txtView = (TextView) findViewById(R.id.txtView);
        myAppRoomDatabase = Room.databaseBuilder(getApplicationContext(),ExpenseDatabase.class, "Expensedb").allowMainThreadQueries().build();


        btnShowChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchAddExpenseActivity();
            }
        });
        btnShowExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpenseList.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

    }
    private void LaunchAddExpenseActivity()
    {
        Intent intent = new Intent(this, AddExpense.class);
        startActivity(intent);
    }
}
