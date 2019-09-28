package com.tiringbring.moneymanager.DataController;

import android.content.Context;

import com.github.mikephil.charting.data.PieEntry;
import com.tiringbring.moneymanager.Activity.StartActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RoomDb.Expense;

public class PieEntryDataController {
    private  double Total=0;
    public ArrayList<PieEntry> GetList(Context context, Date date){
         ArrayList<PieEntry> pieEntries = new ArrayList<>();
         List<Expense> expenses = StartActivity.getDBInstance(context).expenseDao().GetExpensesOfaDate(date);
         StartActivity.destroyDBInstance();
        for (Expense expense:expenses
             ) {
            Total += expense.getAmount();
            pieEntries.add(new PieEntry(((int) expense.getAmount()),expense.getName()));
        }

//         if(pieEntries.size()==0){
//             pieEntries.add(new PieEntry(1 , "No entry"));
//         }
         return pieEntries;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public double getTotal() {
        return Total;
    }
}

