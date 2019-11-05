package com.tiringbring.moneymanager.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiringbring.moneymanager.Activity.FirstStartActivity;
import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.MySharedPreferences;
import com.tiringbring.moneymanager.R;
import com.tiringbring.moneymanager.Utility.ResourceManager;


public class FirstStartLanguageFragment extends Fragment {
    private CardView cvEnglish, cvBangla;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_start_language, container, false);
        cvEnglish = (CardView)view.findViewById(R.id.cvEnglish);
        cvBangla = (CardView)view.findViewById(R.id.cvBangla);
        cvEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ResourceManager.changeLanguage(getContext(),"en");
                new MySharedPreferences(getContext()).setLanguage("en");
                StartStartActivity();

            }
        });
        cvBangla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResourceManager.changeLanguage(getContext(),"bn");
                new MySharedPreferences(getContext()).setLanguage("bn");
                StartStartActivity();
            }
        });
        return view;
    }
    private void StartStartActivity(){
        ((FirstStartActivity)getActivity()).onFirstRun();
        Intent intent  = new Intent(getContext(), StartActivity.class);
        startActivity(intent);
        getActivity().finish();
    }




}
