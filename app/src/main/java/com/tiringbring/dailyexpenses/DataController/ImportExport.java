package com.tiringbring.dailyexpenses.DataController;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ImportExport {
    public static void importDB(String backupPath, Context context) {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "com.tiringbring.dailyexpenses"
                        + "//databases//" + "Expensedb";
                String backupDBPath  =  backupPath;//"/RoomDB/Expensedb";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText( context, backupDB.toString(),
                        Toast.LENGTH_LONG).show();


            }
        } catch (Exception e) {

            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
    //exporting database
    public static void exportDB(Context context) {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "com.tiringbring.dailyexpenses"
                        + "//databases//" + "Expensedb";
                String backupDBPath  = "/Expense/Expensedb";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                File yourAppDir = new File(Environment.getExternalStorageDirectory()+File.separator+"Expense");

                if(!yourAppDir.exists() && !yourAppDir.isDirectory())
                {
                    // create empty directory
                    if (yourAppDir.mkdirs())
                    {
                        Log.i("CreateDir","App dir created");
                    }
                    else
                    {
                        Log.w("CreateDir","Unable to create app dir!");
                    }
                }
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(context, backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }


}
