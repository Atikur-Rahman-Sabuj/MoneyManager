package com.tiringbring.moneymanager.Activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import RoomDb.MMDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tiringbring.moneymanager.DataController.BarEntryDataController;
import com.tiringbring.moneymanager.DataController.MySharedPreferences;
import com.tiringbring.moneymanager.DataController.PieEntryDataController;
import com.tiringbring.moneymanager.Entity.MonthTransactions;
import com.tiringbring.moneymanager.ListAdaptor.ChartMonthlyAdaptor;
import com.tiringbring.moneymanager.Notification.Notification;
import com.tiringbring.moneymanager.R;
import com.tiringbring.moneymanager.Utility.ResourceManager;
import com.tiringbring.moneymanager.chart.MonthlyChartData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private static MMDatabase INSTANCE;
    private BarChart barChartMonthlyIncome, barChartMonthlyExpense;
    private PieChart pieChart;
    private TextView textView;
    Long dailyLimit;
    private Date pieDate;
    private Button btnAddNew;
    private Button btnShowList, btnBottomNavigation;
    private RecyclerView rvTestList;
    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set language
        if(new MySharedPreferences(this).getLanguage().equals("bn")){
            ResourceManager.changeLanguage(this,"bn");
        }else{
            ResourceManager.changeLanguage(this, "en");
        }

        setContentView(R.layout.activity_start);
        //myAppRoomDatabase = Room.databaseBuilder(getApplicationContext(),MMDatabase.class, "Expensedb").allowMainThreadQueries().build();
        onFirstRun();
        btnAddNew = (Button) findViewById(R.id.btnAddNew);
        btnShowList = (Button) findViewById(R.id.btnShowList);
        barChartMonthlyIncome = (BarChart) findViewById(R.id.monthlyIncomeBarChart);
        barChartMonthlyExpense = (BarChart) findViewById(R.id.monthlyExpenseBarChart);
        rvTestList = (RecyclerView) findViewById(R.id.rvTestList);
        rvTestList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MonthlyChartData cmd = new MonthlyChartData();
        List<MonthTransactions> monthTransactionsList = cmd.GetMonthlyExpenseList(getApplicationContext());
        ChartMonthlyAdaptor chartMonthlyAdaptor = new ChartMonthlyAdaptor(getApplicationContext(), monthTransactionsList, cmd.GetMaximumMonthlyIncome(monthTransactionsList), cmd.GetMaximumMonthlyExpense(monthTransactionsList));
        rvTestList.setAdapter(chartMonthlyAdaptor);





        dailyLimit = new MySharedPreferences(getApplicationContext()).getDayilyLimit();

        //new PlayAnimation().PlayFadeIn(getApplicationContext(), btnAddNew);
        //Notification set
