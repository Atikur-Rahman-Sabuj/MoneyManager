package com.tiringbring.moneymanager.DataController;

import android.content.Context;

import com.github.mikephil.charting.data.PieEntry;
import com.tiringbring.moneymanager.Activity.StartActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RoomDb.Category;
import RoomDb.Transaction;

public class PieEntryDataController {
    private  double Total=0;
    public ArrayList<PieEntry> GetList(Context context, Date date){
         ArrayList<PieEntry> pieEntries = new ArrayList<>();
         List<Transaction> transactions = StartActivity.getDBInstance(context).mmDao().GetTallransactionsAfteraDate(date);
         StartActivity.destroyDBInstance();
        for (Transaction transaction : transactions) {
            if(!transaction.getIsIncome()){
                Total += transaction.getAmount();
                if(transaction.getName().equals("") || transaction.getName().equals(null)){
                    Category category = StartActivity.getDBInstance(context).mmDao().GetCategoryById(transaction.getCategoryId());
                    StartActivity.destroyDBInstance();
                    pieEntries.add(new PieEntry(((int) transaction.getAmount()), category.getName()));
                }else{
                    pieEntries.add(new PieEntry(((int) transaction.getAmount()), transaction.getName()));
                }

            }

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

