package com.tiringbring.moneymanager.DataController;

import com.tiringbring.moneymanager.Entity.DayTransactions;
import com.tiringbring.moneymanager.Entity.MonthTransactions;
import com.tiringbring.moneymanager.Entity.YearlyTransactions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import RoomDb.Transaction;

public class ExpenseDataController {
    public List<DayTransactions> DailyExpenses = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public ExpenseDataController(List<Transaction> expens) {
        if(expens.size()>0){
            MakeList(expens);
        }
    }
    public List<MonthTransactions> GetMonthlyExpenses(List<DayTransactions> dailyExpenses){
        List<MonthTransactions> monthlyExpenses = new ArrayList<>();
        if(dailyExpenses.size()>0){
            DateDataController ddc = new DateDataController();
            String month = ddc.DateToMonthYear( dailyExpenses.get(0).date);
            List<DayTransactions> tempDailyExpenses = new ArrayList<>();
            for (DayTransactions dEx:dailyExpenses) {
                if(month.equals(ddc.DateToMonthYear(dEx.date))){
                    tempDailyExpenses.add(dEx);
                }else {
                    MonthTransactions monthTransactions = new MonthTransactions(tempDailyExpenses);
                    monthlyExpenses.add(monthTransactions);
                    tempDailyExpenses.clear();
                    tempDailyExpenses.add(dEx);
                    month = ddc.DateToMonthYear(dEx.date);
                }
            }
            MonthTransactions monthTransactions = new MonthTransactions(tempDailyExpenses);
            monthlyExpenses.add(monthTransactions);
        }
        return monthlyExpenses;
    }

    public List<YearlyTransactions> GetYearlyExpenses(List<MonthTransactions> monthlyExpenseList){
        List<YearlyTransactions> yearlyExpenseList = new ArrayList<>();
        if(monthlyExpenseList.size()>0){
            DateDataController ddc = new DateDataController();
            String year = ddc.DateToYear( monthlyExpenseList.get(0).date);
            List<MonthTransactions> tempMonthlyExpenses = new ArrayList<>();
            for (MonthTransactions mEx:monthlyExpenseList) {
                if(year.equals(ddc.DateToYear(mEx.date))){
                    tempMonthlyExpenses.add(mEx);
                }else {
                    YearlyTransactions yearlyTransactions = new YearlyTransactions(tempMonthlyExpenses);
                    yearlyExpenseList.add(yearlyTransactions);
                    tempMonthlyExpenses.clear();
                    tempMonthlyExpenses.add(mEx);
                    year = ddc.DateToMonthYear(mEx.date);
                }
            }
            YearlyTransactions yearlyTransactions = new YearlyTransactions(tempMonthlyExpenses);
            yearlyExpenseList.add(yearlyTransactions);
        }
        return yearlyExpenseList;
    }
    public  List<DayTransactions> MakeCustomList(Date StartDate, Date EndDate){
        List<DayTransactions> CustomDailyExpenses = new ArrayList<>();
        int i = 0;
        while (i<DailyExpenses.size()){
            boolean first = (DailyExpenses.get(i).date).after(StartDate);
            boolean second = (DailyExpenses.get(i).date).before(EndDate);
            if(!first&&!second){
                CustomDailyExpenses.add(DailyExpenses.get(i));
            }
            if(EndDate.after(DailyExpenses.get(i).date)){
                break;
            }
            i++;
        }
        return CustomDailyExpenses;
    }
    public  void MakeList(List<Transaction> expens){
        Collections.sort(expens);
        Collections.reverse(expens);
        //expens.sort(Comparator.comparing(Transaction::getDate).reversed());
        Transaction fex = expens.get(0);
        Date prevDate = fex.getDate();
        Date newDate;
        List<Transaction> tempExpens = new ArrayList<>();
        DayTransactions tempDayTransactions = new DayTransactions();
        for(Transaction ex: expens)
        {
            newDate = ex.getDate();
            if(prevDate.equals(newDate)){
                tempExpens.add(ex);
            }
            else {
                tempDayTransactions = new DayTransactions(tempExpens);
                DailyExpenses.add(tempDayTransactions);
                tempExpens.clear();
                tempExpens.add(ex);
                prevDate = ex.getDate();
            }
        }
        tempDayTransactions = new DayTransactions(tempExpens);
        DailyExpenses.add(tempDayTransactions);

    }



    public List<DayTransactions> getDailyExpenses() {
        return DailyExpenses;
    }


    public void setDailyExpenses(List<DayTransactions> dailyExpenses) {
        DailyExpenses = dailyExpenses;
    }
}
