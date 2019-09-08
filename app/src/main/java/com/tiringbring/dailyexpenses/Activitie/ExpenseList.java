package com.tiringbring.dailyexpenses.Activitie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tiringbring.dailyexpenses.DataController.DateDataController;
import com.tiringbring.dailyexpenses.Fragment.ExpandableExpenseFragment;
import com.tiringbring.dailyexpenses.R;

import java.util.Calendar;
import java.util.Date;

public class ExpenseList extends AppCompatActivity {

    private Button btnAddNew;
    private Date startDate = new Date();
    private Date endDate = new Date();
    private TextView tvStartDate;
    private TextView tvEndDate;
    public TextView tvTotal;
    public LinearLayout layoutUpper;
    private LinearLayout layoutLower;
    private DatePickerDialog.OnDateSetListener tvStartDateSetListner;
    private DatePickerDialog.OnDateSetListener tvEndDateSetListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        final DateDataController ddc = new DateDataController();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,  -6);
        endDate = ddc.CropTimeFromDate(c);
        layoutUpper = (LinearLayout) findViewById(R.id.layoutUpper);
        layoutLower = (LinearLayout) findViewById(R.id.layoutLower);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvStartDate.setText(ddc.DatetoDateMonthYear(startDate));

        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        tvEndDate.setText(ddc.DatetoDateMonthYear(endDate));

        tvTotal = (TextView) findViewById(R.id.tvCustomTotal);
        btnAddNew = findViewById(R.id.btnAddNewExpense);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddExpense.class);

                    startActivity(intent);

                finish();
            }
        });
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        ExpenseList.this,
                        android.R.style.Theme_Holo_Light,
                        tvStartDateSetListner,
                        ddc.DateToYearNo(startDate),ddc.DateToMonthNo(startDate),ddc.DateToDayOfMonth(startDate));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        tvStartDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String newDate = dayOfMonth+"/"+(month+1)+"/"+year;
                Date newStartDate = ddc.StringToDate(newDate);
                if(!endDate.after(newStartDate)){
                    tvStartDate.setText(newDate);
                    startDate = newStartDate;
                    GenerateCustomList();
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid selection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        ExpenseList.this,
                        android.R.style.Theme_Holo_Light,
                        tvEndDateSetListner,
                        ddc.DateToYearNo(endDate),ddc.DateToMonthNo(endDate),ddc.DateToDayOfMonth(endDate));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        tvEndDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String newDate = dayOfMonth+"/"+(month+1)+"/"+year;
                Date newEndDate = ddc.StringToDate(newDate);
                if(!newEndDate.after(startDate)){
                    tvEndDate.setText(newDate);
                    endDate = newEndDate;
                    GenerateCustomList();
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid selection", Toast.LENGTH_SHORT).show();
                }
            }
        };

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

        ArrayAdapter<CharSequence> mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.dropdown_string_array, R.layout.spinner_item);
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
                        layoutUpper.setVisibility(View.GONE);
                        GenerateDailyList();
                        break;
                    }
                    case 1:{
                        layoutUpper.setVisibility(View.GONE);
                        GenerateMonthlyList();
                        break;
                    }
                    case 2:{
                        layoutUpper.setVisibility(View.GONE);
                        GenerateYearlyList();
                        break;
                    }
                    case 3:{
                        layoutUpper.setVisibility(View.VISIBLE);
                        GenerateCustomList();
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
    private void GenerateCustomList(){
        if(findViewById(R.id.expensefragmentLayout)!=null){
            Bundle bundle = new Bundle();
            bundle.putString("DDITEMS", "custom");
            bundle.putString("START_DATE", new DateDataController().DatetoDateMonthBigYear(startDate));
            bundle.putString("END_DATE", new DateDataController().DatetoDateMonthBigYear(endDate));
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
    public void setTotalTextView(double Total){
        tvTotal.setText(getResources().getString(R.string.total)+" "+String.format("%.2f", Total));
    }
}
