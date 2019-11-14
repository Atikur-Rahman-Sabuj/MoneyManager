package com.tiringbring.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiringbring.moneymanager.DataController.ImportExport;
import com.tiringbring.moneymanager.ListAdaptor.FileExplorerRecyclerViewAdapter;
import com.tiringbring.moneymanager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileExplorerActivity extends AppCompatActivity {

    private List<String> item = null;
    private List<String> path = null;
    private String root = "/";
    private TextView myPath;
    private RecyclerView rcFiles;
    private RecyclerView.Adapter adapter;

    private ImageView ivBarLeft,ivBarRight;
    private TextView tvBarText;

    /**
     * Called when the activity is first created.
     */

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_explorer);
        tvBarText = (TextView) findViewById(R.id.tvBarText);
        ivBarLeft = (ImageView) findViewById(R.id.ivBarLeft);
        ivBarRight = (ImageView) findViewById(R.id.ivBarRight);
        tvBarText.setText("Import");
        ivBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ImportExportActivity.class));

            }
        });
        ivBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });

        //myPath = (TextView) findViewById(R.id.path);

        getDir(Environment.getExternalStorageDirectory().toString()+root);

    }


    private void getDir(String dirPath) {

        //myPath.setText("Location: " + dirPath);

        String filePath = Environment.getExternalStorageDirectory().toString()+dirPath;
        item = new ArrayList<String>();

        path = new ArrayList<String>();


        File f = new File(dirPath);

        File[] files = f.listFiles();
        String test = Environment.getExternalStorageDirectory().toString()+root;

        if (!dirPath.equals(Environment.getExternalStorageDirectory().toString()+root) && !dirPath.equals(Environment.getExternalStorageDirectory().toString())) {


            //item.add(root);

            //path.add(root);


            item.add("specialId8143y01");

            path.add(f.getParent());


        }


        for (int i = 0; i < files.length; i++) {

            File file = files[i];

            path.add(file.getPath());

            if (file.isDirectory())

                item.add(file.getName() + "/");

            else

                item.add(file.getName());

        }


        //ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, R.layout.file_row, item);
        //setListAdapter(fileList);
        rcFiles = (RecyclerView) findViewById(R.id.rvFileList);
        rcFiles.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FileExplorerRecyclerViewAdapter(item, this);
        rcFiles.setAdapter(adapter);

    }




    public void onListItemClick(int position) {
        File file = new File(path.get(position));
        if (file.isDirectory()) {
            if (file.canRead())
                getDir(path.get(position));
            else {
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.folder_icon)
                        .setTitle("[" + file.getName() + "] folder can't be read!")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).show();
            }
        } else {

            if(ImportExport.importDB(path.get(position).substring(Environment.getExternalStorageDirectory().toString().length()), getApplicationContext())){
                startActivity(new Intent(getApplicationContext(), ImportExportActivity.class).putExtra("isImport","success"));

            }else {
                startActivity(new Intent(getApplicationContext(), ImportExportActivity.class).putExtra("isImport","failed"));
            }


        }

    }
}
