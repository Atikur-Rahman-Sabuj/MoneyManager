package com.tiringbring.moneymanager.ListAdaptor;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiringbring.moneymanager.DataController.DateDataController;
import com.tiringbring.moneymanager.Entity.MonthTransactions;
import com.tiringbring.moneymanager.R;

import java.util.List;

public class ChartMonthlyAdaptor extends RecyclerView.Adapter<ChartMonthlyAdaptor.ViewHolder> {

    Context context;
    List<MonthTransactions> monthTransactionsList;
    Double incomeMaximum, expenseMaximum;

    public ChartMonthlyAdaptor(Context context, List<MonthTransactions> monthTransactionsList, Double incomeMaximum, Double expenseMaximum) {
        this.context = context;
        this.monthTransactionsList = monthTransactionsList;
        this.incomeMaximum = incomeMaximum;
        this.expenseMaximum = expenseMaximum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bar_chart_monthly_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvIncomeAmount.setText(monthTransactionsList.get(position).incomeTotal.toString());
        holder.tvExpenseAmount.setText(monthTransactionsList.get(position).expenseTotal.toString());
        holder.tvDate.setText(new DateDataController().DateToShortMonthYear(monthTransactionsList.get(position).date));
        float incomeHeight = (float)((110*monthTransactionsList.get(position).incomeTotal)/incomeMaximum);
        float expenseHeight = (float)((110*monthTransactionsList.get(position).expenseTotal)/expenseMaximum);
        LinearLayout.LayoutParams incomeLP = new LinearLayout.LayoutParams(getPXFromDP(40), getPXFromDP(incomeHeight));
        incomeLP.setMargins(getPXFromDP(10),0, getPXFromDP(10),0);
        holder.income.setLayoutParams(incomeLP);
        LinearLayout.LayoutParams expenseLP = new LinearLayout.LayoutParams(getPXFromDP(40), getPXFromDP(expenseHeight));
        expenseLP.setMargins(getPXFromDP(10),0, getPXFromDP(10),0);
        holder.expense.setLayoutParams(expenseLP);
        if(((110*monthTransactionsList.get(position).incomeTotal)/incomeMaximum)<10.0){
            holder.tvDate.setText("");
        }

    }
    private int getPXFromDP(float dp){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getItemCount() {
        return monthTransactionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout income;
        LinearLayout expense;
        TextView tvIncomeAmount, tvExpenseAmount, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            income = itemView.findViewById(R.id.income);
            expense = itemView.findViewById(R.id.expense);
            tvIncomeAmount = itemView.findViewById(R.id.tvIncomeAmount);
            tvExpenseAmount = itemView.findViewById(R.id.tvExpenseAmount);
            tvDate = itemView.findViewById(R.id.tvDate);

        }
    }
}
