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

import RoomDb.Category;

public class SelectedCategoryListAdaptor extends RecyclerView.Adapter<SelectedCategoryListAdaptor.ViewHolder> {

    private Context context;
    private final List<Category> categories;

    public SelectedCategoryListAdaptor(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_selected_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(categories.size()==0){
            holder.tvCategoryName.setText("All Categories");
            holder.tvCategoryType.setVisibility(View.GONE);
            return;
        }
        holder.tvCategoryType.setVisibility(View.VISIBLE);
        holder.tvCategoryName.setText(categories.get(position).getName());
        if(categories.get(position).getIsIncome()){
            holder.tvCategoryType.setText("Income");
        }else{
            holder.tvCategoryType.setText("Expense");
        }
    }

    @Override
    public int getItemCount() {
        if(categories.size()==0){
            return 1;
        }
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCategoryName;
        public TextView tvCategoryType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvCategoryType = itemView.findViewById(R.id.tvCategoryType);
        }
    }
}
