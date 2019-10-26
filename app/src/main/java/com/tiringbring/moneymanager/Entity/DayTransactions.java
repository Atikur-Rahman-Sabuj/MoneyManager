package com.tiringbring.moneymanager.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RoomDb.Transaction;

public class DayTransactions {
    public Date date;
    public Double incomeTotal;
    public Double expenseTotal;
    public Double total;

    public List<Transaction> dayTransactionList = new ArrayList<>();
    public DayTransactions(){

    }

    public DayTransactions(List<Transaction> dayTransactionList) {
        this.dayTransactionList.addAll(dayTransactionList);
        this.date = dayTransactionList.get(0).getDate();
        SetTotals(dayTransactionList);
    }





    public Double getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal(Double incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public Double getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal(Double expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

    public void SetTotals(List<Transaction> expens){
        double incomeSum = 0;
        double expenseSum = 0;
        for(Transaction ex: expens){
            if(ex.getIsIncome()){
                incomeSum += ex.getAmount();
            }else{
                expenseSum += ex.getAmount();
            }
        }
        setIncomeTotal(incomeSum);
        setExpenseTotal(expenseSum);
    }

    public Double AddTotal(List<Transaction> expens){
        double Sum = 0;
        for(Transaction ex: expens){

            Sum += ex.getAmount();

        }
        return Sum;
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
