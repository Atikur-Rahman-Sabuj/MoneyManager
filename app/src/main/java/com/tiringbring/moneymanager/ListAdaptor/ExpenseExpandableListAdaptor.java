package com.tiringbring.moneymanager.ListAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.DateDataController;
import com.tiringbring.moneymanager.DataController.MySharedPreferences;
import com.tiringbring.moneymanager.Entity.DayExpenses;
import com.tiringbring.moneymanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import RoomDb.Category;
import RoomDb.Transaction;

public class ExpenseExpandableListAdaptor extends BaseExpandableListAdapter {
    Context context;
    Long dailyLimit;
    float scale;
    List<DayExpenses> DayExpenseList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    List<Category> categories = new ArrayList<>();


    public  ExpenseExpandableListAdaptor(Context context, List<DayExpenses> DayExpenseList){
        this.context = context;
        this.DayExpenseList = DayExpenseList;
        dailyLimit = new MySharedPreferences(context).getDayilyLimit();
        scale = context.getResources().getDisplayMetrics().density;
        categories = StartActivity.getDBInstance(context).mmDao().GetCategories();
        StartActivity.destroyDBInstance();
    }


    @Override
    public int getGroupCount() {
        return DayExpenseList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return DayExpenseList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return DayExpenseList.get(groupPosition).getDayTransactionList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return DayExpenseList.get(groupPosition).getDayTransactionList().get(childPosition).getId();
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


        ChildHolder childHolder = null;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.horizontal_list_parent, null);
        }
            childHolder = new ChildHolder();
            childHolder.recyclerView = (RecyclerView) convertView.findViewById(R.id.horizontalListView);
            convertView.setTag(childHolder);

            List<Transaction> transactions = DayExpenseList.get(groupPosition).dayTransactionList;
            for(int i=0 ; i<transactions.size(); i++){
                Transaction transaction = transactions.get(i);
                Category cat = null;
                for (int j = 0; j<categories.size(); j++) {
                    if (transaction.getCategoryId() == categories.get(j).getId()) {
                        transaction.setCategory(categories.get(j));
                        break;
                    }
                }
            }

//            List<String> strings = new ArrayList<>();
//            strings.add("one");
//            strings.add("two");
//            strings.add("three");
//            strings.add("one");
//            strings.add("two");
//            strings.add("three");
//            strings.add("one");
//            strings.add("two");
//            strings.add("three");
            HorizontalListAdapter horizontalListAdapter = new HorizontalListAdapter(transactions, context);
            childHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            childHolder.recyclerView.setAdapter(horizontalListAdapter);
//        }
//        else
//        {
//            childHolder = (ChildHolder) convertView.getTag();
//        }
//        List<String> strings = new ArrayList<>();
//        strings.add("one");
//        strings.add("two");
//        strings.add("three");
//        HorizontalListAdapter horizontalListAdapter = new HorizontalListAdapter(strings, context);
//        childHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//        childHolder.recyclerView.setAdapter(horizontalListAdapter);

        return convertView;






//        //region previous original
//        final int gp = groupPosition;
//        final int cp = childPosition;
//        if(convertView == null)
//        {
//            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.fragment_details_expense, null);
//        }
//        TextView mIdView = (TextView) convertView.findViewById(R.id.tvName);
//        TextView mContentView = (TextView) convertView.findViewById(R.id.tvAmount);
//        mIdView.setText(DayExpenseList.get(groupPosition).getDayTransactionList().get(childPosition).getName());
//        mContentView.setText("Total "+String.format("%.2f", DayExpenseList.get(groupPosition).getDayTransactionList().get(childPosition).getAmount()));
//        convertView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                final CharSequence[] items = { "Edit", "Delete" };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle(DayExpenseList.get(gp).getDayTransactionList().get(cp).getName()+"    "+String.valueOf(String.format("%.2f", DayExpenseList.get(gp).getDayTransactionList().get(cp).getAmount())));
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case 0:{
//                                Intent intent = new Intent(context, AddExpenseActivity.class);
//                                intent.putExtra("expenseId", DayExpenseList.get(gp).getDayTransactionList().get(cp).getId());
//                                ((Activity)context).startActivity(intent);
//                                break;
//                            }
//                            case 1:{
//                                new AlertDialog.Builder(context)
//                                        .setTitle("Confirm")
//                                        .setMessage("Data will be permanently deleted. Do you really want to remove?")
//                                        .setIcon(android.R.drawable.ic_dialog_alert)
//                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int whichButton) {
//                                                StartActivity.getDBInstance(context).mmDao().DeleteTransaction(DayExpenseList.get(gp).getDayTransactionList().get(cp));
//                                                StartActivity.destroyDBInstance();
//                                                //DayExpenseList.get(groupPosition).getDayTransactionList().remove(childPosition);
//                                                if(DayExpenseList.get(gp).getDayTransactionList().size()==1){
//                                                    DayExpenseList.remove(gp);
//                                                }else {
//                                                    DayExpenseList.get(gp).setTotal(DayExpenseList.get(gp).getTotal()-DayExpenseList.get(gp).getDayTransactionList().get(cp).getAmount());
//                                                    DayExpenseList.get(gp).getDayTransactionList().remove(cp);
//                                                }
//                                                notifyDataSetChanged();
//                                                //Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
//                                            }})
//                                        .setNegativeButton(android.R.string.no, null).show();
//                                break;
//                            }
//                        }
//
//                    }
//                });
//
//                AlertDialog alert = builder.create();
//
//                alert.show();
//                return false;
//            }
//        });
//        return  convertView;
//        //endregion
    }

    private static class ChildHolder
    {
        RecyclerView recyclerView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
