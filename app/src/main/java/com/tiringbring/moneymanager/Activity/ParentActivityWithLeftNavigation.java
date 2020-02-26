package com.tiringbring.moneymanager.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.tiringbring.moneymanager.R;

public class ParentActivityWithLeftNavigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout mainDrawer;
    protected NavigationView leftNavigationView;
    protected ImageView ivBarLeft;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setMyContentView(int resId){
        setContentView(resId);
        mainDrawer = findViewById(R.id.mainDrawer);
        leftNavigationView = (NavigationView) findViewById(R.id.leftNavigationView);
        leftNavigationView.setNavigationItemSelectedListener(this);
        ivBarLeft = (ImageView) findViewById(R.id.ivBarLeft);
        ivBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainDrawer.openDrawer(GravityCompat.START);
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.l_n_home:{
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.l_n_income:{
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                intent.putExtra("isIncome", true);
                startActivity(intent);
                break;
            }
            case R.id.l_n_expense:{
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.l_n_everyday:{
                startActivity(new Intent(getApplicationContext(), DailyTransactionsActivity.class));
                break;
            }
            case R.id.l_n_show_all:{
                startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
                break;
            }
            case R.id.l_n_setting:{
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        }
        return true;
    }
}
