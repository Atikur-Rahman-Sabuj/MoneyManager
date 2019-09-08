package com.tiringbring.dailyexpenses.ListAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiringbring.dailyexpenses.Activitie.FileExplorerActivity;
import com.tiringbring.dailyexpenses.R;

import java.util.List;

public class FileExplorerRecyclerViewAdapter extends RecyclerView.Adapter<FileExplorerRecyclerViewAdapter.ViewHolder> {

    private List<String> list;
    private Context context;
    public FileExplorerRecyclerViewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_row,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String name = list.get(position);
        holder.tvFolder.setText(name);
        if(name.charAt(name.length()-1) == '/'){
            holder.ivFileIcon.setVisibility(View.GONE);
            holder.ivFolderIcon.setVisibility(View.VISIBLE);
        }
        else{
            holder.ivFolderIcon.setVisibility(View.GONE);
            holder.ivFileIcon.setVisibility(View.VISIBLE);
        }
        holder.tvFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof FileExplorerActivity) {
                    Toast.makeText(context, "Here", Toast.LENGTH_LONG).show();
                    ((FileExplorerActivity)context).onListItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvFolder;
        public ImageView ivFolderIcon;
        public ImageView ivFileIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFolder = (TextView) itemView.findViewById(R.id.tvFileText);
            ivFolderIcon = (ImageView) itemView.findViewById(R.id.ivFolderIcon);
            ivFileIcon = (ImageView) itemView.findViewById(R.id.ivFileIcon);


        }
    }
}
