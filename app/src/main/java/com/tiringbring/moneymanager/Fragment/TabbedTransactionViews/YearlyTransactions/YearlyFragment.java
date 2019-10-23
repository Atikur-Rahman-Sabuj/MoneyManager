package com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.YearlyTransactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.CategoryDataController;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.DataController.TransactionDataController;
import com.tiringbring.moneymanager.Dialog.SelectCategoryListDialog;
import com.tiringbring.moneymanager.Entity.DayExpenses;
import com.tiringbring.moneymanager.Entity.MonthExpenses;
import com.tiringbring.moneymanager.Entity.YearlyExpenses;
import com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.ITabbedFragments;
import com.tiringbring.moneymanager.ListAdaptor.ExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.ListAdaptor.MonthlyExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.ListAdaptor.SelectedCategoryListAdaptor;
import com.tiringbring.moneymanager.ListAdaptor.YearlyExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.R;

import java.util.ArrayList;
import java.util.List;

import RoomDb.Category;
import RoomDb.Transaction;


public class YearlyFragment extends Fragment implements ITabbedFragments {

    ExpandableListView expandableListView;
    RecyclerView rvCategoryFilterList;
    YearlyExpenseExpandableListAdaptor yearlyExpenseExpandableListAdaptor;
    List<Transaction> transactions;
    List<Transaction> filteredTransactions;
    List<DayExpenses> dayExpensesList;
    List<MonthExpenses> monthlyExpenseList;
    List<YearlyExpenses> yearlyExpensesList;
    Button btnSelectFilterCategory;
    List<Category> allCategories;
    List<Category> selectedCategories;
    SelectedCategoryListAdaptor selectedCategoryListAdaptor;
    private TextView tvMessage;
    private FrameLayout flListFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yearly, container, false);
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


        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableExpenseList);
        rvCategoryFilterList = (RecyclerView) view.findViewById(R.id.rvCategoryFilterList);
        rvCategoryFilterList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        selectedCategoryListAdaptor = new SelectedCategoryListAdaptor(getContext(), selectedCategories);
        rvCategoryFilterList.setAdapter(selectedCategoryListAdaptor);
        transactions = StartActivity.getDBInstance(getContext()).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();
        filteredTransactions = transactions;
        dayExpensesList = new ExpenseDataController(filteredTransactions).getDailyExpenses();
        monthlyExpenseList = new ExpenseDataController(transactions).GetMonthlyExpenses(dayExpensesList);
        yearlyExpensesList = new ExpenseDataController(transactions).GetYearlyExpenses(monthlyExpenseList);
        yearlyExpenseExpandableListAdaptor = new YearlyExpenseExpandableListAdaptor(getContext(), yearlyExpensesList);
        expandableListView.setAdapter(yearlyExpenseExpandableListAdaptor);
        showHideMessage();
        return view;
    }
    @Override
    public void NotifySelectedCategoryChange() {
        selectedCategoryListAdaptor.notifyDataSetChanged();
        filteredTransactions = TransactionDataController.FilterTransactionsByCatgory(transactions, selectedCategories);
        dayExpensesList = new ExpenseDataController(filteredTransactions).getDailyExpenses();
        monthlyExpenseList = new ExpenseDataController(transactions).GetMonthlyExpenses(dayExpensesList);
        yearlyExpensesList = new ExpenseDataController(transactions).GetYearlyExpenses(monthlyExpenseList);
        yearlyExpenseExpandableListAdaptor = new YearlyExpenseExpandableListAdaptor(getContext(), yearlyExpensesList);
        expandableListView.setAdapter(yearlyExpenseExpandableListAdaptor);
        showHideMessage();
    }
    void showHideMessage(){
        if(yearlyExpensesList.size()>0){
            tvMessage.setVisibility(View.GONE);
            flListFragment.setVisibility(View.VISIBLE);
        }else{
            flListFragment.setVisibility(View.GONE);
            tvMessage.setVisibility(View.VISIBLE);
        }
    }
}