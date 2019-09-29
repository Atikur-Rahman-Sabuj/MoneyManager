package com.tiringbring.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tiringbring.moneymanager.ListAdaptor.CategoryListAdaptor;
import com.tiringbring.moneymanager.R;

import java.util.ArrayList;
import java.util.List;

import RoomDb.Category;

public class AddTransactionActivity extends AppCompatActivity {

    private  RecyclerView rvCategoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        rvCategoryList = (RecyclerView) findViewById(R.id.rvCategoryList);
        rvCategoryList.setLayoutManager(new LinearLayoutManager(this));
        LoadCategory(0);


    }
    public void LoadCategory(long selectedId){
        List<Category> categories = StartActivity.getDBInstance(this).mmDao().GetCategories();
        StartActivity.destroyDBInstance();

        List<List<Category>> categoryList = new ArrayList<List<Category>>();
        List<Category> tempCategories = new ArrayList<>();
        for(int i=0; i<categories.size(); i++){
            if(i%4==0 && i>0){
                categoryList.add(tempCategories);
                tempCategories = new ArrayList<>();
            }
            tempCategories.add(categories.get(i));
        }
        if(tempCategories.size()==4){
            categoryList.add(tempCategories);
            tempCategories.clear();
        }
        Category category = new Category();
        category.setName("Add");
        category.setId(-1);
        tempCategories.add(category);
        categoryList.add(tempCategories);
        CategoryListAdaptor adaptor = new CategoryListAdaptor(categoryList, this, selectedId);
        rvCategoryList.setAdapter(adaptor);
    }

}
