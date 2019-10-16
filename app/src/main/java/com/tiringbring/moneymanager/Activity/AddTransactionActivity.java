package com.tiringbring.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tiringbring.moneymanager.ListAdaptor.CategoryListAdaptor;
import com.tiringbring.moneymanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import RoomDb.Category;
import RoomDb.Transaction;

public class AddTransactionActivity extends AppCompatActivity {

    private RecyclerView rvCategoryList;
    private LinearLayout layoutAddTransaction;
    private DatePickerDialog.OnDateSetListener tvDateSetListner;
    private Boolean isIncome = false;
    private EditText etMemo, etAmount;
    private Button btnSave,btnLeft, btnRight;
    private TextView tvDatePicker, tvIncomeSelect, tvExpenseSelect;
    private long transactionId = 0;
    private long selectedId;
    private int  Year;
    private int Month;
    private int Day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        rvCategoryList = (RecyclerView) findViewById(R.id.rvCategoryList);
        layoutAddTransaction = (LinearLayout) findViewById(R.id.layoutAddTrsaction);
        etMemo = (EditText) findViewById(R.id.etMemo);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLeft = (Button) findViewById(R.id.btnDPleft);
        btnRight = (Button) findViewById(R.id.btnDPRight);
        tvIncomeSelect = (TextView) findViewById(R.id.tvSelectIncome);
        tvExpenseSelect = (TextView) findViewById(R.id.tvSelectExpense);
        tvExpenseSelect.setBackground(getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
        tvExpenseSelect.setTextColor(getResources().getColor(R.color.white));
        //region income expense type select listners
        tvIncomeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIncome = true;
                tvIncomeSelect.setBackground(getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
                tvIncomeSelect.setTextColor(getResources().getColor(R.color.white));

                tvExpenseSelect.setBackground(null);
                tvExpenseSelect.setTextColor(getResources().getColor(R.color.black));

                layoutAddTransaction.setVisibility(View.GONE);
                LoadCategory(0);
            }
        });
        tvExpenseSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIncome = false;
                tvExpenseSelect.setBackground(getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
                tvExpenseSelect.setTextColor(getResources().getColor(R.color.white));

                tvIncomeSelect.setBackground(null);
                tvIncomeSelect.setTextColor(getResources().getColor(R.color.black));

                layoutAddTransaction.setVisibility(View.GONE);
                LoadCategory(0);
            }
        });
        //endregion

        rvCategoryList.setLayoutManager(new LinearLayoutManager(this));
        //user this for horizontal list
        //rvCategoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tvDatePicker = (TextView) findViewById(R.id.tvDatePicker);
        LoadCategory(0);
        Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH)+1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        tvDatePicker.setText(Day+"/"+Month+"/"+Year);
        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        AddTransactionActivity.this,
                        android.R.style.Theme_Material_Light_Dialog,
                        tvDateSetListner,
                        Year,Month-1,Day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
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
                //RelaceFragment();
                //ChangeTotal();
            }
        };


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

            }
        });




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Transaction newTransaction = new Transaction();
                    newTransaction.setName(etMemo.getText().toString());
                    newTransaction.setIsIncome(isIncome);
                    newTransaction.setCategoryId(selectedId);
                    newTransaction.setAmount(Double.parseDouble(etAmount.getText().toString()));
                    newTransaction.setDate(new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(tvDatePicker.getText().toString()));
                    if(transactionId == 0){
                        StartActivity.getDBInstance(getApplicationContext()).mmDao().AddTransaction(newTransaction);
                        StartActivity.destroyDBInstance();
                        Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void LoadCategory(long selectedId){
        List<Category> categories = StartActivity.getDBInstance(this).mmDao().GetCategoriesofType(isIncome);
        StartActivity.destroyDBInstance();
        List<List<Category>> categoryList = new ArrayList<List<Category>>();
        List<Category> tempCategories = new ArrayList<>();
        for(int i=0; i<categories.size(); i++){
            if(i%4==0 && i>0){
                categoryList.add(tempCategories);
                tempCategories = new ArrayList<>();
            }
            tempCategories.add(categories.get(i));
        }
        if(tempCategories.size()==4){
            categoryList.add(tempCategories);
            tempCategories = new ArrayList<>();
        }
        Category category = new Category();
        category.setName("Add");
        category.setId(-1);
        tempCategories.add(category);
        categoryList.add(tempCategories);
        CategoryListAdaptor adaptor = new CategoryListAdaptor(categoryList, this, selectedId, isIncome);
        rvCategoryList.setAdapter(adaptor);

        if(selectedId==0){
            layoutAddTransaction.setVisibility(View.GONE);
            this.selectedId = selectedId;
        }else {
            layoutAddTransaction.setVisibility(View.VISIBLE);
            this.selectedId = selectedId;
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.options, menu);
//        MenuItem item = menu.findItem(R.id.spinner);
//        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
//
//        ArrayAdapter<CharSequence> mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.dropdown_income_expense, R.layout.spinner_item);
//        mSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
//        //spinner.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
//
//        spinner.setAdapter(mSpinnerAdapter); // set the adapter to provide layout of rows and content
//        //spinner.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
//        spinner.requestLayout();
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0: {
//                        if(!isIncome){
//                            isIncome = true;
//                            layoutAddTransaction.setVisibility(View.GONE);
//                            LoadCategory(0);
//
//                        }
//
//                        break;
//                    }
//                    case 1: {
//                       if(isIncome){
//                           isIncome = false;
//                           layoutAddTransaction.setVisibility(View.GONE);
//                           LoadCategory(0);
//                       }
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Toast.makeText(getApplicationContext(),"nothing selected", Toast.LENGTH_SHORT).show();
//
//            }
//        }); // set the listener, to perform actions based on item selection
//        return super.onCreateOptionsMenu(menu);
//    }
}
