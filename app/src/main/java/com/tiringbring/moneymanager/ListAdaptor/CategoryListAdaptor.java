package com.tiringbring.moneymanager.ListAdaptor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.tiringbring.moneymanager.Activity.AddTransactionActivity;
import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.Dialog.CategoryDialog;
import com.tiringbring.moneymanager.R;

import java.util.List;

import RoomDb.Category;

public class CategoryListAdaptor extends RecyclerView.Adapter<CategoryListAdaptor.ViewHolder> {

    private final List<List<Category>> categoryList;
    private Context context;
    private long selectedId;
    private boolean isIncome;
    private View.OnClickListener tvClickListner;
    private View.OnLongClickListener tvLongClickListner;

    public CategoryListAdaptor(final List<List<Category>> categoryList, final Context context, long selectedId, final boolean isIncome) {
        this.categoryList = categoryList;
        this.context = context;
        this.selectedId = selectedId;
        this.isIncome = isIncome;
        //region on click listener
        tvClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                final EditText editText = new EditText(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(200, 0, 200, 0);
                editText.setLayoutParams(lp);

                //editText.setPadding(200, 0,200,0);
                if(textView.getText().equals("Add")){
//                    new AlertDialog.Builder(context)
//                            .setTitle("Add category")
//                            .setView(editText)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    //Toast.makeText(context,editText.getText(),Toast.LENGTH_LONG).show();
//                                    if(editText.getText().toString().length()>0){
//                                        Category category = new Category();
//                                        category.setName(editText.getText().toString());
//                                        category.setIsIncome(isIncome);
//                                        StartActivity.getDBInstance(context).mmDao().AddCategory(category);
//                                        StartActivity.destroyDBInstance();
//                                        ((AddTransactionActivity)context).LoadCategory(0);
//                                    }
//                                }
//                            }).setNegativeButton("Cancel", null).show();
                    CategoryDialog categoryDialog = new CategoryDialog();
                    categoryDialog.showDialog(context, "Add Category", isIncome, categoryList, "");
                }else{
                    for(int j=0; j<categoryList.size(); j++){
                        for(int k=0; k<categoryList.get(j).size(); k++){
                            if(textView.getText().equals(categoryList.get(j).get(k).getName())){
                                ((AddTransactionActivity)context).LoadCategory(categoryList.get(j).get(k).getId());
                            }
                        }
                    }
                }
            }
        };
        //endregion

        //region on long click listener
        tvLongClickListner = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TextView textView = (TextView) view;
//                final EditText editText = new EditText(context);
                final String catName = textView.getText().toString();
//                editText.setText(textView.getText());
//                if(!textView.getText().equals("Add")){
//                    new AlertDialog.Builder(context)
//                            .setTitle("Update category")
//                            .setView(editText)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    //Toast.makeText(context,editText.getText(),Toast.LENGTH_LONG).show();
//                                    if(editText.getText().toString().length()>0){
//
//                                        for(int j=0; j<categoryList.size(); j++){
//                                            for(int k=0; k<categoryList.get(j).size(); k++){
//                                                if(catName.equals(categoryList.get(j).get(k).getName())){
//                                                    Category category = categoryList.get(j).get(k);
//                                                    category.setName(editText.getText().toString());
//                                                    StartActivity.getDBInstance(context).mmDao().UpdateCategory(category);
//                                                    StartActivity.destroyDBInstance();
//                                                    ((AddTransactionActivity)context).LoadCategory(0);
//
//                                                }
//                                            }
//                                        }
//
//
//                                    }
//                                }
//                            }).setNegativeButton("Cancel", null).show();
//                }
//
                CategoryDialog categoryDialog = new CategoryDialog();
                categoryDialog.showDialog(context, "Update Category", isIncome, categoryList, catName);
                return false;
            }
        };
        //endregion
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.first.setText(categoryList.get(position).get(0).getName());
        if(categoryList.get(position).get(0).getId() == selectedId){
            holder.first.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
            holder.first.setTextColor(context.getResources().getColor(R.color.white));
        }
        if(categoryList.get(position).size()>1){
            holder.second.setText(categoryList.get(position).get(1).getName());
            if(categoryList.get(position).get(1).getId() == selectedId){
                holder.second.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
                holder.second.setTextColor(context.getResources().getColor(R.color.white));
            }
        }else {
            holder.second.setVisibility(View.GONE);
        }

        if(categoryList.get(position).size()>2){
            holder.third.setText(categoryList.get(position).get(2).getName());
            if(categoryList.get(position).get(2).getId() == selectedId){
                holder.third.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
                holder.third.setTextColor(context.getResources().getColor(R.color.white));
            }
        }else{
            holder.third.setVisibility(View.GONE);
        }

        if(categoryList.get(position).size()>3){
            holder.fourth.setText(categoryList.get(position).get(3).getName());
            if(categoryList.get(position).get(3).getId() == selectedId){
                holder.fourth.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_rectangle_amber));
                holder.fourth.setTextColor(context.getResources().getColor(R.color.white));
            }
        }else {
            holder.fourth.setVisibility(View.GONE);
        }

        holder.first.setOnClickListener(tvClickListner);
        holder.second.setOnClickListener(tvClickListner);
        holder.third.setOnClickListener(tvClickListner);
        holder.fourth.setOnClickListener(tvClickListner);
        holder.first.setOnLongClickListener(tvLongClickListner);
        holder.second.setOnLongClickListener(tvLongClickListner);
        holder.third.setOnLongClickListener(tvLongClickListner);
        holder.fourth.setOnLongClickListener(tvLongClickListner);

    }



    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public final View mView;
        public final TextView first;
        public final TextView second;
        public final TextView third;
        public final TextView fourth;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            first = itemView.findViewById(R.id.tvFirst);
            second = itemView.findViewById(R.id.tvSecond);
            third = itemView.findViewById(R.id.tvThird);
            fourth = itemView.findViewById(R.id.tvFourth);
        }
    }
}
