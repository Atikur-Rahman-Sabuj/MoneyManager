package com.tiringbring.dailyexpenses;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;

import RoomDb.ExpenseDatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Pair;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tiringbring.dailyexpenses.DataController.BarEntryDataController;
import com.tiringbring.dailyexpenses.DataController.MySharedPreferences;
import com.tiringbring.dailyexpenses.DataController.PieEntryDataController;
import com.tiringbring.dailyexpenses.Notification.Notification;
import com.tiringbring.dailyexpenses.Utility.OnSwipeTouchListener;
import com.tiringbring.dailyexpenses.Utility.PlayAnimation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    public static ExpenseDatabase myAppRoomDatabase;
    private BarChart mChart;
    private PieChart pieChart;
    private TextView textView;
    Long dailyLimit;
    private Date pieDate;
    private Button btnAddNew;
    private Button btnShowList;
    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        myAppRoomDatabase = Room.databaseBuilder(getApplicationContext(),ExpenseDatabase.class, "Expensedb").allowMainThreadQueries().build();
        onFirstRun();
        btnAddNew = (Button) findViewById(R.id.btnAddNew);
        btnShowList = (Button) findViewById(R.id.btnShowList);
        mChart= (BarChart) findViewById(R.id.barChart);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        textView = (TextView) findViewById(R.id.textView);
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
                Intent intent = new Intent(getApplicationContext(), AddExpense.class);


                startActivity(intent);


            }
        });
        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpenseList.class);

                startActivity(intent);

            }
        });
        SetPieChartDate();
        BindDataToPieChart();

        mChart.getDescription().setEnabled(false);
        final BarEntryDataController beDataController = new BarEntryDataController();
        List<BarEntry> data = beDataController.GetBarEntries();
        //generating colors
        List<Integer> colors = new ArrayList<>();
        for (BarEntry be:
                data) {
            if(be.getY()>dailyLimit){
                colors.add(ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.dark_red, null));
            }
            else {
                colors.add(ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.myColorPrimary, null));
            }
        }
        BarDataSet set = new BarDataSet(data, "");
        //set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setColors(colors);
        set.setDrawValues(true);
        BarData barData = new BarData(set);
        barData.setBarWidth(.8f);
        mChart.setData(barData);
        mChart.setExtraOffsets(0, 0, 0, 0);


        mChart.getContentRect().set(0, 0, mChart.getWidth(), mChart.getHeight());
        mChart.animateY(500);
        mChart.setScaleEnabled(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDrawBorders(false);
        //mChart.setExtraOffsets(0,0,0,0);
        mChart.getLegend().setEnabled(false);
        mChart.setVisibleXRangeMaximum(7); // allow 20 values to be displayed at once on the x-axis, not more
        mChart.moveViewToX(-1);
        XAxis xAxis = mChart.getXAxis();
        YAxis left = mChart.getAxisLeft();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(beDataController.getXAxisValues()));
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(false); // draw a zero line

        mChart.getAxisRight().setEnabled(false); // no right axis
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.BLACK);
        //xAxis.setCenterAxisLabels(true);
        mChart.setDrawGridBackground(false);
        mChart.setFitBars(false);
        mChart.invalidate();
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                pieDate = beDataController.dates.get(((int) e.getX()));
                BindDataToPieChart();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        pieChart.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            public void onSwipeTop() {
                //Toast.makeText(StartActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(StartActivity.this, "right", Toast.LENGTH_SHORT).show();
                ChangePieDate(1);
                pieChart.clear();
                BindDataToPieChart();
            }
            public void onSwipeLeft() {
                //Toast.makeText(StartActivity.this, "left", Toast.LENGTH_SHORT).show();
                ChangePieDate(-1);
                pieChart.clear();
                BindDataToPieChart();
            }
            public void onSwipeBottom() {
                //Toast.makeText(StartActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });

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
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
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
        ArrayList<PieEntry> yValues = pedc.GetList(pieDate);
        if(yValues.size()<1){
            pieChart.setHoleRadius(50f);
            pieChart.setCenterText(dateFormat.format(pieDate)+" No expense on this date");
        }
        Double Total = pedc.getTotal();
        textView.setText("Total : "+Total.toString()+"   Slide chart to see more!");
        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
    }

}
