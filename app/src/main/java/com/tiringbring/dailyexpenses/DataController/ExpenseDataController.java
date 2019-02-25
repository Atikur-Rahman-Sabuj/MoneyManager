package com.tiringbring.dailyexpenses.DataController;

import com.tiringbring.dailyexpenses.Entity.DayExpenses;
import com.tiringbring.dailyexpenses.Entity.MonthExpenses;
import com.tiringbring.dailyexpenses.Entity.YearlyExpenses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import RoomDb.Expense;

public class ExpenseDataController {
    public List<DayExpenses> DailyExpenses = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public ExpenseDataController(List<Expense> Expenses) {
        if(Expenses.size()>0){
            MakeList(Expenses);
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

    public  void MakeList(List<Expense> expenses){
        Collections.sort(expenses);
        Collections.reverse(expenses);
        //expenses.sort(Comparator.comparing(Expense::getDate).reversed());
        Expense fex = expenses.get(0);
        Date prevDate = fex.getDate();
        Date newDate;
        List<Expense> tempExpenses = new ArrayList<>();
        DayExpenses tempDayExpenses = new DayExpenses();
        for(Expense ex:expenses)
        {
            newDate = ex.getDate();
            if(prevDate.equals(newDate)){
                tempExpenses.add(ex);
            }
            else {
                tempDayExpenses = new DayExpenses(tempExpenses);
                DailyExpenses.add(tempDayExpenses);
                tempExpenses.clear();
                tempExpenses.add(ex);
                prevDate = ex.getDate();
            }
        }
        tempDayExpenses = new DayExpenses(tempExpenses);
        DailyExpenses.add(tempDayExpenses);

    }



    public List<DayExpenses> getDailyExpenses() {
        return DailyExpenses;
    }

    public void setDailyExpenses(List<DayExpenses> dailyExpenses) {
        DailyExpenses = dailyExpenses;
    }
}
