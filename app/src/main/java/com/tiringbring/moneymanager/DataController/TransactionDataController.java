package com.tiringbring.moneymanager.DataController;

import java.util.ArrayList;
import java.util.List;

import RoomDb.Category;
import RoomDb.Transaction;

public class TransactionDataController {
    public static List<Transaction> FilterTransactionsByCatgory(List<Transaction> transactions, List<Category> categories){
        if(categories.size()==0){
            return transactions;
        }
        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction:transactions){
            if(DoesCategoryExists(transaction.getCategoryId(), categories)){
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    public static Boolean DoesCategoryExists(Long categoryid, List<Category> categories){
        for(Category category:categories){
            if(category.getId() == categoryid){
                return true;
            }
        }
        return false;
    }
}
