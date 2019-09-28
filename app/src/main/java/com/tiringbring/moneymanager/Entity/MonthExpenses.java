package com.tiringbring.moneymanager.Entity;

import com.tiringbring.moneymanager.DataController.DateDataController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonthExpenses {
    public Date date;
    public String month;
    public Double total;
    public List<DayExpenses> dayExpenses = new ArrayList<>();

    public MonthExpenses(List<DayExpenses> dayExpenses) {
        this.date = dayExpenses.get(0).date;
        this.month = new DateDataController().DateToMonthYear(dayExpenses.get(0).date);
        this.total = AddTotal(dayExpenses);
        this.dayExpenses.addAll(dayExpenses);
    }
    private double AddTotal(List<DayExpenses> dayExpenses){
        double sum = 0;
        for (DayExpenses dayexp: dayExpenses) {
            sum+=dayexp.total;
        }
        return  sum;
    }

}
