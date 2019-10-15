package com.tiringbring.moneymanager.Activity.TabbedTransactionViews.CustomTransactions;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiringbring.moneymanager.R;


public class CustomFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root  =  inflater.inflate(R.layout.fragment_custom, container, false);
        return  root;
    }

}
