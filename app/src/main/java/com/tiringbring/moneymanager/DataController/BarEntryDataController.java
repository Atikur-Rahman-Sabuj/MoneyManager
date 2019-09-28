package com.tiringbring.moneymanager.DataController;

import android.content.Context;

import com.github.mikephil.charting.data.BarEntry;
import com.tiringbring.moneymanager.Entity.DayExpenses;
import com.tiringbring.moneymanager.Activity.StartActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import RoomDb.Expense;

public class BarEntryDataController {
    public ArrayList<String> labels = new ArrayList<String> ();
    public ArrayList<Date> dates = new ArrayList<>();
    public List<BarEntry> GetBarEntries(Context context){
        List<Expense> expenses = StartActivity.getDBInstance(context).expenseDao().GetExpenses();
        StartActivity.destroyDBInstance();
        List<DayExpenses> dayExpensesList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        //Calendar calendar1 = Calendar.getInstance();
        Date startDate = new DateDataController().CropTimeFromDate(calendar);

        if(expenses.size()>0){
            dayExpensesList = new ExpenseDataController(expenses).getDailyExpenses();
            if(dayExpensesList.get(0).getDate().after(startDate)){
                startDate = dayExpensesList.get(0).getDate();
                calendar.setTime(startDate);
            }
        }

        List<DayExpenses> dayExpenses = new ArrayList<>();
        //calendar1.add(Calendar.DATE, -30);
        Date endDate = startDate;// = new DateDataController().CropTimeFromDate(calendar1);
        DayExpenses itDayExpense = new DayExpenses();
        if(expenses.size()>0){
            endDate = dayExpensesList.get(dayExpensesList.size()-1).getDate();
            itDayExpense = dayExpensesList.get(0);
        }
        int i = 0,j = 0;


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
        if(expenses.size()>0){
            DayExpenses tempdayExpenses = new DayExpenses();
            tempdayExpenses.setDate(endDate);
            tempdayExpenses.setTotal(dayExpensesList.get(dayExpensesList.size()-1).getTotal());
            dayExpenses.add(tempdayExpenses);
        }else {
            calendar.add(Calendar.DATE, 1);
        }
        Date dt;
        for(int d = 0 ; d < 7 ; d++){
            calendar.add(Calendar.DATE, -1);
            dt = new DateDataController().CropTimeFromDate(calendar);
            DayExpenses tempodayExpenses = new DayExpenses();
            tempodayExpenses.setDate(dt);
            tempodayExpenses.setTotal(0.0);
            dayExpenses.add(tempodayExpenses);

        }
        List<BarEntry> barEntries = new ArrayList<>();
        for (DayExpenses expense:
             dayExpenses) {
            labels.add(new DateDataController().DatetoString(expense.date));
            dates.add(expense.date);
            barEntries.add(new BarEntry(j++, expense.getTotal().intValue()));
        }

        return  barEntries;

    }
    public ArrayList<String> getXAxisValues()
    {
        return labels;
    }
}
