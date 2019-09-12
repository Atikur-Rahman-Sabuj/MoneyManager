package com.tiringbring.dailyexpenses.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiringbring.dailyexpenses.Entity.DayExpenses;
import com.tiringbring.dailyexpenses.Fragment.ExpenseFragment;
import com.tiringbring.dailyexpenses.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import RoomDb.Expense;

public class AddExpenseActivity extends AppCompatActivity {
    private TextView tvDatePicker;
    private EditText etName;
    private EditText etAmount;
    private Button btnSave;
    private Button btnLeft;
    private Button btnRight;
    private TextView tvTotal;
    private LinearLayout myLinearLayout;
    private DatePickerDialog.OnDateSetListener tvDateSetListner;
    private int  Year;
    private int Month;
    private int Day;
    private long expenseId = 0;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        myLinearLayout=(LinearLayout)findViewById(R.id.linearLayout);

        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvDatePicker = (TextView) findViewById(R.id.tvDatePicker);
        etName = (EditText) findViewById(R.id.etName);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLeft = (Button) findViewById(R.id.btnDPleft);
        btnRight = (Button) findViewById(R.id.btnDPRight);
        tvTotal = (TextView) findViewById(R.id.tvAddTotal);

        Intent intent = getIntent();
        expenseId = intent.getLongExtra("expenseId", 0);
        Calendar calendar = Calendar.getInstance();
        if(expenseId != 0)
        {
            Expense expense = StartActivity.getDBInstance(getApplicationContext()).expenseDao().GetExpenseById(expenseId);
            StartActivity.destroyDBInstance();
            calendar.setTime(expense.getDate());
            etName.setText(expense.getName());
            etAmount.setText(String.valueOf(expense.getAmount()));
            btnSave.setText("Update");
        }

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.DAY_OF_MONTH, Day);
                calendar1.set(Calendar.MONTH, Month-1);
                calendar1.set(Calendar.YEAR, Year);
                calendar1.add(Calendar.DAY_OF_MONTH, 1);
                Year = calendar1.get(Calendar.YEAR);
                Month = calendar1.get(Calendar.MONTH)+1;
                Day = calendar1.get(Calendar.DAY_OF_MONTH);
                tvDatePicker.setText(Day+"/"+Month+"/"+Year);
                RelaceFragment();
                ChangeTotal();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.DAY_OF_MONTH, Day);
                calendar1.set(Calendar.MONTH, Month-1);
                calendar1.set(Calendar.YEAR, Year);
                calendar1.add(Calendar.DAY_OF_MONTH, -1);
                Year = calendar1.get(Calendar.YEAR);
                Month = calendar1.get(Calendar.MONTH)+1;
                Day = calendar1.get(Calendar.DAY_OF_MONTH);
                tvDatePicker.setText(Day+"/"+Month+"/"+Year);
                RelaceFragment();
                ChangeTotal();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Expense newExpense = new Expense();
                    if(etName.getText().toString().equals("")){
                        newExpense.setName("Untitled");
                    }else {
                        newExpense.setName(etName.getText().toString());
                    }

                    newExpense.setAmount(Double.parseDouble(etAmount.getText().toString()));
                    newExpense.setDate(new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(tvDatePicker.getText().toString()));
                    if(expenseId == 0){
                        StartActivity.getDBInstance(getApplicationContext()).expenseDao().AddExpense(newExpense);
                        StartActivity.destroyDBInstance();
                        //Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                    }
                    else {
                        newExpense.setId(expenseId);
                        StartActivity.getDBInstance(getApplicationContext()).expenseDao().UpdateExpense(newExpense);
                        StartActivity.destroyDBInstance();
                        expenseId = 0;
                        //Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                    }

                    etName.setText("");
                    etAmount.setText("");
                    btnSave.setText("Save");
                    if(etName.requestFocus()) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        etAmount.clearFocus();
                    }
                    RelaceFragment();
                    ChangeTotal();
                }catch (Exception e)
                {
                    //Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH)+1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        tvDatePicker.setText(Day+"/"+Month+"/"+Year);
        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        AddExpenseActivity.this,
                         android.R.style.Theme_Holo_Light,
                        tvDateSetListner,
                        Year,Month-1,Day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        tvDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                Year = year;
                Month = month;
                Day = dayOfMonth;
                tvDatePicker.setText(dayOfMonth+"/"+month+"/"+year);
                RelaceFragment();
                ChangeTotal();
            }
        };
        if(findViewById(R.id.dayExpensesFragmentLayout)!=null)
        {
            ExpenseFragment expenseFragment = new ExpenseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Day", Day);
            bundle.putInt("Month", Month);
            bundle.putInt("Year", Year);
            expenseFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.dayExpensesFragmentLayout,expenseFragment).commit();
            ChangeTotal();
        }

    }
    private  void RelaceFragment(){
        if(findViewById(R.id.dayExpensesFragmentLayout)!=null)
        {
            ExpenseFragment expenseFragment = new ExpenseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Day", Day);
            bundle.putInt("Month", Month);
            bundle.putInt("Year", Year);
            expenseFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.dayExpensesFragmentLayout,expenseFragment).commit();
        }
    }
    public void ChangeTotal(){
        Date date = new GregorianCalendar(Year, Month-1, Day).getTime();
        List<Expense> expenses = StartActivity.getDBInstance(getApplicationContext()).expenseDao().GetExpensesOfaDate(date);
        StartActivity.destroyDBInstance();
        Double totoal = new DayExpenses().AddTotal(expenses);
        tvTotal.setText(String.format("%.2f", totoal));
        //mContentView.setText(String.format("%.2f",mValues.get(position).getAmount()));

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
