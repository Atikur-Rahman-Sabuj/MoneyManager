package com.tiringbring.moneymanager.chart;

import android.content.Context;

import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.Entity.MonthTransactions;

import java.util.List;

import RoomDb.Transaction;

public class MonthlyChartData {
    public List<MonthTransactions> GetMonthlyExpenseList(Context context){
        List<Transaction> transactions = StartActivity.getDBInstance(context).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();
        ExpenseDataController expenseDataController = new ExpenseDataController(transactions);
        return expenseDataController.GetMonthlyExpenses(expenseDataController.getDailyExpenses());
    }

    public Double GetMaximumMonthlyIncome(List<MonthTransactions> monthTransactions){
        Double maximum = 0.0;
        for(MonthTransactions monthTransactions1 : monthTransactions){
            if(monthTransactions1.incomeTotal>maximum){
                maximum = monthTransactions1.incomeTotal;
            }
        }
        return  maximum;
    }

    public Double GetMaximumMonthlyExpense(List<MonthTransactions> monthTransactions){
        Double maximum = 0.0;
        for(MonthTransactions monthTransactions1 : monthTransactions){
            if(monthTransactions1.expenseTotal>maximum){
                maximum = monthTransactions1.expenseTotal;
            }
        }
        return  maximum;
    }
}
