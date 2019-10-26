package com.tiringbring.moneymanager.DataController;

import android.content.Context;

import com.github.mikephil.charting.data.BarEntry;
import com.tiringbring.moneymanager.Entity.DayTransactions;
import com.tiringbring.moneymanager.Activity.StartActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import RoomDb.Transaction;

public class BarEntryDataController {
    public ArrayList<String> labels = new ArrayList<String> ();
    public ArrayList<Date> dates = new ArrayList<>();
    public List<BarEntry> GetBarEntries(Context context){
        List<Transaction> transactions = StartActivity.getDBInstance(context).mmDao().GetTransactionofType(false);
        StartActivity.destroyDBInstance();
        List<DayTransactions> dayTransactionsList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        //Calendar calendar1 = Calendar.getInstance();
        Date startDate = new DateDataController().CropTimeFromDate(calendar);

        if(transactions.size()>0){
            dayTransactionsList = new ExpenseDataController(transactions).getDailyExpenses();
            if(dayTransactionsList.get(0).getDate().after(startDate)){
                startDate = dayTransactionsList.get(0).getDate();
                calendar.setTime(startDate);
            }
        }

        List<DayTransactions> dayExpens = new ArrayList<>();
        //calendar1.add(Calendar.DATE, -30);
        Date endDate = startDate;// = new DateDataController().CropTimeFromDate(calendar1);
        DayTransactions itDayExpense = new DayTransactions();
        if(transactions.size()>0){
            endDate = dayTransactionsList.get(dayTransactionsList.size()-1).getDate();
            itDayExpense = dayTransactionsList.get(0);
        }
        int i = 0,j = 0;


        for (Date date = startDate; date.after(endDate); calendar.add(Calendar.DATE, -1),date = new DateDataController().CropTimeFromDate(calendar)) {
            // Do your job here with `date`.
            DayTransactions tempdayTransactions = new DayTransactions();
            tempdayTransactions.setDate(date);
            if(date.equals(itDayExpense.date)){
                tempdayTransactions.setExpenseTotal(itDayExpense.getExpenseTotal());
                itDayExpense = dayTransactionsList.get(++i);
            }
            else {
                tempdayTransactions.setExpenseTotal(0.0);
            }
            dayExpens.add(tempdayTransactions);
        }
        if(transactions.size()>0){
            DayTransactions tempdayTransactions = new DayTransactions();
            tempdayTransactions.setDate(endDate);
            tempdayTransactions.setExpenseTotal(dayTransactionsList.get(dayTransactionsList.size()-1).getExpenseTotal());
            dayExpens.add(tempdayTransactions);
        }else {
            calendar.add(Calendar.DATE, 1);
        }
        Date dt;
        for(int d = 0 ; d < 7 ; d++){
            calendar.add(Calendar.DATE, -1);
            dt = new DateDataController().CropTimeFromDate(calendar);
            DayTransactions tempodayTransactions = new DayTransactions();
            tempodayTransactions.setDate(dt);
            tempodayTransactions.setExpenseTotal(0.0);
            dayExpens.add(tempodayTransactions);

        }
        List<BarEntry> barEntries = new ArrayList<>();
        for (DayTransactions expense:
                dayExpens) {
            labels.add(new DateDataController().DatetoString(expense.date));
            dates.add(expense.date);
            barEntries.add(new BarEntry(j++, expense.getExpenseTotal().intValue()));
        }

        return  barEntries;

    }
    public List<String> getXAxisValues()
    {
//        List<String> mlabels = new ArrayList<>();
//        mlabels.add("first");
//        mlabels.add("second");
//        mlabels.add("third");
//        mlabels.add("fourth");
//        mlabels.add("fifth");
//        mlabels.add("sixth");
//        mlabels.add("seventh");
//        mlabels.add("first");
//        mlabels.add("second");
//        mlabels.add("third");
//        mlabels.add("fourth");
//        mlabels.add("fifth");
//        mlabels.add("sixth");
//        mlabels.add("seventh");
//        return mlabels;
          return labels;
    }
}
