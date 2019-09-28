package com.tiringbring.moneymanager.ListAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.tiringbring.moneymanager.DataController.DateDataController;
import com.tiringbring.moneymanager.DataController.MySharedPreferences;
import com.tiringbring.moneymanager.Entity.MonthExpenses;
import com.tiringbring.moneymanager.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MonthlyExpenseExpandableListAdaptor extends BaseExpandableListAdapter {
    Context context;
    Long monthlyLimit;
    float scale;
    List<MonthExpenses> MonthlyExpenseList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public  MonthlyExpenseExpandableListAdaptor(Context context, List<MonthExpenses> MonthlyExpenseList){
        this.context = context;
        this.MonthlyExpenseList = MonthlyExpenseList;
        monthlyLimit = new MySharedPreferences(context).getMonthlyLimit();
        scale = context.getResources().getDisplayMetrics().density;
    }


    @Override
    public int getGroupCount() {
        return MonthlyExpenseList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return MonthlyExpenseList.get(groupPosition).dayExpenses.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return MonthlyExpenseList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return MonthlyExpenseList.get(groupPosition).dayExpenses.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
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

        double percentage = (((MonthlyExpenseList.get(groupPosition).total/monthlyLimit))*100);
        tvDate.setText(MonthlyExpenseList.get(groupPosition).month);
        tvTotal.setText("Total "+String.format("%.2f", MonthlyExpenseList.get(groupPosition).total));
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
        mIdView.setText(new DateDataController().DateToDate(MonthlyExpenseList.get(groupPosition).dayExpenses.get(childPosition).date));
        mContentView.setText("Total "+String.format("%.2f", MonthlyExpenseList.get(groupPosition).dayExpenses.get(childPosition).total));
        return  convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
