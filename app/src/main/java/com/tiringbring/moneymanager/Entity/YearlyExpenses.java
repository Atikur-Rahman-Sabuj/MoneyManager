package com.tiringbring.moneymanager.Entity;

import com.tiringbring.moneymanager.DataController.DateDataController;

import java.util.ArrayList;
import java.util.List;

public class YearlyExpenses {
    public String year;
    public Double incomeTotal;
    public Double expenseTotal;
    public List<MonthExpenses> monthlyExpenses = new ArrayList<>();

    public YearlyExpenses(List<MonthExpenses> monthlyExpenses) {
        this.year = new DateDataController().DateToYear(monthlyExpenses.get(0).date);
        SetTotal(monthlyExpenses);
        this.monthlyExpenses.addAll(monthlyExpenses);
    }
    private void SetTotal(List<MonthExpenses> monthlyExpenses){
        double incomeSum = 0;
        double expenseSum = 0;
        for (MonthExpenses monex: monthlyExpenses) {
            incomeSum += monex.incomeTotal;
            expenseSum += monex.expenseTotal;
        }
        this.incomeTotal = incomeSum;
        this.expenseTotal = expenseSum;
    }
}
