package com.tiringbring.moneymanager.ListAdaptor;

        import android.content.Context;
        import android.util.TypedValue;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

        import com.tiringbring.moneymanager.DataController.DateDataController;
        import com.tiringbring.moneymanager.Entity.DayTransactions;
        import com.tiringbring.moneymanager.Entity.MonthTransactions;
        import com.tiringbring.moneymanager.R;

        import java.util.List;

public class ChartDailyAdaptor extends RecyclerView.Adapter<ChartDailyAdaptor.ViewHolder> {

    Context context;
    List<DayTransactions> dayTransactionList;
    Double expenseMaximum;

    public ChartDailyAdaptor(Context context, List<DayTransactions> dayTransactionList, Double expenseMaximum) {
        this.context = context;
        this.dayTransactionList = dayTransactionList;
        this.expenseMaximum = expenseMaximum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bar_chart_daily_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvExpenseAmount.setText(dayTransactionList.get(position).expenseTotal.toString());
        holder.tvDate.setText(new DateDataController().DatetoDateMonthYear(dayTransactionList.get(position).date));
        float expenseHeight = (float)((190*dayTransactionList.get(position).expenseTotal)/expenseMaximum);
        LinearLayout.LayoutParams expenseLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPXFromDP(expenseHeight));
        holder.expense.setLayoutParams(expenseLP);

    }
    private int getPXFromDP(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getItemCount() {
        return dayTransactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView expense;
        TextView tvExpenseAmount, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expense = itemView.findViewById(R.id.expense);
            tvExpenseAmount = itemView.findViewById(R.id.tvExpenseAmount);
            tvDate = itemView.findViewById(R.id.tvDate);

        }
    }
}

