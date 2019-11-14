package com.tiringbring.moneymanager.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tiringbring.moneymanager.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class BottomNavigationActivity extends AppCompatActivity {


    private ImageView ivBarLeft,ivBarRight;
    private TextView tvBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getSupportActionBar() != null;
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_bottom_navigation);


        tvBarText = (TextView) findViewById(R.id.tvBarText);
        ivBarLeft = (ImageView) findViewById(R.id.ivBarLeft);
        ivBarRight = (ImageView) findViewById(R.id.ivBarRight);
        ivBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });
        tvBarText.setText("Daily");
        ivBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
            }
        });


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_daily, R.id.navigation_monthly, R.id.navigation_yearly, R.id.navigation_custom)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        Intent intent = getIntent();
        if(intent.getBooleanExtra("isMonth", false)){
            navController.navigate(R.id.navigation_monthly);
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), StartActivity.class));
    }

    public void setHeaderText(String text){
        tvBarText.setText(text);
    }

}