//        Calendar nCalendar = Calendar.getInstance();
//        nCalendar.set(Calendar.HOUR, 21);
//        nCalendar.set(Calendar.MINUTE, 45);
//        nCalendar.set(Calendar.SECOND, 0);
//        setAlart(nCalendar.getTimeInMillis());



        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);


                startActivity(intent);


            }
        });
        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);

                startActivity(intent);

            }
        });
    }

    private void BindDataToMonthlyIncomeBarChart() {
        barChartMonthlyIncome.getDescription().setEnabled(false);
        final BarEntryDataController beDataController = new BarEntryDataController();
//        List<BarEntry> data = beDataController.GetBarEntries(getApplicationContext());
//        //generating colors
//        List<Integer> colors = new ArrayList<>();
//        for (BarEntry be:
//                data) {
//            if(be.getY()>dailyLimit){
//                colors.add(ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.dark_red, null));
//            }
//            else {
//                colors.add(ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.myColorPrimary, null));
//            }
//        }
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 20));
        barEntries.add(new BarEntry(1, 30));
        barEntries.add(new BarEntry(2, 40));
        barEntries.add(new BarEntry(3, 38));
        barEntries.add(new BarEntry(4, 25));
        barEntries.add(new BarEntry(5, 35));
        barEntries.add(new BarEntry(6, 20));
        barEntries.add(new BarEntry(7, 38));
        barEntries.add(new BarEntry(8, 20));
        barEntries.add(new BarEntry(9, 30));
        barEntries.add(new BarEntry(10, 40));
        barEntries.add(new BarEntry(11, 25));
        barEntries.add(new BarEntry(12, 35));
        barEntries.add(new BarEntry(13, 20));

        BarDataSet set = new BarDataSet(barEntries, "");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        //set.setColors(colors);
        set.setDrawValues(true);
        BarData barData = new BarData(set);
        barData.setBarWidth(.8f);
        barChartMonthlyIncome.setData(barData);
        barChartMonthlyExpense.setPadding(0,0,0,0);
        barChartMonthlyIncome.setExtraOffsets(0, 0, 0, 0);


        barChartMonthlyIncome.getContentRect().set(0, 0, barChartMonthlyIncome.getWidth(), barChartMonthlyIncome.getHeight());
        barChartMonthlyIncome.animateY(500);
        barChartMonthlyIncome.setScaleEnabled(false);
        barChartMonthlyIncome.setDrawValueAboveBar(true);
        barChartMonthlyIncome.setDrawBorders(false);
        barChartMonthlyIncome.setExtraOffsets(0,0,0,0);
        barChartMonthlyIncome.getLegend().setEnabled(false);
        barChartMonthlyIncome.setVisibleXRangeMaximum(7); // allow 20 values to be displayed at once on the x-axis, not more
        barChartMonthlyIncome.moveViewToX(-1);
        XAxis xAxis = barChartMonthlyIncome.getXAxis();
        YAxis left = barChartMonthlyIncome.getAxisLeft();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(beDataController.getXAxisValues()));
        left.setAxisMaximum(30);
        left.setDrawLabels(true); // no axis labels
        left.setDrawAxisLine(true); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(false); // draw a zero line
        left.setInverted(false);

        barChartMonthlyIncome.getAxisRight().setEnabled(false); // no right axis
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.BLACK);
        //xAxis.setCenterAxisLabels(true);
        barChartMonthlyIncome.setDrawGridBackground(false);
        barChartMonthlyIncome.setFitBars(false);
        barChartMonthlyIncome.invalidate();
    }

    private void BindDataToMonthlyExpenseBarChart() {
        barChartMonthlyExpense.getDescription().setEnabled(false);
        final BarEntryDataController beDataController = new BarEntryDataController();
//        List<BarEntry> data = beDataController.GetBarEntries(getApplicationContext());
//        //generating colors
//        List<Integer> colors = new ArrayList<>();
//        for (BarEntry be:
//                data) {
//            if(be.getY()>dailyLimit){
//                colors.add(ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.dark_red, null));
//            }
//            else {
//                colors.add(ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.myColorPrimary, null));
//            }
//        }
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 20));
        barEntries.add(new BarEntry(1, 30));
        barEntries.add(new BarEntry(2, 40));
        barEntries.add(new BarEntry(3, 38));
        barEntries.add(new BarEntry(4, 25));
        barEntries.add(new BarEntry(5, 35));
        barEntries.add(new BarEntry(6, 20));
        barEntries.add(new BarEntry(7, 38));
        barEntries.add(new BarEntry(8, 20));
        barEntries.add(new BarEntry(9, 30));
        barEntries.add(new BarEntry(10, 40));
        barEntries.add(new BarEntry(11, 25));
        barEntries.add(new BarEntry(12, 35));
        barEntries.add(new BarEntry(13, 20));

        BarDataSet set = new BarDataSet(barEntries, "");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        //set.setColors(colors);
        set.setDrawValues(true);
        BarData barData = new BarData(set);
        barData.setBarWidth(.8f);
        barChartMonthlyExpense.setData(barData);
        barChartMonthlyExpense.setViewPortOffsets(0,0,0,0);
        barChartMonthlyExpense.setExtraOffsets(0, 0, 0, 0);


        barChartMonthlyExpense.getContentRect().set(0, 0, barChartMonthlyExpense.getWidth(), barChartMonthlyExpense.getHeight());
        barChartMonthlyExpense.animateY(500);
        barChartMonthlyExpense.setScaleEnabled(false);
        barChartMonthlyExpense.setDrawValueAboveBar(true);
        barChartMonthlyExpense.setDrawBorders(false);
        barChartMonthlyExpense.setExtraOffsets(0,0,0,0);
        barChartMonthlyExpense.getLegend().setEnabled(false);
        barChartMonthlyExpense.setVisibleXRangeMaximum(7); // allow 20 values to be displayed at once on the x-axis, not more
        barChartMonthlyExpense.moveViewToX(-1);
        //barChartMonthlyExpense.
        XAxis xAxis = barChartMonthlyExpense.getXAxis();
        YAxis left = barChartMonthlyExpense.getAxisLeft();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(beDataController.getXAxisValues()));
        left.setAxisMaximum(30);
        left.setDrawLabels(true); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(false); // draw a zero line
        left.setInverted(true);

        barChartMonthlyExpense.getAxisRight().setEnabled(false); // no right axis
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.BLACK);
        //xAxis.setCenterAxisLabels(true);
        barChartMonthlyExpense.setDrawGridBackground(false);
        barChartMonthlyExpense.setFitBars(false);
        barChartMonthlyExpense.invalidate();
    }

    private void onFirstRun() {
        //MySharedPreferences msp = new MySharedPreferences(getApplicationContext());
            setNotification();
            //msp.setIsFirstRun(false);

    }

    private void setNotification() {
        //Long alertTime = new GregorianCalendar().getTimeInMillis()+10*1000;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent alertIntent = new Intent(this, Notification.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT));
    }

