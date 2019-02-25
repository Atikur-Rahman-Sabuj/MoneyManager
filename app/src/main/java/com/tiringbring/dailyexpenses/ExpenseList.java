package com.tiringbring.dailyexpenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseList extends AppCompatActivity {

    private Button btnAddNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        btnAddNew = findViewById(R.id.btnAddNewExpense);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddExpense.class);
                startActivity(intent);
                finish();
            }
        });
//        if(findViewById(R.id.expensefragmentLayout)!=null){
//            if(savedInstanceState != null){
//                return;
//            }
//            ExpandableExpenseFragment fragment = new ExpandableExpenseFragment();
//            getSupportFragmentManager().beginTransaction().add(R.id.expensefragmentLayout, fragment).commit();
//        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> mSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.dropdown_string_array,R.layout.spinner_item);
        mSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        //spinner.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));

        spinner.setAdapter(mSpinnerAdapter); // set the adapter to provide layout of rows and content
        //spinner.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        spinner.requestLayout();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
                        GenerateDailyList();
                        break;
                    }
                    case 1:{
                        GenerateMonthlyList();
                        break;
                    }
                    case 2:{
                        GenerateYearlyList();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // Toast.makeText(getApplicationContext(),"nothing selected", Toast.LENGTH_SHORT).show();

            }
        }); // set the listener, to perform actions based on item selection

        return super.onCreateOptionsMenu(menu);
    }

    private void GenerateDailyList(){
        if(findViewById(R.id.expensefragmentLayout)!=null){
            Bundle bundle = new Bundle();
            bundle.putString("DDITEMS", "daily");
            ExpandableExpenseFragment fragment = new ExpandableExpenseFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.expensefragmentLayout, fragment).commit();
        }
    }
    private void GenerateMonthlyList(){
        if(findViewById(R.id.expensefragmentLayout)!=null){
            Bundle bundle = new Bundle();
            bundle.putString("DDITEMS", "monthly");
            ExpandableExpenseFragment fragment = new ExpandableExpenseFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.expensefragmentLayout, fragment).commit();
        }
    }
    private void GenerateYearlyList(){
        if(findViewById(R.id.expensefragmentLayout)!=null){
            Bundle bundle = new Bundle();
            bundle.putString("DDITEMS", "yearly");
            ExpandableExpenseFragment fragment = new ExpandableExpenseFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.expensefragmentLayout, fragment).commit();
        }
    }



    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settingMenuButton) {
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
