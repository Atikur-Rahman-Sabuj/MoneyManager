package com.tiringbring.dailyexpenses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ExpenseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        if(findViewById(R.id.expensefragmentLayout)!=null){
            if(savedInstanceState != null){
                return;
            }
            expenseFragment fragment = new expenseFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.expensefragmentLayout, fragment).commit();
        }
    }
}
