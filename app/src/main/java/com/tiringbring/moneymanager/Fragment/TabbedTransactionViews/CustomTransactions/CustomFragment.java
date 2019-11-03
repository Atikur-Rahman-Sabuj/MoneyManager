package com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.CustomTransactions;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiringbring.moneymanager.Activity.BottomNavigationActivity;
import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.CategoryDataController;
import com.tiringbring.moneymanager.DataController.DateDataController;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.DataController.TransactionDataController;
import com.tiringbring.moneymanager.Dialog.SelectCategoryListDialog;
import com.tiringbring.moneymanager.Entity.DayTransactions;
import com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.ITabbedFragments;
import com.tiringbring.moneymanager.ListAdaptor.ExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.ListAdaptor.SelectedCategoryListAdaptor;
import com.tiringbring.moneymanager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import RoomDb.Category;
import RoomDb.Transaction;


public class CustomFragment extends Fragment implements ITabbedFragments {


    ExpandableListView expandableListView;
    ExpenseExpandableListAdaptor expandableListAdapter;
    List<DayTransactions> dayTransactionsList;
    List<Transaction> transactions;
    List<Transaction> filteredTransactions;
    List<DayTransactions> customDayExpenseList;

    private Date startDate = new Date();
    private Date endDate = new Date();
    private TextView tvStartDate;
    private TextView tvEndDate;
    public TextView tvTotal;
    private TextView tvExpenseTotal, tvIncomeTotal, tvBalanceTotal;
    private DatePickerDialog.OnDateSetListener tvStartDateSetListner;
    private DatePickerDialog.OnDateSetListener tvEndDateSetListner;
    private Double incomeTotal, expenseTotal;
    List<Category> allCategories;
    List<Category> selectedCategories;
    private Button btnSelectFilterCategory;
    RecyclerView rvCategoryFilterList;
    SelectedCategoryListAdaptor selectedCategoryListAdaptor;
    private TextView tvMessage;
    private FrameLayout flListFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view  =  inflater.inflate(R.layout.fragment_custom, container, false);
        ((BottomNavigationActivity)getActivity()).setHeaderText("Custom");
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        flListFragment = (FrameLayout) view.findViewById(R.id.flListFrrame);

        allCategories = StartActivity.getDBInstance(getContext()).mmDao().GetCategories();
        StartActivity.destroyDBInstance();
        allCategories = CategoryDataController.SortcategoryByType(true, allCategories);
        selectedCategories = new ArrayList<>();
        btnSelectFilterCategory = (Button) view.findViewById(R.id.btnSelectFilterCategory);
        btnSelectFilterCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectCategoryListDialog().showDialog(getContext(), allCategories, selectedCategories);
            }
        });
        rvCategoryFilterList = (RecyclerView) view.findViewById(R.id.rvCategoryFilterList);
        rvCategoryFilterList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        selectedCategoryListAdaptor = new SelectedCategoryListAdaptor(getContext(), selectedCategories);
        rvCategoryFilterList.setAdapter(selectedCategoryListAdaptor);

        transactions = StartActivity.getDBInstance(getContext()).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();
        filteredTransactions = transactions;

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
        showHideMessage();
        return  view;
    }

    private void GenerateCustomList() {

        //dayTransactionsList = new ExpenseDataController(transactions).getDailyExpenses();
        customDayExpenseList = new ExpenseDataController(filteredTransactions).MakeCustomList(startDate, endDate);
        calculateTotals(customDayExpenseList);
        tvIncomeTotal.setText(String.format("%.2f",incomeTotal));
        tvExpenseTotal.setText(String.format("%.2f",expenseTotal));
        tvBalanceTotal.setText(String.format("%.2f",incomeTotal - expenseTotal));
        expandableListAdapter = new ExpenseExpandableListAdaptor(getContext(), customDayExpenseList);
        expandableListView.setAdapter(expandableListAdapter);
    }


    private void calculateTotals(List<DayTransactions> dayTransactionsList){
        double incomeSum = 0;
        double expenseSum = 0;
        for (DayTransactions dex: dayTransactionsList
        ) {
            incomeSum += dex.incomeTotal;
            expenseSum += dex.expenseTotal;
        }
        incomeTotal = incomeSum;
        expenseTotal = expenseSum;
    }

    @Override
    public void NotifySelectedCategoryChange() {
        selectedCategoryListAdaptor.notifyDataSetChanged();
        filteredTransactions = TransactionDataController.FilterTransactionsByCatgory(transactions, selectedCategories);
        //dayTransactionsList = new ExpenseDataController(filteredTransactions).getDailyExpenses();
        customDayExpenseList = new ExpenseDataController(filteredTransactions).MakeCustomList(startDate, endDate);
        calculateTotals(customDayExpenseList);
        tvIncomeTotal.setText(String.format("%.2f",incomeTotal));
        tvExpenseTotal.setText(String.format("%.2f",expenseTotal));
        tvBalanceTotal.setText(String.format("%.2f",incomeTotal - expenseTotal));
        expandableListAdapter = new ExpenseExpandableListAdaptor(getContext(), customDayExpenseList);
        expandableListView.setAdapter(expandableListAdapter);
        showHideMessage();
    }
    void showHideMessage(){
        if(customDayExpenseList.size()>0){
            tvMessage.setVisibility(View.GONE);
            flListFragment.setVisibility(View.VISIBLE);
        }else{
            flListFragment.setVisibility(View.GONE);
            tvMessage.setVisibility(View.VISIBLE);
        }
    }


}
