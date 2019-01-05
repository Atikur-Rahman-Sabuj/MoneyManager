package com.tiringbring.dailyexpenses.Entity;

import java.util.Date;
import java.util.List;

import RoomDb.Expense;

public class DayExpenses {
    private Date date;
    private List<Expense> dayExpenseList;

    public DayExpenses(List<Expense> dayExpenseList) {
        this.date = dayExpenseList.get(0).getDate();
        this.dayExpenseList = dayExpenseList;
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
