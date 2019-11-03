package com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.MonthlyTransactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tiringbring.moneymanager.Activity.BottomNavigationActivity;
import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.CategoryDataController;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.DataController.TransactionDataController;
import com.tiringbring.moneymanager.Dialog.SelectCategoryListDialog;
import com.tiringbring.moneymanager.Entity.DayTransactions;
import com.tiringbring.moneymanager.Entity.MonthTransactions;
import com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.ITabbedFragments;
import com.tiringbring.moneymanager.ListAdaptor.MonthlyExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.ListAdaptor.SelectedCategoryListAdaptor;
import com.tiringbring.moneymanager.R;

import java.util.ArrayList;
import java.util.List;

import RoomDb.Category;
import RoomDb.Transaction;

public class MonthlyFragment extends Fragment implements ITabbedFragments {


    ExpandableListView expandableListView;
    RecyclerView rvCategoryFilterList;
    MonthlyExpenseExpandableListAdaptor monthlyExpenseExpandableListAdaptor;
    List<Transaction> transactions;
    List<Transaction> filteredTransactions;
    List<DayTransactions> dayTransactionsList;
    List<MonthTransactions> monthlyExpenseList;
    Button btnSelectFilterCategory;
    List<Category> allCategories;
    List<Category> selectedCategories;
    SelectedCategoryListAdaptor selectedCategoryListAdaptor;
    private TextView tvMessage;
    private FrameLayout flListFragment;
    private CardView cvList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monthly, container, false);
        ((BottomNavigationActivity)getActivity()).setHeaderText("Monthly");
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        flListFragment = (FrameLayout) view.findViewById(R.id.flListFrrame);
        cvList = (CardView) view.findViewById(R.id.cvList);

        btnSelectFilterCategory = (Button) view.findViewById(R.id.btnSelectFilterCategory);
        btnSelectFilterCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectCategoryListDialog().showDialog(getContext(), allCategories, selectedCategories);
            }
        });

        allCategories = StartActivity.getDBInstance(getContext()).mmDao().GetCategories();
        StartActivity.destroyDBInstance();
        allCategories = CategoryDataController.SortcategoryByType(true, allCategories);
        selectedCategories = new ArrayList<>();
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableExpenseList);
        rvCategoryFilterList = (RecyclerView) view.findViewById(R.id.rvCategoryFilterList);
        rvCategoryFilterList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        selectedCategoryListAdaptor = new SelectedCategoryListAdaptor(getContext(), selectedCategories);
        rvCategoryFilterList.setAdapter(selectedCategoryListAdaptor);
        transactions = StartActivity.getDBInstance(getContext()).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();

        dayTransactionsList = new ExpenseDataController(transactions).getDailyExpenses();
        filteredTransactions = transactions;
        monthlyExpenseList = new ExpenseDataController(filteredTransactions).GetMonthlyExpenses(dayTransactionsList);
        monthlyExpenseExpandableListAdaptor = new MonthlyExpenseExpandableListAdaptor(getContext(), monthlyExpenseList);
        expandableListView.setAdapter(monthlyExpenseExpandableListAdaptor);
        showHideMessage();
        return view;
    }
    @Override
    public void NotifySelectedCategoryChange() {
        selectedCategoryListAdaptor.notifyDataSetChanged();
        filteredTransactions = TransactionDataController.FilterTransactionsByCatgory(transactions, selectedCategories);
        dayTransactionsList = new ExpenseDataController(filteredTransactions).getDailyExpenses();
        monthlyExpenseList = new ExpenseDataController(transactions).GetMonthlyExpenses(dayTransactionsList);
        monthlyExpenseExpandableListAdaptor = new MonthlyExpenseExpandableListAdaptor(getContext(), monthlyExpenseList);
        expandableListView.setAdapter(monthlyExpenseExpandableListAdaptor);
        showHideMessage();
    }
    void showHideMessage(){
        if(monthlyExpenseList.size()>0){
            tvMessage.setVisibility(View.GONE);
            cvList.setVisibility(View.VISIBLE);
        }else{
            cvList.setVisibility(View.GONE);
            tvMessage.setVisibility(View.VISIBLE);
        }
    }
}