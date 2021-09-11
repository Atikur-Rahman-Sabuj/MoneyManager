package com.tiringbring.moneymanager.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.tiringbring.moneymanager.Activity.AddTransactionActivity;
import com.tiringbring.moneymanager.Activity.BottomNavigationActivity;
import com.tiringbring.moneymanager.DataController.DateDataController;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.DataController.MySharedPreferences;
import com.tiringbring.moneymanager.Entity.DayTransactions;
import com.tiringbring.moneymanager.Entity.MonthTransactions;
import com.tiringbring.moneymanager.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import RoomDb.Transaction;
import RoomDb.MMDatabase;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

public class Notification extends BroadcastReceiver {
    MMDatabase myAppRoomDatabase = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(new MySharedPreferences(context).getIsNotificationEnabled()){
            Calendar calendar = Calendar.getInstance();
            Date date = new DateDataController().CropTimeFromDate(calendar);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
            Date endDate = new DateDataController().CropTimeFromDate(calendar);

            myAppRoomDatabase = Room.databaseBuilder(context, MMDatabase.class, "MMdb").allowMainThreadQueries().build();

            boolean isExpenseExist = (myAppRoomDatabase.mmDao().GetAllransactionsBetweenTime(date, endDate)).size()>0;
            myAppRoomDatabase = null;
            if(!isExpenseExist){
                CreateDailyNotification(context, "Alert!!!", "You might have forgot to add todays expense!", "Reminder from Money Manager");
            }
              if(calendar.get(Calendar.DAY_OF_MONTH ) == 1){
                CreateMonthlyNotification(context);
            }


        }

    }

    private void CreateMonthlyNotification(Context context) {
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(context, BottomNavigationActivity.class),0);
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
        List<Transaction> expens = myAppRoomDatabase.mmDao().GetTransaction();
        List<DayTransactions> dayTransactionsList = new ExpenseDataController(expens).getDailyExpenses();
        List<MonthTransactions> monthlyExpenseList = new ExpenseDataController(expens).GetMonthlyExpenses(dayTransactionsList);
        Calendar calendar = Calendar.getInstance();
        Double total = 0.0;
        String monthName = "";
        DateDataController ddc= new DateDataController();
        for (MonthTransactions mex:monthlyExpenseList) {
            if(String.valueOf(calendar.get(Calendar.YEAR)).equals( ddc.DateToYear(mex.date))&&(calendar.get(Calendar.MONTH)-1)==ddc.DateToMonthNo(mex.date))  {
                total = mex.expenseTotal;
                monthName = mex.month;
            }
        }
        Double percentage = getPercentage(context, total);

        builder = builder
                .setContentTitle(monthName + " Expenses")
                .setContentText("Total Transaction "+total+", "+percentage+"% than limit.")
                .setTicker("Monthly expense report");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.ic_notification_icon);
            builder.setColor(context.getResources().getColor(R.color.myColorPrimary));
        } else {
            builder.setSmallIcon(R.drawable.ic_notification_icon);
        }
        builder.setContentIntent(notificationIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        notificationManager.notify(2, builder.build());
    }

    private void CreateDailyNotification(Context context, String header, String message, String alert) {
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(context, AddTransactionActivity.class),0);
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
            builder.setSmallIcon(R.drawable.ic_notification_icon);
            builder.setColor(context.getResources().getColor(R.color.myColorPrimary));
        } else {
            builder.setSmallIcon(R.drawable.ic_notification_icon);
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
