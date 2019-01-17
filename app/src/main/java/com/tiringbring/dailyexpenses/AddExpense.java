package com.tiringbring.dailyexpenses;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import RoomDb.Expense;
import RoomDb.ExpenseDatabase;

public class AddExpense extends AppCompatActivity {
    private TextView tvDatePicker;
    private EditText etName;
    private EditText etAmount;
    private Button btnSave;
    private DatePickerDialog.OnDateSetListener tvDateSetListner;
    private int  Year;
    private int Month;
    private int Day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        tvDatePicker = (TextView) findViewById(R.id.tvDatePicker);
        etName = (EditText) findViewById(R.id.etName);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnSave = (Button) findViewById(R.id.btnSave);

        Intent intent = getIntent();
        Long expenseId = intent.getLongExtra("expenseId", 0);
        Calendar calendar = Calendar.getInstance();
        if(expenseId != 0)
        {
            Expense expense = MainActivity.myAppRoomDatabase.expenseDao().GetExpenseById(expenseId);
            calendar.setTime(expense.getDate());
            etName.setText(expense.getName());
            etAmount.setText(String.valueOf(expense.getAmount()));
            btnSave.setText("Update");
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Expense newExpense = new Expense();
                    newExpense.setName(etName.getText().toString());
                    newExpense.setAmount(Double.parseDouble(etAmount.getText().toString()));
                    newExpense.setDate(new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(tvDatePicker.getText().toString()));
                    if(expenseId == 0){
                        MainActivity.myAppRoomDatabase.expenseDao().AddExpense(newExpense);
                        Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                    }
                    else {
                        newExpense.setId(expenseId);
                        MainActivity.myAppRoomDatabase.expenseDao().UpdateExpense(newExpense);
                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                    }

                    etName.setText("");
                    etAmount.setText("");
                    btnSave.setText("Save");
                    RelaceFragment();
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
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
                        AddExpense.this,
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
}
