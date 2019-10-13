package com.tiringbring.moneymanager.Entity;

import com.tiringbring.moneymanager.DataController.DateDataController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonthExpenses {
    public Date date;
    public String month;
    public Double incomeTotal;
    public Double expenseTotal;
    public List<DayExpenses> dayExpenses = new ArrayList<>();

    public MonthExpenses(List<DayExpenses> dayExpenses) {
        this.date = dayExpenses.get(0).date;
        this.month = new DateDataController().DateToMonthYear(dayExpenses.get(0).date);
        SetTotals(dayExpenses);
        this.dayExpenses.addAll(dayExpenses);
    }
    private void SetTotals(List<DayExpenses> dayExpenses){
        double incomeSum = 0;
        double expenseSum = 0;
        for (DayExpenses dayexp: dayExpenses) {
            incomeSum += dayexp.incomeTotal;
            expenseSum += dayexp.expenseTotal;
        }
        this.incomeTotal = incomeSum;
        this.expenseTotal = expenseSum;
    }

}