//    private void setAlart(long timeInMillis) {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, Notification.class);
//        intent.setAction("MY_NOTIFICATION_MESSAGE");
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
//        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
//    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settingMenuButton) {
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void SetPieChartDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            pieDate = formatter.parse(formatter.format(calendar.getTime()));
        }catch (Exception ex){

        }
        //calendar.add(Calendar.DAY_OF_MONTH, -1); to substract date
    }
    private  void ChangePieDate(int noOfDay){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pieDate);
        calendar.add(Calendar.DAY_OF_MONTH, noOfDay);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar2 = Calendar.getInstance();
        Date today = new Date(), expectedDay = new Date();

        try {
            today = formatter.parse(formatter.format(calendar2.getTime()));
        }catch (Exception ex){

        }
        try {
            expectedDay = formatter.parse(formatter.format(calendar.getTime()));
        }catch (Exception ex){

        }
        if(expectedDay.compareTo(today)<=0){
            pieDate = expectedDay;
        }
    }
    boolean closable = false;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(closable){
            //super.onBackPressed();
            this.moveTaskToBack(true);

        }else {
            Toast.makeText(getApplicationContext(),"press again to exit", Toast.LENGTH_SHORT).show();
            closable = !closable;
        }
    }

    private void BindDataToPieChart() {
        pieChart.setUsePercentValues(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        pieChart.getDescription().setText("");

        pieChart.setCenterText(dateFormat.format(pieDate));
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);

        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(35f);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieEntryDataController pedc = new PieEntryDataController();
        ArrayList<PieEntry> yValues = pedc.GetList(getApplicationContext(), pieDate);
        if(yValues.size()<1){
            pieChart.setHoleRadius(50f);
            pieChart.setCenterText(dateFormat.format(pieDate)+" No expense on this date");
        }
        Double Total = pedc.getTotal();
        textView.setText(getResources().getString(R.string.total)+" : "+Total.toString()+"   "+getResources().getString(R.string.chart_slide));
        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
    }



    public static MMDatabase getDBInstance(final Context context) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, MMDatabase.class, "MMdb").allowMainThreadQueries().build();
            }
            return INSTANCE;
    }

    // close database
    public static void destroyDBInstance(){
        if (INSTANCE.isOpen()) INSTANCE.close();
        INSTANCE = null;
    }

}
