package com.tiringbring.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tiringbring.moneymanager.ListAdaptor.CategoryListAdaptor;
import com.tiringbring.moneymanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private Button btnSave;
    private LinearLayout btnLeft, btnRight;
    private TextView tvDatePicker, tvIncomeSelect, tvExpenseSelect;
    private long transactionId = 0;
    private long selectedId;
    private int  Year;
    private int Month;
    private int Day;
    private ImageView ivBarLeft,ivBarRight;
    private TextView tvBarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        tvBarText = (TextView) findViewById(R.id.tvBarText);
        ivBarLeft = (ImageView) findViewById(R.id.ivBarLeft);
        ivBarRight = (ImageView) findViewById(R.id.ivBarRight);
        tvBarText.setText("Add Transaction");
        ivBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
            }
        });
        ivBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });
        rvCategoryList = (RecyclerView) findViewById(R.id.rvCategoryList);
        layoutAddTransaction = (LinearLayout) findViewById(R.id.layoutAddTrsaction);
        etMemo = (EditText) findViewById(R.id.etMemo);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnLeft = (LinearLayout) findViewById(R.id.btnDPleft);
        btnRight = (LinearLayout) findViewById(R.id.btnDPRight);
        tvIncomeSelect = (TextView) findViewById(R.id.tvSelectIncome);
        tvExpenseSelect = (TextView) findViewById(R.id.tvSelectExpense);
        tvExpenseSelect.setBackground(getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
        tvExpenseSelect.setTextColor(getResources().getColor(R.color.white));
        //region income expense type select listners
        tvIncomeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isIncome) return;
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
                if(!isIncome) return;
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
                    if(transactionId != 0){
                        newTransaction.setId(transactionId);
                        StartActivity.getDBInstance(getApplicationContext()).mmDao().UpdateTransaction(newTransaction);
                        StartActivity.destroyDBInstance();
                        Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                        setTitle("Add transaction");
                    }
                    etMemo.setText("");
                    etAmount.setText("");
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });
        //region for update
        Intent intent = getIntent();
        transactionId = intent.getLongExtra("transactionId", 0);
        if(transactionId != 0){
            setTitle("Update transaction");
            Transaction transaction =StartActivity.getDBInstance(getApplicationContext()).mmDao().GetTransactionById(transactionId);
            StartActivity.destroyDBInstance();
            Date date = transaction.getDate();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            Year = calendar1.get(Calendar.YEAR);
            Month = calendar1.get(Calendar.MONTH)+1;
            Day = calendar1.get(Calendar.DAY_OF_MONTH);
            tvDatePicker.setText(Day+"/"+Month+"/"+Year);
            etMemo.setText(transaction.getName());
            etAmount.setText(String.valueOf(transaction.getAmount()));
            SetType(transaction.getIsIncome());
            LoadCategory(transaction.getCategoryId());
        }else {
            SetType(intent.getBooleanExtra("isIncome", false));
        }

        //endregion


    }

    public void SetType(Boolean _isIncome){
        isIncome = _isIncome;
        if(_isIncome){
            tvIncomeSelect.setBackground(getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
            tvIncomeSelect.setTextColor(getResources().getColor(R.color.white));

            tvExpenseSelect.setBackground(null);
            tvExpenseSelect.setTextColor(getResources().getColor(R.color.black));
        }else{
            tvExpenseSelect.setBackground(getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
            tvExpenseSelect.setTextColor(getResources().getColor(R.color.white));

            tvIncomeSelect.setBackground(null);
            tvIncomeSelect.setTextColor(getResources().getColor(R.color.black));
        }
        LoadCategory(0);
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
        category.setName("+");
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
            if(etAmount.requestFocus()){
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(etAmount, InputMethodManager.SHOW_IMPLICIT);
            }

            this.selectedId = selectedId;
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
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
