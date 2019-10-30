package com.tiringbring.moneymanager.Activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import RoomDb.Category;
import RoomDb.MMDatabase;
import RoomDb.Transaction;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.os.TestLooperManager;
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
import com.tiringbring.moneymanager.DataController.InitialData;
import com.tiringbring.moneymanager.DataController.MySharedPreferences;
import com.tiringbring.moneymanager.DataController.PieEntryDataController;
import com.tiringbring.moneymanager.DataController.TransactionDataController;
import com.tiringbring.moneymanager.Entity.DayTransactions;
import com.tiringbring.moneymanager.Entity.MonthTransactions;
import com.tiringbring.moneymanager.ListAdaptor.ChartMonthlyAdaptor;
import com.tiringbring.moneymanager.Notification.Notification;
import com.tiringbring.moneymanager.R;
import com.tiringbring.moneymanager.Utility.ResourceManager;
import com.tiringbring.moneymanager.chart.MonthlyChartData;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private static MMDatabase INSTANCE;
    private BarChart barChartMonthlyExpense;
    private PieChart pcTodaysTransactions;
    private TextView tvMonthlyIncomeTotal, tvMonthlyExpenseTotal, tvMonthlyBalanceTotal,
            tvDailyIncomeTotal, tvDailyExpenseTotal, tvDailyBalanceTotal,
            tvFirstTransactionType, tvFirstTransactionValue, tvFirstTransactionName, tvFirstTransactionCategory,
            tvSecondTransactionType, tvSecondTransactionValue, tvSecondTransactionName, tvSecondTransactionCategory,
            tvThirdTransactionType, tvThirdTransactionValue, tvThirdTransactionName, tvThirdTransactionCategory;
    Long dailyLimit;
    private Date pieDate;
    private Button btnAddNew;
    private Button btnShowList, btnBottomNavigation;
    private RecyclerView rvTestList;
    private CardView cvTodayInfo;
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


        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom);
        getSupportActionBar().getCustomView().setLayoutParams(new androidx.appcompat.widget.Toolbar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT, 3));
        //getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();


        onFirstRun();
        tvMonthlyIncomeTotal = (TextView) findViewById(R.id.tvMonthlyIncomeTotal);
        tvMonthlyExpenseTotal = (TextView) findViewById(R.id.tvMonthlyExpenseTotal);
        tvMonthlyBalanceTotal = (TextView) findViewById(R.id.tvMonthlyBalanceTotal);

        tvDailyIncomeTotal = (TextView) findViewById(R.id.tvDailyIncomeTotal);
        tvDailyExpenseTotal = (TextView) findViewById(R.id.tvDailyExpenseTotal);
        tvDailyBalanceTotal = (TextView) findViewById(R.id.tvDailyBalanceTotal);

        tvFirstTransactionType = (TextView) findViewById(R.id.tvFirstTransactionType);
        tvFirstTransactionValue = (TextView) findViewById(R.id.tvFirstTransactionValue);
        tvFirstTransactionName = (TextView) findViewById(R.id.tvFirstTransactionName);
        tvFirstTransactionCategory = (TextView) findViewById(R.id.tvFirstTransactionCategory);

        tvSecondTransactionType = (TextView) findViewById(R.id.tvSecondTransactionType);
        tvSecondTransactionValue = (TextView) findViewById(R.id.tvSecondTransactionValue);
        tvSecondTransactionName = (TextView) findViewById(R.id.tvSecondTransactionName);
        tvSecondTransactionCategory = (TextView) findViewById(R.id.tvSecondTransactionCategory);

        tvThirdTransactionType = (TextView) findViewById(R.id.tvThirdTransactionType);
        tvThirdTransactionValue = (TextView) findViewById(R.id.tvThirdTransactionValue);
        tvThirdTransactionName = (TextView) findViewById(R.id.tvThirdTransactionName);
        tvThirdTransactionCategory = (TextView) findViewById(R.id.tvThirdTransactionCategory);
        cvTodayInfo = (CardView) findViewById(R.id.cvTodayInfo);

        BindDataTodaySection();
        BindDataToRecentTransactions();

        btnAddNew = (Button) findViewById(R.id.btnAddNew);
        btnShowList = (Button) findViewById(R.id.btnShowList);
        pcTodaysTransactions = (PieChart) findViewById(R.id.pcTodaysTransactions);
        barChartMonthlyExpense = (BarChart) findViewById(R.id.dailyExpenseBarChart);
        rvTestList = (RecyclerView) findViewById(R.id.rvTestList);
        rvTestList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MonthlyChartData cmd = new MonthlyChartData();
        List<MonthTransactions> monthTransactionsList = cmd.GetMonthlyExpenseList(getApplicationContext());
        MonthTransactions thisMonth = null;
        if(monthTransactionsList.size()>0){
            thisMonth = cmd.GetThisMonthTransactions(monthTransactionsList);
        }
        if(thisMonth == null){
            tvMonthlyIncomeTotal.setText("0");
            tvMonthlyExpenseTotal.setText("0");
            tvMonthlyBalanceTotal.setText("0");
        }else{
            tvMonthlyIncomeTotal.setText(String.format("%.2f", thisMonth.incomeTotal));
            tvMonthlyExpenseTotal.setText(String.format("%.2f", thisMonth.expenseTotal));
            tvMonthlyBalanceTotal.setText(String.format("%.2f", thisMonth.incomeTotal - thisMonth.expenseTotal));
        }
        ChartMonthlyAdaptor chartMonthlyAdaptor = new ChartMonthlyAdaptor(getApplicationContext(), monthTransactionsList, cmd.GetMaximumMonthlyIncome(monthTransactionsList), cmd.GetMaximumMonthlyExpense(monthTransactionsList));
        rvTestList.setAdapter(chartMonthlyAdaptor);
        SetPieChartDate();
        BindDataToPieChart();
        BindDataToDailyExpenseBarChart();





        dailyLimit = new MySharedPreferences(getApplicationContext()).getDayilyLimit();

        //new PlayAnimation().PlayFadeIn(getApplicationContext(), btnAddNew);
        //Notification set
