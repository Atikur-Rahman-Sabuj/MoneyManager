package com.tiringbring.moneymanager.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tiringbring.moneymanager.Activity.ExpenseListActivity;
import com.tiringbring.moneymanager.Activity.StartActivity;
import com.tiringbring.moneymanager.DataController.DateDataController;
import com.tiringbring.moneymanager.DataController.ExpenseDataController;
import com.tiringbring.moneymanager.Entity.DayTransactions;
import com.tiringbring.moneymanager.Entity.MonthTransactions;
import com.tiringbring.moneymanager.Entity.YearlyTransactions;
import com.tiringbring.moneymanager.ListAdaptor.ExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.ListAdaptor.MonthlyExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.ListAdaptor.YearlyExpenseExpandableListAdaptor;
import com.tiringbring.moneymanager.R;

import java.util.Date;
import java.util.List;

import RoomDb.Transaction;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpandableExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpandableExpenseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ExpandableListView expandableListView;
    ExpenseExpandableListAdaptor expandableListAdapter;
    List<DayTransactions> dayTransactionsList;

    public ExpandableExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expandable_expense, container, false);
        //Toast.makeText(getContext(),"here", Toast.LENGTH_LONG).show();
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableExpenseList);
        List<Transaction> transactions = StartActivity.getDBInstance(getContext()).mmDao().GetTransaction();
        StartActivity.destroyDBInstance();
        //if(StartActivity.myAppRoomDatabase.isOpen())
            //StartActivity.myAppRoomDatabase.close();
        dayTransactionsList = new ExpenseDataController(transactions).getDailyExpenses();
        String ddItem = getArguments().getString("DDITEMS");
        switch (ddItem){
            case "daily":{
                expandableListAdapter = new ExpenseExpandableListAdaptor(getContext(), dayTransactionsList);
                expandableListView.setAdapter(expandableListAdapter);
                break;
            }
            case "monthly":{
                List<MonthTransactions> monthlyExpenseList = new ExpenseDataController(transactions).GetMonthlyExpenses(dayTransactionsList);
                MonthlyExpenseExpandableListAdaptor monthlyExpenseExpandableListAdaptor = new MonthlyExpenseExpandableListAdaptor(getContext(), monthlyExpenseList);
                expandableListView.setAdapter(monthlyExpenseExpandableListAdaptor);
                break;
            }
            case "yearly":{
                List<MonthTransactions> monthlyExpenseList = new ExpenseDataController(transactions).GetMonthlyExpenses(dayTransactionsList);
                List<YearlyTransactions> yearlyTransactionsList = new ExpenseDataController(transactions).GetYearlyExpenses(monthlyExpenseList);
                YearlyExpenseExpandableListAdaptor yearlyExpenseExpandableListAdaptor = new YearlyExpenseExpandableListAdaptor(getContext(), yearlyTransactionsList);
                expandableListView.setAdapter(yearlyExpenseExpandableListAdaptor);
                break;
            }
            case "custom":{
                Date startDate = new DateDataController().StringToDate( getArguments().getString("START_DATE"));
                Date endDate = new DateDataController().StringToDate(getArguments().getString("END_DATE"));
                List<DayTransactions> customDayExpenseList = new ExpenseDataController(transactions).MakeCustomList(startDate, endDate);
                ExpenseListActivity expenseList = (ExpenseListActivity)getActivity();
                expenseList.setTotalTextView(getTotal(customDayExpenseList));
                expandableListAdapter = new ExpenseExpandableListAdaptor(getContext(), customDayExpenseList);
                expandableListView.setAdapter(expandableListAdapter);
                break;
            }

        }



        return  view;
    }
    private double getTotal(List<DayTransactions> dayTransactionsList){
        double sum = 0;
        for (DayTransactions dex: dayTransactionsList
             ) {
            sum += dex.total;
        }
        return sum;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
