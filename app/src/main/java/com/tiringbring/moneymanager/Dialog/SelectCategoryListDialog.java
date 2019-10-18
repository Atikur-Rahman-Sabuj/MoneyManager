package com.tiringbring.moneymanager.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.tiringbring.moneymanager.Activity.AddTransactionActivity;
import com.tiringbring.moneymanager.Activity.BottomNavigationActivity;
import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.Activity.TabbedTransactionViews.DailyTransactions.DailyFragment;
import com.tiringbring.moneymanager.Activity.TabbedTransactionViews.ITabbedFragments;
import com.tiringbring.moneymanager.R;

import java.util.List;

import RoomDb.Category;

public class SelectCategoryListDialog {
    public void showDialog(final Context context) {

        final Dialog dialog = new Dialog(context);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_category_edit_update_test);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.99);
        int height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView title = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        final TextView tvErrorMessage = (TextView) dialog.findViewById(R.id.tvErrorMessage);
        title.setText("Select Category");
        tvErrorMessage.setText("");

        final EditText etCategoryName = (EditText) dialog.findViewById(R.id.etCategoryName);


        Button okButton = (Button) dialog.findViewById(R.id.btnSave);
        Button cancleButton = (Button) dialog.findViewById(R.id.btnCancel);


        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                 NavHostFragment hff =  (NavHostFragment) ((BottomNavigationActivity)context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                 List<Fragment> fragments =   ( List<Fragment>) hff.getChildFragmentManager().getFragments(); //.getFragmentManager().getFragments();
                for(Fragment fragment : fragments){
                    if(fragment != null && fragment.isVisible())
                        ((ITabbedFragments) fragment).Test();
                }
                 //fragment.Test();
                 dialog.dismiss();
            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

}
