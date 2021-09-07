package com.tiringbring.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tiringbring.moneymanager.DataController.PieEntryDataController;
import com.tiringbring.moneymanager.ListAdaptor.DayTransactionsRecycleViewAdaptor;
import com.tiringbring.moneymanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import RoomDb.Category;
import RoomDb.Transaction;

public class DailyTransactionsActivity extends ParentActivityWithLeftNavigation {
    private PieChart pcTodaysTransactions;
    private Date date;
    private Boolean isIncome = false;
    private List<Transaction> dayTransactions;
    private List<Transaction> listTransactions;
    private List<Category> allCategories;
    private RecyclerView recyclerView;
    private int  Year;
    private int Month;
    private int Day;
    private Double incomeTotal, expenseTotal;
    private TextView tvDatePicker;
    private LinearLayout btnLeft, btnRight;
    private DatePickerDialog.OnDateSetListener tvDateSetListner;
    private RecyclerView rvTransactionList;
    private TextView tvTypeTop,tvTypeBottom, tvValueTop, tvValueBottom;
    private CardView cvTodayPieChart;
    private ImageView ivBarRight;
    private TextView tvBarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.activity_daily_transactions);
        tvBarText = (TextView) findViewById(R.id.tvBarText);
        ivBarRight = (ImageView) findViewById(R.id.ivBarRight);
        tvBarText.setText(getResources().getString(R.string.expense));
        ivBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });
        allCategories = StartActivity.getDBInstance(getApplicationContext()).mmDao().GetCategories();
        StartActivity.destroyDBInstance();
        pcTodaysTransactions = (PieChart) findViewById(R.id.pcTodaysTransactions);
        date = new Date();
        tvTypeTop = (TextView) findViewById(R.id.tvTypeTop);
        tvTypeBottom = (TextView) findViewById(R.id.tvTypeBottom);
        tvValueTop = (TextView) findViewById(R.id.tvValueTop);
        tvValueBottom = (TextView) findViewById(R.id.tvValueBottom);
        dayTransactions = new ArrayList<>();
        listTransactions = new ArrayList<>();
        btnLeft = (LinearLayout) findViewById(R.id.btnDPleft);
        btnRight = (LinearLayout) findViewById(R.id.btnDPRight);
        tvDatePicker = (TextView) findViewById(R.id.tvDatePicker);
        rvTransactionList = (RecyclerView) findViewById(R.id.rvTransactionList);
        cvTodayPieChart = (CardView) findViewById(R.id.cvTodayPieChart);
        rvTransactionList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH)+1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        tvDatePicker.setText(Day+"/"+Month+"/"+Year);
        //region change income/expense
        cvTodayPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIncome = !isIncome;
                if(isIncome){
                    tvBarText.setText(getResources().getString(R.string.income));
                }else{
                    tvBarText.setText(getResources().getString(R.string.expense));
                }
                listTransactions.clear();
                for(Transaction _transaction: dayTransactions){
                    if(_transaction.getIsIncome()==isIncome){
                        listTransactions.add(_transaction);
                    }
                }
                CountTotal();
                SetTotalTextviews();
                LoadList();
                BindDataToPieChart();
            }
        });
        //endregion
        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        DailyTransactionsActivity.this,
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
                LoadTransactions();
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
                LoadTransactions();

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
                LoadTransactions();

            }
        });
        LoadTransactions();


    }
    private void LoadTransactions(){
        date = new GregorianCalendar(Year, Month-1, Day).getTime();
        dayTransactions = StartActivity.getDBInstance(getApplicationContext()).mmDao().GetTransactionsOfaDate(date);
        StartActivity.destroyDBInstance();
        for(int i=0 ; i<dayTransactions.size(); i++){
            Transaction transaction = dayTransactions.get(i);
            Category cat = null;
            for (int j = 0; j<allCategories.size(); j++) {
                if (transaction.getCategoryId() == allCategories.get(j).getId()) {
                    transaction.setCategory(allCategories.get(j));
                    break;
                }
            }
        }
        listTransactions.clear();
        for(Transaction _transaction: dayTransactions){
            if(_transaction.getIsIncome()==isIncome){
                listTransactions.add(_transaction);
            }
        }
        BindDataToPieChart();
        LoadList();
        CountTotal();
        SetTotalTextviews();
    }

    private void CountTotal(){
        Double incomeSum = 0.0, expenseSum = 0.0;
        for(Transaction transaction: dayTransactions){
            if(transaction.getIsIncome()){
                incomeSum += transaction.getAmount();
            }else {
                expenseSum += transaction.getAmount();
            }
        }
        incomeTotal = incomeSum;
        expenseTotal = expenseSum;
    }

    private void SetTotalTextviews(){
        if(isIncome){
            tvTypeTop.setText(getResources().getString(R.string.income));
            tvTypeBottom.setText(getResources().getString(R.string.expense));
            tvValueTop.setText(String.valueOf(String.format("%.2f", incomeTotal)));
            tvValueBottom.setText(String.valueOf(String.format("%.2f", expenseTotal)));
        }else {
            tvTypeBottom.setText(getResources().getString(R.string.income));
            tvTypeTop.setText(getResources().getString(R.string.expense));
            tvValueBottom.setText(String.valueOf(String.format("%.2f", incomeTotal)));
            tvValueTop.setText(String.valueOf(String.format("%.2f", expenseTotal)));
        }

    }

    private void LoadList(){
        DayTransactionsRecycleViewAdaptor dayTransactionsRecycleViewAdaptor = new DayTransactionsRecycleViewAdaptor(listTransactions, this);
        rvTransactionList.setAdapter(dayTransactionsRecycleViewAdaptor);
    }

    private void BindDataToPieChart() {
        pcTodaysTransactions.setUsePercentValues(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        pcTodaysTransactions.getDescription().setText("");

        pcTodaysTransactions.setCenterText(dateFormat.format(date));
        pcTodaysTransactions.setExtraOffsets(0,0,0,0);
        pcTodaysTransactions.setDragDecelerationFrictionCoef(0.95f);
        pcTodaysTransactions.setDrawHoleEnabled(true);

        pcTodaysTransactions.getLegend().setEnabled(false);
        pcTodaysTransactions.setHoleColor(Color.WHITE);
        pcTodaysTransactions.setHoleRadius(40f);
        pcTodaysTransactions.setTransparentCircleRadius(50f);
        pcTodaysTransactions.animateY(1000, Easing.EaseInOutCubic);
        Double Total = 0.0;
        ArrayList<PieEntry> yValues = new ArrayList<>();
        for (Transaction transaction : listTransactions) {

                Total += transaction.getAmount();
                if(transaction.getName().equals("")||transaction.getName().equals(null)){
                    yValues.add(new PieEntry(((int) transaction.getAmount()), transaction.getCategory().getName()));
                }else {
                    yValues.add(new PieEntry(((int) transaction.getAmount()), transaction.getName()));
                }



        }
        if(yValues.size()<1){
            pcTodaysTransactions.setHoleRadius(50f);
            if(isIncome){
                pcTodaysTransactions.setCenterText(getResources().getString(R.string.no_income));
            }else{
                pcTodaysTransactions.setCenterText(getResources().getString(R.string.no_expense));
            }
           // pcTodaysTransactions.setCenterText(dateFormat.format(date)+" No expense on this date");
        }
        //textView.setText(getResources().getString(R.string.total)+" : "+Total.toString()+"   "+getResources().getString(R.string.chart_slide));
        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(8f);

        data.setValueTextColor(Color.WHITE);

        pcTodaysTransactions.setData(data);
    }

}
