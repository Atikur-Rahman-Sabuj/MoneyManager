package com.tiringbring.dailyexpenses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.tiringbring.dailyexpenses.DataController.DateDataController;
import com.tiringbring.dailyexpenses.DataController.ExpenseDataController;
import com.tiringbring.dailyexpenses.Entity.DayExpenses;
import com.tiringbring.dailyexpenses.Entity.MonthExpenses;
import com.tiringbring.dailyexpenses.Entity.YearlyExpenses;
import com.tiringbring.dailyexpenses.ListAdaptor.ExpenseExpandableListAdaptor;
import com.tiringbring.dailyexpenses.ListAdaptor.MonthlyExpenseExpandableListAdaptor;
import com.tiringbring.dailyexpenses.ListAdaptor.YearlyExpenseExpandableListAdaptor;

import java.util.Date;
import java.util.List;

import RoomDb.Expense;

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
    List<DayExpenses> dayExpensesList;

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
        List<Expense> expenses = StartActivity.myAppRoomDatabase.expenseDao().GetExpenses();
        dayExpensesList = new ExpenseDataController(expenses).getDailyExpenses();
        String ddItem = getArguments().getString("DDITEMS");
        switch (ddItem){
            case "daily":{
                expandableListAdapter = new ExpenseExpandableListAdaptor(getContext(), dayExpensesList);
                expandableListView.setAdapter(expandableListAdapter);
                break;
            }
            case "monthly":{
                List<MonthExpenses> monthlyExpenseList = new ExpenseDataController(expenses).GetMonthlyExpenses(dayExpensesList);
                MonthlyExpenseExpandableListAdaptor monthlyExpenseExpandableListAdaptor = new MonthlyExpenseExpandableListAdaptor(getContext(), monthlyExpenseList);
                expandableListView.setAdapter(monthlyExpenseExpandableListAdaptor);
                break;
            }
            case "yearly":{
                List<MonthExpenses> monthlyExpenseList = new ExpenseDataController(expenses).GetMonthlyExpenses(dayExpensesList);
                List<YearlyExpenses> yearlyExpensesList = new ExpenseDataController(expenses).GetYearlyExpenses(monthlyExpenseList);
                YearlyExpenseExpandableListAdaptor yearlyExpenseExpandableListAdaptor = new YearlyExpenseExpandableListAdaptor(getContext(), yearlyExpensesList);
                expandableListView.setAdapter(yearlyExpenseExpandableListAdaptor);
                break;
            }
            case "custom":{
                Date startDate = new DateDataController().StringToDate( getArguments().getString("START_DATE"));
                Date endDate = new DateDataController().StringToDate(getArguments().getString("END_DATE"));
                List<DayExpenses> customDayExpenseList = new ExpenseDataController(expenses).MakeCustomList(startDate, endDate);
                ExpenseList expenseList = (ExpenseList)getActivity();
                expenseList.setTotalTextView(getTotal(customDayExpenseList));
                expandableListAdapter = new ExpenseExpandableListAdaptor(getContext(), customDayExpenseList);
                expandableListView.setAdapter(expandableListAdapter);
                break;
            }

        }



        return  view;
    }
    private double getTotal(List<DayExpenses> dayExpensesList){
        double sum = 0;
        for (DayExpenses dex: dayExpensesList
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
