package com.tiringbring.dailyexpenses.Activitie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FileExplorerActivity.class));
            }
        });
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportExport.exportDB(getApplicationContext());
               // StartActivity.myAppRoomDatabase = Room.databaseBuilder(getApplicationContext(), ExpenseDatabase.class, "Expensedb").allowMainThreadQueries().build();
            }
        });

    }
}
