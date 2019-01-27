package com.tiringbring.dailyexpenses.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RoomDb.Expense;

public class DayExpenses {
    public Date date;
    public Double total;
    public List<Expense> dayExpenseList = new ArrayList<>();
    public DayExpenses(){

    }

    public DayExpenses(List<Expense> dayExpenseList) {
        this.dayExpenseList.addAll(dayExpenseList);
        this.date = dayExpenseList.get(0).getDate();
        this.total = AddTotal(dayExpenseList);
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public double AddTotal(List<Expense> expenses){
        double sum = 0;
        for(Expense ex:expenses){
            sum += ex.getAmount();
        }
        return  sum;

    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Expense> getDayExpenseList() {
        return dayExpenseList;
    }

    public void setDayExpenseList(List<Expense> dayExpenseList) {
        this.dayExpenseList = dayExpenseList;
    }
}
