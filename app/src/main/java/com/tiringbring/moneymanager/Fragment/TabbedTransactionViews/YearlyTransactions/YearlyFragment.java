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
import com.tiringbring.moneymanager.Entity.YearlyTransactions;
import com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.ITabbedFragments;
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
    List<DayTransactions> dayTransactionsList;
    List<MonthTransactions> monthlyExpenseList;
    List<YearlyTransactions> yearlyTransactionsList;
    Button btnSelectFilterCategory;
    List<Category> allCategories;
    List<Category> selectedCategories;
    SelectedCategoryListAdaptor selectedCategoryListAdaptor;
    private TextView tvMessage;
    private FrameLayout flListFragment;
    private CardView cvList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yearly, container, false);
        ((BottomNavigationActivity)getActivity()).setHeaderText("Yearly");
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        flListFragment = (FrameLayout) view.findViewById(R.id.flListFrrame);
        cvList = (CardView) view.findViewById(R.id.cvList);

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
        dayTransactionsList = new ExpenseDataController(filteredTransactions).getDailyExpenses();
        monthlyExpenseList = new ExpenseDataController(transactions).GetMonthlyExpenses(dayTransactionsList);
        yearlyTransactionsList = new ExpenseDataController(transactions).GetYearlyExpenses(monthlyExpenseList);
        yearlyExpenseExpandableListAdaptor = new YearlyExpenseExpandableListAdaptor(getContext(), yearlyTransactionsList);
        expandableListView.setAdapter(yearlyExpenseExpandableListAdaptor);
        showHideMessage();
        return view;
    }
    @Override
    public void NotifySelectedCategoryChange() {
        selectedCategoryListAdaptor.notifyDataSetChanged();
        filteredTransactions = TransactionDataController.FilterTransactionsByCatgory(transactions, selectedCategories);
        dayTransactionsList = new ExpenseDataController(filteredTransactions).getDailyExpenses();
        monthlyExpenseList = new ExpenseDataController(transactions).GetMonthlyExpenses(dayTransactionsList);
        yearlyTransactionsList = new ExpenseDataController(transactions).GetYearlyExpenses(monthlyExpenseList);
        yearlyExpenseExpandableListAdaptor = new YearlyExpenseExpandableListAdaptor(getContext(), yearlyTransactionsList);
        expandableListView.setAdapter(yearlyExpenseExpandableListAdaptor);
        showHideMessage();
    }
    void showHideMessage(){
        if(yearlyTransactionsList.size()>0){
            tvMessage.setVisibility(View.GONE);
            cvList.setVisibility(View.VISIBLE);
        }else{
            cvList.setVisibility(View.GONE);
            tvMessage.setVisibility(View.VISIBLE);
        }
    }
}