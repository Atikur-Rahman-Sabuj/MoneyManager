package com.tiringbring.moneymanager.DataController;

import java.util.ArrayList;
import java.util.List;

import RoomDb.Category;

public class CategoryDataController {
    public static List<Category> SortcategoryByType(Boolean isIncomeOnTop, List<Category> categories){
        List<Category> onlyIncome = new ArrayList<>();
        List<Category> onlyExpense = new ArrayList<>();
        List<Category> allCategories = new ArrayList<>();
        for (Category category:categories){
            if(category.getIsIncome()){
                onlyIncome.add(category);
            }else {
                onlyExpense.add(category);
            }

        }
        if(isIncomeOnTop){
            allCategories.addAll(onlyIncome);
            allCategories.addAll(onlyExpense);
        }else {
            allCategories.addAll(onlyExpense);
            allCategories.addAll(onlyIncome);
        }
        return allCategories;
    }
}
