package com.tiringbring.moneymanager.DataController;

import android.content.Context;

import com.tiringbring.moneymanager.Activity.StartActivity;

import RoomDb.Category;
import RoomDb.MMDao;

public class InitialData {
    public static void CreateCategories(Context context){
        MMDao mmdao = StartActivity.getDBInstance(context).mmDao();

        CreateCategory("Food", false, mmdao);
        CreateCategory("Bills", false, mmdao);
        CreateCategory("Transport", false, mmdao);
        CreateCategory("Shopping", false, mmdao);
        CreateCategory("Entertainment", false, mmdao);
        CreateCategory("Gift", false, mmdao);
        CreateCategory("Book", false, mmdao);
        CreateCategory("Fruits", false, mmdao);
        CreateCategory("Baby", false, mmdao);
        CreateCategory("Telephone", false, mmdao);
        CreateCategory("Health", false, mmdao);
        CreateCategory("Education", false, mmdao);
        CreateCategory("Tax", false, mmdao);
        CreateCategory("Home", false, mmdao);
        CreateCategory("Car", false, mmdao);
        CreateCategory("Others", false, mmdao);


        CreateCategory("Salary", true, mmdao);
        CreateCategory("Awards", true, mmdao);
        CreateCategory("Rental", true, mmdao);
        CreateCategory("Refund", true, mmdao);
        CreateCategory("Lottery", true, mmdao);
        CreateCategory("Investments", true, mmdao);
        CreateCategory("Scholarship", true, mmdao);
        CreateCategory("Others", true, mmdao);
        StartActivity.destroyDBInstance();
    }
    public static void CreateCategory(String name, Boolean isIncome, MMDao mmDao){
        Category category = new Category();
        category.setName(name);
        category.setIsIncome(isIncome);
        mmDao.AddCategory(category);
    }
}
