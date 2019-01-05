package com.tiringbring.dailyexpenses.DataController;

import com.tiringbring.dailyexpenses.Entity.DayExpenses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import RoomDb.Expense;

public class ExpenseDataController {
    public List<DayExpenses> DailyExpenses;
    public List<Expense> Expenses;

    public ExpenseDataController(List<Expense> Expenses) {
        this.Expenses = Expenses;
    }

    public  void MakeList(List<Expense> expenses){
        expenses.sort(Comparator.comparing(Expense::getDate));
        Expense fex = expenses.get(0);
        Date prevDate = fex.getDate();
        Date newDate;
        List<Expense> tempExpenses = new ArrayList<Expense>();
        for(Expense ex:expenses)
        {
            if(prevDate==ex.getDate()){
                tempExpenses.add(ex);
            }
            else {
                DayExpenses tempDayExpenses = new DayExpenses(tempExpenses);
                DailyExpenses.add(tempDayExpenses);
                tempExpenses.clear();
                tempExpenses.add(ex);
            }
        }

    }



    public List<DayExpenses> getDailyExpenses() {
        return DailyExpenses;
    }

    public void setDailyExpenses(List<DayExpenses> dailyExpenses) {
        DailyExpenses = dailyExpenses;
    }

    public List<Expense> getExpenses() {
        return Expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        Expenses = expenses;
    }
}
