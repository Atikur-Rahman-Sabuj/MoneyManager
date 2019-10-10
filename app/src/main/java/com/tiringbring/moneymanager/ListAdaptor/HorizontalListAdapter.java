package com.tiringbring.moneymanager.ListAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCategoryName.setText(transactions.get(position).getCategory().getName());
        holder.tvName.setText(transactions.get(position).getName());
        holder.tvValue.setText(String.valueOf(String.format("%.2f", transactions.get(position).getAmount())));
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


        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvCategoryName = (TextView) view.findViewById(R.id.tvCategoyName);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvValue = (TextView) view.findViewById(R.id.tvValue);

        }

    }
}
