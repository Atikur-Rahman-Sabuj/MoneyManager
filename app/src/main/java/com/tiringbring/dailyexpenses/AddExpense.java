package com.tiringbring.dailyexpenses;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.arch.persistence.room.Room;
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


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Expense expense = new Expense();
                    expense.setName(etName.getText().toString());
                    expense.setAmount(Double.parseDouble(etAmount.getText().toString()));
                    expense.setDate(new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(tvDatePicker.getText().toString()));
                    MainActivity.myAppRoomDatabase.expenseDao().AddExpense(expense);
                    Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                    etName.setText("");
                    etAmount.setText("");
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        });


        Calendar calendar = Calendar.getInstance();
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
                        Year,Month,Day);
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
                tvDatePicker.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
    }
}
