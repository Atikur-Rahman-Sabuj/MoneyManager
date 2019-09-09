package com.tiringbring.dailyexpenses.Activitie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.tiringbring.dailyexpenses.DataController.ImportExport;
import com.tiringbring.dailyexpenses.R;

import RoomDb.ExpenseDatabase;

public class ImportExportActivity extends AppCompatActivity {
    private Button btnImport, btnExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export);
        btnImport = (Button) findViewById(R.id.btnImport);
        btnExport = (Button) findViewById(R.id.btnExport);
        Intent intent = getIntent();
        if(intent.getExtras().getString("isImport").equals("success")){
            Snackbar.make(findViewById(R.id.clRootExportImport),"Imported successfully",Snackbar.LENGTH_LONG).show();
        }else if(intent.getExtras().getString("isImport").equals("failed")){
            Snackbar.make(findViewById(R.id.clRootExportImport),"Import failed, try again.",Snackbar.LENGTH_LONG).show();
        }
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FileExplorerActivity.class));
            }
        });
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportExport.exportDB(ImportExportActivity.this);
                //Snackbar.make(findViewById(R.id.clRootExportImport), "Welcome to AndroidHive", Snackbar.LENGTH_LONG).show();
               // StartActivity.myAppRoomDatabase = Room.databaseBuilder(getApplicationContext(), ExpenseDatabase.class, "Expensedb").allowMainThreadQueries().build();
            }
        });

    }
}
