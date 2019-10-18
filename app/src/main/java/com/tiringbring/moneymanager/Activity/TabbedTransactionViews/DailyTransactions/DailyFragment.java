package com.tiringbring.moneymanager.Activity.TabbedTransactionViews.DailyTransactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.Activity.TabbedTransactionViews.ITabbedFragments;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.Dialog.SelectCategoryListDialog;
import com.tiringbring.moneymanager.Entity.DayExpenses;
import com.tiringbring.moneymanager.ListAdaptor.ExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.ListAdaptor.SelectedCategoryListAdaptor;
import com.tiringbring.moneymanager.R;

import java.util.ArrayList;
import java.util.List;

import RoomDb.Transaction;


public class DailyFragment extends Fragment implements ITabbedFragments {


    ExpandableListView expandableListView;
    RecyclerView rvCategoryFilterList;
    ExpenseExpandableListAdaptor expandableListAdapter;
    List<DayExpenses> dayExpensesList;
    Button btnSelectFilterCategory;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        btnSelectFilterCategory = (Button) view.findViewById(R.id.btnSelectFilterCategory);
        btnSelectFilterCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectCategoryListDialog().showDialog(getContext());
            }
        });
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableExpenseList);
        rvCategoryFilterList = (RecyclerView) view.findViewById(R.id.rvCategoryFilterList);
        rvCategoryFilterList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<String> strings = new ArrayList<>();
        strings.add("food");
        strings.add("beverage");
        strings.add("cloth");
        strings.add("food");
        strings.add("beverage");
        strings.add("cloth");
        strings.add("food");
        strings.add("beverage");
        strings.add("cloth");
        SelectedCategoryListAdaptor selectedCategoryListAdaptor = new SelectedCategoryListAdaptor(getContext(), strings);
        rvCategoryFilterList.setAdapter(selectedCategoryListAdaptor);
        List<Transaction> transactions = StartActivity.getDBInstance(getContext()).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();

        dayExpensesList = new ExpenseDataController(transactions).getDailyExpenses();

        expandableListAdapter = new ExpenseExpandableListAdaptor(getContext(), dayExpensesList);
        expandableListView.setAdapter(expandableListAdapter);

        return view;
    }

    @Override
    public void Test() {
        Toast.makeText(getContext(),"getting context", Toast.LENGTH_LONG).show();
    }
}