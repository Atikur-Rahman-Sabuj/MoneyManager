package com.tiringbring.moneymanager.DataController;

import com.tiringbring.moneymanager.Entity.DayExpenses;
import com.tiringbring.moneymanager.Entity.MonthExpenses;
import com.tiringbring.moneymanager.Entity.YearlyExpenses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import RoomDb.Transaction;

public class ExpenseDataController {
    public List<DayExpenses> DailyExpenses = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public ExpenseDataController(List<Transaction> expens) {
        if(expens.size()>0){
            MakeList(expens);
        }
    }
    public List<MonthExpenses> GetMonthlyExpenses(List<DayExpenses> dailyExpenses){
        List<MonthExpenses> monthlyExpenses = new ArrayList<>();
        if(dailyExpenses.size()>0){
            DateDataController ddc = new DateDataController();
            String month = ddc.DateToMonthYear( dailyExpenses.get(0).date);
            List<DayExpenses> tempDailyExpenses = new ArrayList<>();
            for (DayExpenses dEx:dailyExpenses) {
                if(month.equals(ddc.DateToMonthYear(dEx.date))){
                    tempDailyExpenses.add(dEx);
                }else {
                    MonthExpenses monthExpenses = new MonthExpenses(tempDailyExpenses);
                    monthlyExpenses.add(monthExpenses);
                    tempDailyExpenses.clear();
                    tempDailyExpenses.add(dEx);
                    month = ddc.DateToMonthYear(dEx.date);
                }
            }
            MonthExpenses monthExpenses = new MonthExpenses(tempDailyExpenses);
            monthlyExpenses.add(monthExpenses);
        }
        return monthlyExpenses;
    }

    public List<YearlyExpenses> GetYearlyExpenses(List<MonthExpenses> monthlyExpenseList){
        List<YearlyExpenses> yearlyExpenseList = new ArrayList<>();
        if(monthlyExpenseList.size()>0){
            DateDataController ddc = new DateDataController();
            String year = ddc.DateToYear( monthlyExpenseList.get(0).date);
            List<MonthExpenses> tempMonthlyExpenses = new ArrayList<>();
            for (MonthExpenses mEx:monthlyExpenseList) {
                if(year.equals(ddc.DateToYear(mEx.date))){
                    tempMonthlyExpenses.add(mEx);
                }else {
                    YearlyExpenses yearlyExpenses = new YearlyExpenses(tempMonthlyExpenses);
                    yearlyExpenseList.add(yearlyExpenses);
                    tempMonthlyExpenses.clear();
                    tempMonthlyExpenses.add(mEx);
                    year = ddc.DateToMonthYear(mEx.date);
                }
            }
            YearlyExpenses yearlyExpenses = new YearlyExpenses(tempMonthlyExpenses);
            yearlyExpenseList.add(yearlyExpenses);
        }
        return yearlyExpenseList;
    }
    public  List<DayExpenses> MakeCustomList(Date StartDate, Date EndDate){
        List<DayExpenses> CustomDailyExpenses = new ArrayList<>();
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
        DayExpenses tempDayExpenses = new DayExpenses();
        for(Transaction ex: expens)
        {
            newDate = ex.getDate();
            if(prevDate.equals(newDate)){
                tempExpens.add(ex);
            }
            else {
                tempDayExpenses = new DayExpenses(tempExpens);
                DailyExpenses.add(tempDayExpenses);
                tempExpens.clear();
                tempExpens.add(ex);
                prevDate = ex.getDate();
            }
        }
        tempDayExpenses = new DayExpenses(tempExpens);
        DailyExpenses.add(tempDayExpenses);

    }



    public List<DayExpenses> getDailyExpenses() {
        return DailyExpenses;
    }


    public void setDailyExpenses(List<DayExpenses> dailyExpenses) {
        DailyExpenses = dailyExpenses;
    }
}
