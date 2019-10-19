package com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.CustomTransactions;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.DateDataController;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.Entity.DayExpenses;
import com.tiringbring.moneymanager.ListAdaptor.ExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import RoomDb.Transaction;


public class CustomFragment extends Fragment {


    ExpandableListView expandableListView;
    ExpenseExpandableListAdaptor expandableListAdapter;
    List<DayExpenses> dayExpensesList;


    private Date startDate = new Date();
    private Date endDate = new Date();
    private TextView tvStartDate;
    private TextView tvEndDate;
    public TextView tvTotal;
    private TextView tvExpenseTotal, tvIncomeTotal, tvBalanceTotal;
    private DatePickerDialog.OnDateSetListener tvStartDateSetListner;
    private DatePickerDialog.OnDateSetListener tvEndDateSetListner;
    private Double incomeTotal, expenseTotal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view  =  inflater.inflate(R.layout.fragment_custom, container, false);
        tvExpenseTotal = (TextView) view.findViewById(R.id.tvExpenseTotal);
        tvIncomeTotal = (TextView) view.findViewById(R.id.tvIncomeTotal);
        tvBalanceTotal = (TextView) view.findViewById(R.id.tvBalanceTotal);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableExpenseList);
        final DateDataController ddc = new DateDataController();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,  -6);
        endDate = ddc.CropTimeFromDate(c);
        tvStartDate = (TextView) view.findViewById(R.id.tvStartDate);
        tvStartDate.setText(ddc.DatetoDateMonthYear(startDate));

        tvEndDate = (TextView) view.findViewById(R.id.tvEndDate);
        tvEndDate.setText(ddc.DatetoDateMonthYear(endDate));

        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
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
                    Toast.makeText(getContext(), "Invalid selection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
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
                    Toast.makeText(getContext(), "Invalid selection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        GenerateCustomList();
        return  view;
    }

    private void GenerateCustomList() {

        List<Transaction> transactions = StartActivity.getDBInstance(getContext()).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();
        dayExpensesList = new ExpenseDataController(transactions).getDailyExpenses();
        List<DayExpenses> customDayExpenseList = new ExpenseDataController(transactions).MakeCustomList(startDate, endDate);
        calculateTotals(customDayExpenseList);
        tvIncomeTotal.setText(String.format("%.2f",incomeTotal));
        tvExpenseTotal.setText(String.format("%.2f",expenseTotal));
        tvBalanceTotal.setText(String.format("%.2f",incomeTotal - expenseTotal));
        expandableListAdapter = new ExpenseExpandableListAdaptor(getContext(), customDayExpenseList);
        expandableListView.setAdapter(expandableListAdapter);
    }


    private void calculateTotals(List<DayExpenses> dayExpensesList){
        double incomeSum = 0;
        double expenseSum = 0;
        for (DayExpenses dex: dayExpensesList
        ) {
            incomeSum += dex.incomeTotal;
            expenseSum += dex.expenseTotal;
        }
        incomeTotal = incomeSum;
        expenseTotal = expenseSum;
    }


}
