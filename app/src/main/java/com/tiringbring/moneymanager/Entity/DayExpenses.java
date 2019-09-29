package com.tiringbring.moneymanager.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RoomDb.Transaction;

public class DayExpenses {
    public Date date;
    public Double total;
    public List<Transaction> dayTransactionList = new ArrayList<>();
    public DayExpenses(){

    }

    public DayExpenses(List<Transaction> dayTransactionList) {
        this.dayTransactionList.addAll(dayTransactionList);
        this.date = dayTransactionList.get(0).getDate();
        this.total = AddTotal(dayTransactionList);
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public double AddTotal(List<Transaction> expens){
        double sum = 0;
        for(Transaction ex: expens){
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

    public List<Transaction> getDayTransactionList() {
        return dayTransactionList;
    }

    public void setDayTransactionList(List<Transaction> dayTransactionList) {
        this.dayTransactionList = dayTransactionList;
    }
}
