package com.tiringbring.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tiringbring.moneymanager.DataController.PieEntryDataController;
import com.tiringbring.moneymanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DailyTransactionsActivity extends AppCompatActivity {
    private PieChart pcTodaysTransactions;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_transactions);
        pcTodaysTransactions = (PieChart) findViewById(R.id.pcTodaysTransactions);
        date = new Date();
    }

    private void BindDataToPieChart() {
        pcTodaysTransactions.setUsePercentValues(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        pcTodaysTransactions.getDescription().setText("");

        pcTodaysTransactions.setCenterText(dateFormat.format(date));
        pcTodaysTransactions.setExtraOffsets(0,0,0,0);
        pcTodaysTransactions.setDragDecelerationFrictionCoef(0.95f);
        pcTodaysTransactions.setDrawHoleEnabled(true);

        pcTodaysTransactions.getLegend().setEnabled(false);
        pcTodaysTransactions.setHoleColor(Color.WHITE);
        pcTodaysTransactions.setHoleRadius(40f);
        pcTodaysTransactions.setTransparentCircleRadius(50f);
        pcTodaysTransactions.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieEntryDataController pedc = new PieEntryDataController();
        ArrayList<PieEntry> yValues = pedc.GetList(getApplicationContext(), date);
        if(yValues.size()<1){
            pcTodaysTransactions.setHoleRadius(50f);
            pcTodaysTransactions.setCenterText(dateFormat.format(date)+" No expense on this date");
        }
        Double Total = pedc.getTotal();
        //textView.setText(getResources().getString(R.string.total)+" : "+Total.toString()+"   "+getResources().getString(R.string.chart_slide));
        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(8f);

        data.setValueTextColor(Color.WHITE);

        pcTodaysTransactions.setData(data);
    }

}
