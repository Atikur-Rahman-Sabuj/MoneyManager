package com.tiringbring.moneymanager.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiringbring.moneymanager.Activity.BottomNavigationActivity;
import com.tiringbring.moneymanager.Fragment.TabbedTransactionViews.ITabbedFragments;
import com.tiringbring.moneymanager.ListAdaptor.SelectCategoryListAdaptor;
import com.tiringbring.moneymanager.R;

import java.util.Date;
import java.util.List;

import RoomDb.Category;

public class SelectCategoryListDialog {
    private  RecyclerView rvSelectCategoryList;
    private List<Category> allCategories;
    private List<Category> selectedCategories;
    private Context context;
    private SelectCategoryListAdaptor selectCategoryListAdaptor;
    public void showDialog(final Context context, List<Category> allCategories, List<Category> selectedCategories ) {
        this.allCategories = allCategories;
        this.selectedCategories = selectedCategories;
        this.context = context;
        final Dialog dialog = new Dialog(context, R.style.PauseDialogCategoryList);
        //R.style.PauseDialogCategoryList
        //android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        //dialog.getWindow().get
        dialog.setContentView(R.layout.dialog_select_category_list);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*1);
        int height = (int)(context.getResources().getDisplayMetrics().heightPixels*1);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        TextView title = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        title.setText(context.getResources().getString(R.string.select_category));


        rvSelectCategoryList = (RecyclerView) dialog.findViewById(R.id.rvSelectCategoryList);
        rvSelectCategoryList.setLayoutManager(new LinearLayoutManager(context));
        selectCategoryListAdaptor = new SelectCategoryListAdaptor(context, this, allCategories, selectedCategories);
        rvSelectCategoryList.setAdapter(selectCategoryListAdaptor);

        Button okButton = (Button) dialog.findViewById(R.id.btnSave);
       // Button cancleButton = (Button) dialog.findViewById(R.id.btnCancel);


        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                 NavHostFragment hff =  (NavHostFragment) ((BottomNavigationActivity)context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                 List<Fragment> fragments =   ( List<Fragment>) hff.getChildFragmentManager().getFragments();
                for(Fragment fragment : fragments){
                    if(fragment != null && fragment.isVisible())
                        ((ITabbedFragments) fragment).NotifySelectedCategoryChange();
                }
                 //fragment.Test();
                 dialog.dismiss();
            }
        });



        dialog.show();

    }
    public void ReloadList(int position){
        Category selectedCategory = allCategories.get(position);
        if(selectedCategories.contains(selectedCategory)){
            selectedCategories.remove(selectedCategory);
        }else{
            selectedCategories.add(selectedCategory);
        }
        selectCategoryListAdaptor.notifyItemChanged(position);
    }

}
