package com.tiringbring.moneymanager.Entity;

import com.tiringbring.moneymanager.DataController.DateDataController;

import java.util.ArrayList;
import java.util.List;

public class YearlyExpenses {
    public String year;
    public Double total;
    public List<MonthExpenses> monthlyExpenses = new ArrayList<>();

    public YearlyExpenses(List<MonthExpenses> monthlyExpenses) {
        this.year = new DateDataController().DateToYear(monthlyExpenses.get(0).date);
        this.total = AddTotal(monthlyExpenses);
        this.monthlyExpenses.addAll(monthlyExpenses);
    }
    private double AddTotal(List<MonthExpenses> monthlyExpenses){
        double sum = 0;
        for (MonthExpenses monex: monthlyExpenses) {
            sum+=monex.total;
        }
        return  sum;
    }
}
