package com.tiringbring.moneymanager.DataController;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;
import com.tiringbring.moneymanager.Activity.ImportExportActivity;
import com.tiringbring.moneymanager.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ImportExport {
    public static boolean importDB(String backupPath, Context context) {
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
                //Toast.makeText( context, backupDB.toString(),
                //        Toast.LENGTH_LONG).show();
                return  true;

            }
            return  false;
        } catch (Exception e) {

          //  Toast.makeText(context, e.toString(), Toast.LENGTH_LONG)
           //         .show();
            return  false;
        }
    }
    //exporting database
    public static boolean exportDB(Context context) {
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
                Snackbar.make(((ImportExportActivity)context).findViewById(R.id.clRootExportImport), "Exported to folder: Expense", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(context, backupDB.toString(),
             //           Toast.LENGTH_LONG).show();
                return  true;
            }
            return  false;
        } catch (Exception e) {

            Snackbar.make(((ImportExportActivity)context).findViewById(R.id.clRootExportImport), "Sorry! Could not export", Snackbar.LENGTH_LONG).show();
            return false;
        }
    }


}
