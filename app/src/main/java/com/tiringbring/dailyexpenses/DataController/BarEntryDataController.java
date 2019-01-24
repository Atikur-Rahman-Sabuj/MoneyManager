package com.tiringbring.dailyexpenses.DataController;

import com.github.mikephil.charting.data.BarEntry;
import com.tiringbring.dailyexpenses.Entity.DayExpenses;
import com.tiringbring.dailyexpenses.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import RoomDb.Expense;

public class BarEntryDataController {
    public List<BarEntry> GetBarEntries(){
        List<Expense> expenses = MainActivity.myAppRoomDatabase.expenseDao().GetExpenses();
        List<DayExpenses> dayExpensesList = new ExpenseDataController(expenses).getDailyExpenses();

        List<DayExpenses> dayExpenses = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        Date startDate = new DateDataController().CropTimeFromDate(calendar);
        calendar1.add(Calendar.DATE, -30);
        Date endDate = new DateDataController().CropTimeFromDate(calendar1);
        if(endDate.before(dayExpensesList.get(dayExpensesList.size()-1).getDate())){
            endDate = dayExpensesList.get(dayExpensesList.size()-1).getDate();
        }
        int i = 0,j = 0;
        DayExpenses itDayExpense = dayExpensesList.get(i);
        List<BarEntry> barEntries = new ArrayList<>();
        for (Date date = startDate; date.after(endDate); calendar.add(Calendar.DATE, -1),date = new DateDataController().CropTimeFromDate(calendar)) {
            // Do your job here with `date`.
            DayExpenses tempdayExpenses = new DayExpenses();
            tempdayExpenses.setDate(date);
            if(date.equals(itDayExpense.date)){
                tempdayExpenses.setTotal(itDayExpense.getTotal());
                itDayExpense = dayExpensesList.get(++i);
            }
            else {
                tempdayExpenses.setTotal(0.0);
            }
            dayExpenses.add(tempdayExpenses);

        }
        for (DayExpenses expense:
             dayExpenses) {
            barEntries.add(new BarEntry(j++, expense.getTotal().intValue()));
        }

        return  barEntries;

    }
}
