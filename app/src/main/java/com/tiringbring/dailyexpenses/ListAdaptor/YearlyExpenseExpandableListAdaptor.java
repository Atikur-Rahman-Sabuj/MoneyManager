package com.tiringbring.dailyexpenses.ListAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiringbring.dailyexpenses.DataController.DateDataController;
import com.tiringbring.dailyexpenses.DataController.MySharedPreferences;
import com.tiringbring.dailyexpenses.Entity.MonthExpenses;
import com.tiringbring.dailyexpenses.Entity.YearlyExpenses;
import com.tiringbring.dailyexpenses.R;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.List;

public class YearlyExpenseExpandableListAdaptor extends BaseExpandableListAdapter {
    Context context;
    Long dailyLimit;
    float scale;
    List<YearlyExpenses> YearlyExpenseList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public  YearlyExpenseExpandableListAdaptor(Context context, List<YearlyExpenses> YearlyExpenseList){
        this.context = context;
        this.YearlyExpenseList = YearlyExpenseList;
        dailyLimit = new MySharedPreferences(context).getDayilyLimit();
        scale = context.getResources().getDisplayMetrics().density;
    }


    @Override
    public int getGroupCount() {
        return YearlyExpenseList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return YearlyExpenseList.get(groupPosition).monthlyExpenses.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return YearlyExpenseList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return YearlyExpenseList.get(groupPosition).monthlyExpenses.get(childPosition);
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
        LinearLayout loIndicatorBar = (LinearLayout) convertView.findViewById(R.id.loIndicatorBar);
        loIndicatorBar.setActivated(false);
//        if(DayExpenseList.get(groupPosition).getTotal()>dailyLimit){
//            loIndicatorBar.setBackgroundColor(ContextCompat.getColor(context, R.color.light_red));
//            loIndicatorBar.getLayoutParams().height = (int) (60 * scale + 0.5f);
//        }
//        else {
//            int height = (int) (((DayExpenseList.get(groupPosition).getTotal()/dailyLimit))*60);
//            loIndicatorBar.getLayoutParams().height = (int) (height * scale + 0.5f);
//            loIndicatorBar.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green));
//
//        }
        tvDate.setText(YearlyExpenseList.get(groupPosition).year);
        tvTotal.setText(String.format("%.2f", YearlyExpenseList.get(groupPosition).total));
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
        mIdView.setText(new DateDataController().DateToMonth(YearlyExpenseList.get(groupPosition).monthlyExpenses.get(childPosition).date));
        mContentView.setText(String.format("%.2f", YearlyExpenseList.get(groupPosition).monthlyExpenses.get(childPosition).total));
        return  convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
