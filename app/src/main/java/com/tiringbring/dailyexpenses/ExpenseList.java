package com.tiringbring.dailyexpenses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ExpenseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        //if(findViewById(R.id.expensefragmentLayout)!=null){
         //   if(savedInstanceState != null){
         //       return;
           // }
            //ExpenseFragment fragment = new ExpenseFragment();
            //getSupportFragmentManager().beginTransaction().add(R.id.expensefragmentLayout, fragment).commit();
        //}

        if(findViewById(R.id.expensefragmentLayout)!=null){
            if(savedInstanceState != null){
                return;
            }
            //Toast.makeText(getApplicationContext(),"here 1", Toast.LENGTH_LONG).show();
            ExpandableExpenseFragment fragment = new ExpandableExpenseFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.expensefragmentLayout, fragment).commit();
        }
    }
}
