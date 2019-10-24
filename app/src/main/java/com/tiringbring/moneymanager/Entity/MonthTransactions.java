package com.tiringbring.moneymanager.Entity;

import com.tiringbring.moneymanager.DataController.DateDataController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonthTransactions {
    public Date date;
    public String month;
    public Double incomeTotal;
    public Double expenseTotal;
    public List<DayTransactions> dayTransactions = new ArrayList<>();

    public MonthTransactions(List<DayTransactions> dayTransactions) {
        this.date = dayTransactions.get(0).date;
        this.month = new DateDataController().DateToMonthYear(dayTransactions.get(0).date);
        SetTotals(dayTransactions);
        this.dayTransactions.addAll(dayTransactions);
    }
    private void SetTotals(List<DayTransactions> dayExpens){
        double incomeSum = 0;
        double expenseSum = 0;
        for (DayTransactions dayexp: dayExpens) {
            incomeSum += dayexp.incomeTotal;
            expenseSum += dayexp.expenseTotal;
        }
        this.incomeTotal = incomeSum;
        this.expenseTotal = expenseSum;
    }

}
