package com.tiringbring.dailyexpenses;

import android.annotation.SuppressLint;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tiringbring.dailyexpenses.DataController.BarEntryDataController;
import com.tiringbring.dailyexpenses.DataController.PieEntryDataController;
import com.tiringbring.dailyexpenses.Utility.OnSwipeTouchListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private BarChart mChart;
    private PieChart pieChart;
    private Date pieDate;
    private FloatingActionButton fabAddNew;
    private FloatingActionButton fabShowList;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        fabAddNew = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabShowList = (FloatingActionButton) findViewById(R.id.fabShowAll);
        mChart= (BarChart) findViewById(R.id.barChart);
        pieChart = (PieChart) findViewById(R.id.pieChart);


        SetPieChartDate();
        BindDataToPieChart();



        mChart.getDescription().setEnabled(false);
        BarEntryDataController beDataController = new BarEntryDataController();
        List<BarEntry> data = beDataController.GetBarEntries();
//        data.add(new BarEntry(1, 455 ));
//        data.add(new BarEntry(2, 500));
//        data.add(new BarEntry(3, 235));
//        data.add(new BarEntry(4, 789));
//        data.add(new BarEntry(5, 123));
//        data.add(new BarEntry(6, 999));
//        data.add(new BarEntry(7, 444));
        //generating colors
        List<Integer> colors = new ArrayList<>();
        for (BarEntry be:
                data) {
            if(be.getY()>500){
                colors.add(Color.RED);
            }
            else {
                colors.add(Color.GREEN);
            }
        }
        BarDataSet set = new BarDataSet(data, "");
        //set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setColors(colors);
        set.setDrawValues(false);
        BarData barData = new BarData(set);
        //barData.setBarWidth(.8f);
        mChart.setData(barData);
        mChart.setExtraOffsets(0, 0, 0, 0);


        mChart.getContentRect().set(0, 0, mChart.getWidth(), mChart.getHeight());
        mChart.invalidate();
        mChart.animateY(500);
        mChart.setScaleEnabled(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDrawBorders(false);
        mChart.setDrawMarkers(false);
        //mChart.setExtraOffsets(0,0,0,0);
        mChart.getLegend().setEnabled(false);
        mChart.setVisibleXRangeMaximum(7); // allow 20 values to be displayed at once on the x-axis, not more
        mChart.moveViewToX(0);
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
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.BLACK);
        //xAxis.setCenterAxisLabels(true);
        mChart.setDrawGridBackground(false);
        mChart.setFitBars(false);

        pieChart.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            public void onSwipeTop() {
                //Toast.makeText(StartActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(StartActivity.this, "right", Toast.LENGTH_SHORT).show();
                ChangePieDate(1);
                pieChart.clear();
                BindDataToPieChart();
            }
            public void onSwipeLeft() {
                Toast.makeText(StartActivity.this, "left", Toast.LENGTH_SHORT).show();
                ChangePieDate(-1);
                pieChart.clear();
                BindDataToPieChart();
            }
            public void onSwipeBottom() {
                //Toast.makeText(StartActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void BindDataToPieChart() {
        pieChart.setUsePercentValues(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        pieChart.getDescription().setText(dateFormat.format(pieDate));
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new PieEntryDataController().GetList(pieDate);
        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);

    }
}
