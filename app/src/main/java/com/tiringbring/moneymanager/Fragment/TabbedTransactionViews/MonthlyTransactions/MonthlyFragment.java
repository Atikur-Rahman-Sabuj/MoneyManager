package com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.MonthlyTransactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.Entity.DayExpenses;
import com.tiringbring.moneymanager.Entity.MonthExpenses;
import com.tiringbring.moneymanager.ListAdaptor.ExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.ListAdaptor.MonthlyExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.R;

import java.util.List;

import RoomDb.Transaction;

public class MonthlyFragment extends Fragment {

    ExpandableListView expandableListView;
    ExpenseExpandableListAdaptor expandableListAdapter;
    List<DayExpenses> dayExpensesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monthly, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableExpenseList);
        List<Transaction> transactions = StartActivity.getDBInstance(getContext()).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();

        dayExpensesList = new ExpenseDataController(transactions).getDailyExpenses();

        List<MonthExpenses> monthlyExpenseList = new ExpenseDataController(transactions).GetMonthlyExpenses(dayExpensesList);
        MonthlyExpenseExpandableListAdaptor monthlyExpenseExpandableListAdaptor = new MonthlyExpenseExpandableListAdaptor(getContext(), monthlyExpenseList);
        expandableListView.setAdapter(monthlyExpenseExpandableListAdaptor);

        return view;
    }
}