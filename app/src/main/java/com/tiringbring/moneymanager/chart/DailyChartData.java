package com.tiringbring.moneymanager.chart;

import android.content.Context;

import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.DateDataController;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.Entity.DayTransactions;
import com.tiringbring.moneymanager.Entity.MonthTransactions;

import java.util.Date;
import java.util.List;

import RoomDb.Transaction;

public class DailyChartData {
    public List<DayTransactions> GetDailyExpenseList(Context context){
        List<Transaction> transactions = StartActivity.getDBInstance(context).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();
        ExpenseDataController expenseDataController = new ExpenseDataController(transactions);
        return expenseDataController.getDailyExpenses();
    }


    public Double GetMaximumDailyExpense(List<DayTransactions> dayTransactions){
        Double maximum = 0.0;
        for(DayTransactions dayTransactions1 : dayTransactions){
            if(dayTransactions1.expenseTotal>maximum){
                maximum = dayTransactions1.expenseTotal;
            }
        }
        return  maximum;
    }
}
