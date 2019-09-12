package com.tiringbring.dailyexpenses.ListAdaptor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiringbring.dailyexpenses.Activity.AddExpenseActivity;
import com.tiringbring.dailyexpenses.R;
import com.tiringbring.dailyexpenses.Fragment.ExpenseFragment.OnListFragmentInteractionListener;
import com.tiringbring.dailyexpenses.Activity.StartActivity;
import com.tiringbring.dailyexpenses.dummy.DummyContent.DummyItem;

import java.util.List;

import RoomDb.Expense;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExpensesRecyclerViewAdapter extends RecyclerView.Adapter<ExpensesRecyclerViewAdapter.ViewHolder> {

    private final List<Expense> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;
    AddExpenseActivity addExpense;
    public ExpensesRecyclerViewAdapter(Context context, List<Expense> items, OnListFragmentInteractionListener listener, AddExpenseActivity addExpense) {
        mValues = items;
        mListener = listener;
        this.context = context;
        this.addExpense = addExpense;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_espense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int pos = position;
        holder.mItem = mValues.get(position);
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fadein);
//        if(position == mValues.size()-1){
//            holder.mIdView.startAnimation(animation);
//            holder.mContentView.startAnimation(animation);
//        }

        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(String.format("%.2f",mValues.get(position).getAmount()));
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = { "Edit", "Delete" };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(mValues.get(pos).getName()+"    "+String.valueOf(mValues.get(pos).getAmount()));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:{
                                Intent intent = new Intent(context, AddExpenseActivity.class);
                                intent.putExtra("expenseId", mValues.get(pos).getId());
                                ((Activity)context).startActivity(intent);
                                break;
                            }
                            case 1:{
                                new AlertDialog.Builder(context)
                                        .setTitle("Confirm")
                                        .setMessage("Data will be permanently deleted. Do you really want to remove?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                StartActivity.getDBInstance(context).expenseDao().DeleteExpense(mValues.get(pos));
                                                StartActivity.destroyDBInstance();
                                                mValues.remove(pos);
                                                notifyDataSetChanged();
                                                addExpense.ChangeTotal();
                                                //Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                                break;
                            }
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
                //do your stuff here

                return false;
            }
        });



    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Expense mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
