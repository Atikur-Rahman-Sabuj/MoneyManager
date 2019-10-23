package com.tiringbring.moneymanager.ListAdaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiringbring.moneymanager.R;

public class TestChartAdaptor extends RecyclerView.Adapter<TestChartAdaptor.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chart_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(275, LinearLayout.LayoutParams.MATCH_PARENT));
        //holder.incomeBack.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
       // holder.expenseBack.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        //LinearLayout.LayoutParams lpIncome = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 400,1);
        //lpIncome.setMargins(20,0,20,0);
        //holder.income.setLayoutParams(lpIncome);
        //LinearLayout.LayoutParams lpExpense = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300,1);
        //lpExpense.setMargins(20,0,20,0);
       // holder.expense.setLayoutParams(lpExpense);
         //LinearLayout.LayoutParams lpExpense = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //lpExpense.setMargins(20,0,20,0);
        //holder.allContainer.setLayoutParams(lpExpense);
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout income;
        LinearLayout expense;
        LinearLayout incomeBack;
        LinearLayout expenseBack;
        LinearLayout allContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            income = itemView.findViewById(R.id.income);
            incomeBack = itemView.findViewById(R.id.incomeBack);
            expense = itemView.findViewById(R.id.expense);
            expenseBack = itemView.findViewById(R.id.expenseBack);
            allContainer = itemView.findViewById(R.id.allContainer);
        }
    }
}
