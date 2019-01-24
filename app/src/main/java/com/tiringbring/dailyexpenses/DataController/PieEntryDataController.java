package com.tiringbring.dailyexpenses.DataController;

import com.github.mikephil.charting.data.PieEntry;
import com.tiringbring.dailyexpenses.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RoomDb.Expense;

public class PieEntryDataController {
    public ArrayList<PieEntry> GetList(Date date){
         ArrayList<PieEntry> pieEntries = new ArrayList<>();
         List<Expense> expenses = MainActivity.myAppRoomDatabase.expenseDao().GetExpensesOfaDate(date);
         expenses.forEach(expense ->{
             pieEntries.add(new PieEntry(((int) expense.getAmount()),expense.getName()));
         });
         return pieEntries;
    }
}

