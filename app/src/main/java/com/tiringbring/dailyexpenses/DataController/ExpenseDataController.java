package com.tiringbring.dailyexpenses.DataController;

import com.tiringbring.dailyexpenses.Entity.DayExpenses;

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
//    public List<Expense> SortExpensesByDate(List<Expense> expenses){
//
//    }
}
