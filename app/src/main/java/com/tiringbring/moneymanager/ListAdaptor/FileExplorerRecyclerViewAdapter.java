package com.tiringbring.moneymanager.ListAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tiringbring.moneymanager.Activity.FileExplorerActivity;
import com.tiringbring.moneymanager.R;

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

        if(name.equals("specialId8143y01")){
            holder.ivFileIcon.setVisibility(View.GONE);
            holder.ivFolderIcon.setVisibility(View.GONE);
            holder.ivBackIcon.setVisibility(View.VISIBLE);
            holder.tvFolder.setText("Go back");
        }
        else if(name.charAt(name.length()-1) == '/'){
            holder.ivFileIcon.setVisibility(View.GONE);
            holder.ivFolderIcon.setVisibility(View.VISIBLE);
            holder.ivBackIcon.setVisibility(View.GONE);
            holder.tvFolder.setText(name.substring(0, name.length()-1));
        }
        else{
            holder.ivFileIcon.setVisibility(View.VISIBLE);
            holder.ivFolderIcon.setVisibility(View.GONE);
            holder.ivBackIcon.setVisibility(View.GONE);
            holder.tvFolder.setText(name);
        }
        holder.cvRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof FileExplorerActivity) {
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
        public ImageView ivBackIcon;
        public CardView cvRow;

        public ViewHolder(View itemView) {
            super(itemView);
            cvRow = (CardView) itemView.findViewById(R.id.cvRow);
            tvFolder = (TextView) itemView.findViewById(R.id.tvFileText);
            ivFolderIcon = (ImageView) itemView.findViewById(R.id.ivFolderIcon);
            ivFileIcon = (ImageView) itemView.findViewById(R.id.ivFileIcon);
            ivBackIcon = (ImageView) itemView.findViewById(R.id.ivBackIcon);


        }
    }
}
