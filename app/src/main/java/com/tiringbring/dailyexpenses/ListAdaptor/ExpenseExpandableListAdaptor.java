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

import com.tiringbring.dailyexpenses.Activitie.AddExpenseActivity;
import com.tiringbring.dailyexpenses.DataController.DateDataController;
import com.tiringbring.dailyexpenses.DataController.MySharedPreferences;
import com.tiringbring.dailyexpenses.Entity.DayExpenses;
import com.tiringbring.dailyexpenses.R;
import com.tiringbring.dailyexpenses.Activitie.StartActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseExpandableListAdaptor extends BaseExpandableListAdapter {
    Context context;
    Long dailyLimit;
    float scale;
    List<DayExpenses> DayExpenseList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public  ExpenseExpandableListAdaptor(Context context, List<DayExpenses> DayExpenseList){
        this.context = context;
        this.DayExpenseList = DayExpenseList;
        dailyLimit = new MySharedPreferences(context).getDayilyLimit();
        scale = context.getResources().getDisplayMetrics().density;
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
        TextView tvLimit = (TextView) convertView.findViewById(R.id.tvLimit);
        double percentage = (((DayExpenseList.get(groupPosition).getTotal()/dailyLimit))*100);
        tvDate.setText(new DateDataController().DatetoBigDateMonthYear(DayExpenseList.get(groupPosition).getDate()));
        tvTotal.setText("Total "+String.format("%.2f", DayExpenseList.get(groupPosition).getTotal()));
        tvLimit.setText(String.format("%.2f", percentage)+"% than limit!");
        return  convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final int gp = groupPosition;
        final int cp = childPosition;
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_details_expense, null);
        }
        TextView mIdView = (TextView) convertView.findViewById(R.id.tvName);
        TextView mContentView = (TextView) convertView.findViewById(R.id.tvAmount);
        mIdView.setText(DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition).getName());
        mContentView.setText("Total "+String.format("%.2f", DayExpenseList.get(groupPosition).getDayExpenseList().get(childPosition).getAmount()));
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = { "Edit", "Delete" };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(DayExpenseList.get(gp).getDayExpenseList().get(cp).getName()+"    "+String.valueOf(String.format("%.2f", DayExpenseList.get(gp).getDayExpenseList().get(cp).getAmount())));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:{
                                Intent intent = new Intent(context, AddExpenseActivity.class);
                                intent.putExtra("expenseId", DayExpenseList.get(gp).getDayExpenseList().get(cp).getId());
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
                                                StartActivity.getDBInstance(context).expenseDao().DeleteExpense(DayExpenseList.get(gp).getDayExpenseList().get(cp));
                                                StartActivity.destroyDBInstance();
                                                //DayExpenseList.get(groupPosition).getDayExpenseList().remove(childPosition);
                                                if(DayExpenseList.get(gp).getDayExpenseList().size()==1){
                                                    DayExpenseList.remove(gp);
                                                }else {
                                                    DayExpenseList.get(gp).setTotal(DayExpenseList.get(gp).getTotal()-DayExpenseList.get(gp).getDayExpenseList().get(cp).getAmount());
                                                    DayExpenseList.get(gp).getDayExpenseList().remove(cp);
                                                }
                                                notifyDataSetChanged();
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
