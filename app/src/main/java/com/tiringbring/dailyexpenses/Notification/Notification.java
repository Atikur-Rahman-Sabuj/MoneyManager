package com.tiringbring.dailyexpenses.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.tiringbring.dailyexpenses.Activity.AddExpenseActivity;
import com.tiringbring.dailyexpenses.DataController.DateDataController;
import com.tiringbring.dailyexpenses.DataController.ExpenseDataController;
import com.tiringbring.dailyexpenses.DataController.MySharedPreferences;
import com.tiringbring.dailyexpenses.Entity.DayExpenses;
import com.tiringbring.dailyexpenses.Entity.MonthExpenses;
import com.tiringbring.dailyexpenses.Activity.ExpenseListActivity;
import com.tiringbring.dailyexpenses.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import RoomDb.Expense;
import RoomDb.ExpenseDatabase;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

public class Notification extends BroadcastReceiver {
    ExpenseDatabase myAppRoomDatabase = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(new MySharedPreferences(context).getIsNotificationEnabled()){
            Calendar calendar = Calendar.getInstance();
            Date date = new DateDataController().CropTimeFromDate(calendar);

            myAppRoomDatabase = Room.databaseBuilder(context,ExpenseDatabase.class, "Expensedb").allowMainThreadQueries().build();
            boolean isExpenseExist = (myAppRoomDatabase.expenseDao().GetExpensesOfaDate(date)).size()>0;
            if(!isExpenseExist){
                CreateDailyNotification(context, "Alert!!!", "You might have forgot to add todays expense!", "Reminder from Expenses");
            }
            if(calendar.get(Calendar.DAY_OF_MONTH ) == 1){
                CreateMonthlyNotification(context);
            }

        }

    }

    private void CreateMonthlyNotification(Context context) {
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(context, ExpenseListActivity.class),0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(context, notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        List<Expense> expenses = myAppRoomDatabase.expenseDao().GetExpenses();
        List<DayExpenses> dayExpensesList = new ExpenseDataController(expenses).getDailyExpenses();
        List<MonthExpenses> monthlyExpenseList = new ExpenseDataController(expenses).GetMonthlyExpenses(dayExpensesList);
        Calendar calendar = Calendar.getInstance();
        Double total = 0.0;
        String monthName = "";
        DateDataController ddc= new DateDataController();
        for (MonthExpenses mex:monthlyExpenseList) {
            if(String.valueOf(calendar.get(Calendar.YEAR)).equals( ddc.DateToYear(mex.date))&&(calendar.get(Calendar.MONTH)-1)==ddc.DateToMonthNo(mex.date))  {
                total = mex.total;
                monthName = mex.month;
            }
        }
        Double percentage = getPercentage(context, total);

        builder = builder
                .setContentTitle(monthName + " Expenses")
                .setContentText("Total Expense "+total+", "+percentage+"% than limit.")
                .setTicker("Monthly expense report");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.smalliconlowapi);
            builder.setColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            builder.setSmallIcon(R.drawable.smalliconlowapi);
        }
        builder.setContentIntent(notificationIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        notificationManager.notify(2, builder.build());
    }

    private void CreateDailyNotification(Context context, String header, String message, String alert) {
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(context, AddExpenseActivity.class),0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(context, notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder = builder
                .setContentTitle(header)
                .setContentText(message)
                .setTicker(alert);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.smalliconlowapi);
            builder.setColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            builder.setSmallIcon(R.drawable.smalliconlowapi);
        }
        builder.setContentIntent(notificationIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        notificationManager.notify(1, builder.build());

    }
    private double getPercentage(Context context, double total){
        long monthlyLimit = new MySharedPreferences(context).getMonthlyLimit();
        return ((total/monthlyLimit)*100);
    }
}