//        Calendar nCalendar = Calendar.getInstance();
//        nCalendar.set(Calendar.HOUR, 21);
//        nCalendar.set(Calendar.MINUTE, 45);
//        nCalendar.set(Calendar.SECOND, 0);
//        setAlart(nCalendar.getTimeInMillis());

        cvTodayInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DailyTransactionsActivity.class);
                startActivity(intent);
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();

        BindDataTodaySection();
        BindDataToRecentTransactions();
        MonthlyChartData cmd = new MonthlyChartData();
        List<MonthTransactions> monthTransactionsList = cmd.GetMonthlyExpenseList(getApplicationContext());
        MonthTransactions thisMonth = null;
        if(monthTransactionsList.size()>0){
            thisMonth = cmd.GetThisMonthTransactions(monthTransactionsList);
        }
        if(thisMonth == null){
            tvMonthlyIncomeTotal.setText("0");
            tvMonthlyExpenseTotal.setText("0");
            tvMonthlyBalanceTotal.setText("0");
        }else{
            tvMonthlyIncomeTotal.setText(String.format("%.2f", thisMonth.incomeTotal));
            tvMonthlyExpenseTotal.setText(String.format("%.2f", thisMonth.expenseTotal));
            tvMonthlyBalanceTotal.setText(String.format("%.2f", thisMonth.incomeTotal - thisMonth.expenseTotal));
        }
        ChartMonthlyAdaptor chartMonthlyAdaptor = new ChartMonthlyAdaptor(getApplicationContext(), monthTransactionsList, cmd.GetMaximumMonthlyIncome(monthTransactionsList), cmd.GetMaximumMonthlyExpense(monthTransactionsList));
        rvTestList.setAdapter(chartMonthlyAdaptor);
        SetPieChartDate();
        BindDataToPieChart();
        BindDataToDailyExpenseBarChart();
    }

    private void BindDataToRecentTransactions() {
        List<Transaction> transactions = StartActivity.getDBInstance(getApplicationContext()).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();
        if(transactions.size()>=3)
            transactions = transactions.subList(0,3);
        if(transactions.size()>=1){
            BindDatasUsedByBindDataToRecentTransactions(transactions.get(0), tvFirstTransactionType, tvFirstTransactionValue, tvFirstTransactionName, tvFirstTransactionCategory);
        }
        if(transactions.size()>=2){
            BindDatasUsedByBindDataToRecentTransactions(transactions.get(1), tvSecondTransactionType, tvSecondTransactionValue, tvSecondTransactionName, tvSecondTransactionCategory);
        }
        if(transactions.size()>=3){
            BindDatasUsedByBindDataToRecentTransactions(transactions.get(2), tvThirdTransactionType, tvThirdTransactionValue, tvThirdTransactionName, tvThirdTransactionCategory);
        }
    }
    private void BindDatasUsedByBindDataToRecentTransactions(Transaction transaction, TextView type, TextView value, TextView name, TextView category){
        Category _category = StartActivity.getDBInstance(getApplicationContext()).mmDao().GetCategoryById(transaction.getCategoryId());
        if(transaction.getIsIncome()){
            type.setText("Income");
        }else {
            type.setText("Expense");
        }
        value.setText(String.format("%.2f", transaction.getAmount()));
        if(transaction.getName().equals(null) || transaction.getName().equals("")){
            name.setText(_category.getName());
        }else{
            name.setText(transaction.getName());
        }
        category.setText(_category.getName());

    }

    private void BindDataToDailyExpenseBarChart() {
        barChartMonthlyExpense.getDescription().setEnabled(false);
        final BarEntryDataController beDataController = new BarEntryDataController();
        List<BarEntry> barEntries = beDataController.GetBarEntries(getApplicationContext());
        //generating colors
        List<Integer> colors = new ArrayList<>();
        for (BarEntry be: barEntries) {
            if(be.getY()>500){
                colors.add(ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.dark_red, null));
            }
            else {
                colors.add(ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.myColorPrimary, null));
            }
        }

        BarDataSet set = new BarDataSet(barEntries, "");
        set.setColors(colors);
        //set.setColors(colors);
        set.setDrawValues(true);
        BarData barData = new BarData(set);
        barData.setBarWidth(.8f);
        barChartMonthlyExpense.setData(barData);
        //barChartMonthlyExpense.setViewPortOffsets(0,0,0,0);
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
        //left.setAxisMaximum(1000);
        left.setDrawLabels(true); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(false); // draw a zero line
        left.setInverted(false);

        barChartMonthlyExpense.getAxisRight().setEnabled(false); // no right axis
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.BLACK);
        //xAxis.setCenterAxisLabels(true);
        barChartMonthlyExpense.setDrawGridBackground(false);
        barChartMonthlyExpense.setFitBars(false);
        barChartMonthlyExpense.invalidate();
    }

    private void BindDataTodaySection(){
        DayTransactions dayTransactions = TransactionDataController.GetTodaysTransactions(getApplicationContext());
        if(dayTransactions!=null) {
            tvDailyIncomeTotal.setText(String.format("%.2f", dayTransactions.incomeTotal));
            tvDailyExpenseTotal.setText(String.format("%.2f", dayTransactions.expenseTotal));
            tvDailyBalanceTotal.setText(String.format("%.2f", dayTransactions.incomeTotal - dayTransactions.expenseTotal));
        }
    }

    private void onFirstRun() {
        MySharedPreferences msp = new MySharedPreferences(getApplicationContext());
        if(msp.getIsFirstRun()){
            InitialData.CreateCategories(getApplicationContext());
            msp.setIsFirstRun(false);
        }
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
        pcTodaysTransactions.setUsePercentValues(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        pcTodaysTransactions.getDescription().setText("");

        pcTodaysTransactions.setCenterText(dateFormat.format(pieDate));
        pcTodaysTransactions.setExtraOffsets(0,0,0,0);
        pcTodaysTransactions.setDragDecelerationFrictionCoef(0.95f);
        pcTodaysTransactions.setDrawHoleEnabled(true);

        pcTodaysTransactions.getLegend().setEnabled(false);
        pcTodaysTransactions.setHoleColor(Color.WHITE);
        pcTodaysTransactions.setHoleRadius(40f);
        pcTodaysTransactions.setTransparentCircleRadius(50f);
        pcTodaysTransactions.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        PieEntryDataController pedc = new PieEntryDataController();
        ArrayList<PieEntry> yValues = pedc.GetList(getApplicationContext(), pieDate);
        if(yValues.size()<1){
            pcTodaysTransactions.setHoleRadius(50f);
            pcTodaysTransactions.setCenterText("No expense");
            //pcTodaysTransactions.setCenterText(dateFormat.format(pieDate)+" No expense on this date");
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
