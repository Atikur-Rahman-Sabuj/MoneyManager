package com.tiringbring.moneymanager.ListAdaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tiringbring.moneymanager.Activity.AddTransactionActivity;
import com.tiringbring.moneymanager.R;

import java.util.List;

import RoomDb.Transaction;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {

    private List<Transaction> transactions;
    private Context context;

    public HorizontalListAdapter(List<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_list_item, parent, false);
        return new HorizontalListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvCategoryName.setText(transactions.get(position).getCategory().getName());
        if(transactions.get(position).getName().equals("") || transactions.get(position).getName().equals(null)){
            holder.tvName.setText(transactions.get(position).getCategory().getName());
        }else {
            holder.tvName.setText(transactions.get(position).getName());
        }
        holder.tvValue.setText(String.valueOf(String.format("%.2f", transactions.get(position).getAmount())));
        if(transactions.get(position).getIsIncome()){
            //holder.llBorderColer.setBackground(ContextCompat.getDrawable(context, R.drawable.card_back_with_border_green));
            holder.tvType.setText(context.getResources().getString(R.string.income));

        }else{
            //holder.llBorderColer.setBackground(ContextCompat.getDrawable(context, R.drawable.card_back_with_border_red));
            holder.tvType.setText(context.getResources().getString(R.string.expense));
        }
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, AddTransactionActivity.class);
                intent.putExtra("transactionId", transactions.get(position).getId());
                context.startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvCategoryName;
        public final TextView tvName;
        public final TextView tvValue;
        public final TextView tvType;
        public final LinearLayout llBorderColer;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvCategoryName = (TextView) view.findViewById(R.id.tvCategoyName);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvValue = (TextView) view.findViewById(R.id.tvValue);
            tvType = (TextView) view.findViewById(R.id.tvType);
            llBorderColer = (LinearLayout) view.findViewById(R.id.llBorderColor);

        }

    }
}
