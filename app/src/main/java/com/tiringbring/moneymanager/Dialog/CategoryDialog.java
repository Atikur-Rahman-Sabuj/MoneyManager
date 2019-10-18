package com.tiringbring.moneymanager.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tiringbring.moneymanager.Activity.AddTransactionActivity;
import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.R;

import java.util.List;

import RoomDb.Category;
import RoomDb.Transaction;

public class CategoryDialog {
    public void showDialog(final Context context, final String msg, final Boolean isIncome, final List<List<Category>> categoryList, final String catName) {

        final Dialog dialog = new Dialog(context);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_category_edit_update);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.99);
        int height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView title = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        final TextView tvErrorMessage = (TextView) dialog.findViewById(R.id.tvErrorMessage);
        title.setText(msg);
        tvErrorMessage.setText("");

        final EditText etCategoryName = (EditText) dialog.findViewById(R.id.etCategoryName);
        if(catName.length()>0){
            etCategoryName.setText(catName);
        }


        Button okButton = (Button) dialog.findViewById(R.id.btnSave);
        Button cancleButton = (Button) dialog.findViewById(R.id.btnCancel);


        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                List<Category> categories =  StartActivity.getDBInstance(context).mmDao().GetCategoriesofType(isIncome);
                StartActivity.destroyDBInstance();
                for(int i = 0 ; i<categories.size(); i++){
                    if(categories.get(i).getName().equals(etCategoryName.getText().toString())){
                        tvErrorMessage.setText("Category already exists!");
                        return;
                    }
                }

                if(msg.equals("Add Category")){
                    if(etCategoryName.getText().toString().length()>0){
                        Category category = new Category();
                        category.setName(etCategoryName.getText().toString());
                        category.setIsIncome(isIncome);
                        StartActivity.getDBInstance(context).mmDao().AddCategory(category);
                        StartActivity.destroyDBInstance();
                        ((AddTransactionActivity)context).LoadCategory(0);
                    }
                }else{
                    if(etCategoryName.getText().toString().length()>0){

                        for(int j=0; j<categoryList.size(); j++){
                            for(int k=0; k<categoryList.get(j).size(); k++){
                                if(catName.equals(categoryList.get(j).get(k).getName())){
                                    Category category = categoryList.get(j).get(k);
                                    category.setName(etCategoryName.getText().toString());
                                    StartActivity.getDBInstance(context).mmDao().UpdateCategory(category);
                                    StartActivity.destroyDBInstance();
                                    ((AddTransactionActivity)context).LoadCategory(0);

                                }
                            }
                        }


                    }
                }

                dialog.dismiss();
            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }
}
