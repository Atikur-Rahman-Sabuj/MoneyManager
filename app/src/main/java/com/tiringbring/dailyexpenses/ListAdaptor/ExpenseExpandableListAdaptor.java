package com.tiringbring.dailyexpenses.ListAdaptor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tiringbring.dailyexpenses.AddExpense;
import com.tiringbring.dailyexpenses.Entity.DayExpenses;
import com.tiringbring.dailyexpenses.MainActivity;
import com.tiringbring.dailyexpenses.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseExpandableListAdaptor extends BaseExpandableListAdapter {
    Context context;
    List<DayExpenses> DayExpenseList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public  ExpenseExpandableListAdaptor(Context context, List<DayExpenses> DayExpenseList){
        this.context = context;
        this.DayExpenseList = DayExpenseList;
    }


    @Override
    public int getGroupCount() {
        return DayExpenseList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return DayExpenseList.get(groupPosition).getDayExpenseList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return DayExpenseList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_date, null);
        }
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
        tvDate.setText(dateFormat.format(DayExpenseList.get(groupPosition).getDate()));
        tvTotal.setText(String.format("%.2f", DayExpenseList.get(groupPosition).getTotal()));
        return  convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_espense, null);
        }
        TextView mIdView = (TextView) convertView.findViewById(R.id.item_number);
        TextView mContentView = (TextView) convertView.findViewById(R.id.content);
        mIdView.setText(DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition).getName());
        mContentView.setText(String.format("%.2f", DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition).getAmount()));
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = { "Edit", "Delete" };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition).getName()+" "+String.valueOf(String.format("%.2f", DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition).getAmount())));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:{
                                Intent intent = new Intent(context, AddExpense.class);
                                intent.putExtra("expenseId", DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition).getId());
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
                                                MainActivity.myAppRoomDatabase.expenseDao().DeleteExpense(DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition));
                                                //DayExpenseList.get(groupPosition).getDayExpenseList().remove(childPosition);
                                                if(DayExpenseList.get(groupPosition).getDayExpenseList().size()==1){
                                                    DayExpenseList.remove(groupPosition);
                                                }else {
                                                    DayExpenseList.get(groupPosition).setTotal(DayExpenseList.get(groupPosition).getTotal()-DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition).getAmount());
                                                    DayExpenseList.get(groupPosition).getDayExpenseList().remove(childPosition);
                                                }
                                                notifyDataSetChanged();
                                                Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                                break;
                            }
                        }

                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
                return false;
            }
        });
        return  convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
