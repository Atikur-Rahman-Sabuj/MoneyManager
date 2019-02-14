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

import com.tiringbring.dailyexpenses.DataController.ExpenseDataController;
import com.tiringbring.dailyexpenses.Entity.DayExpenses;
import com.tiringbring.dailyexpenses.ListAdaptor.ExpenseExpandableListAdaptor;

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
        Toast.makeText(getContext(),"here", Toast.LENGTH_LONG).show();
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableExpenseList);
        List<Expense> expenses = StartActivity.myAppRoomDatabase.expenseDao().GetExpenses();
        dayExpensesList = new ExpenseDataController(expenses).getDailyExpenses();
        expandableListAdapter = new ExpenseExpandableListAdaptor(getContext(), dayExpensesList);
        expandableListView.setAdapter(expandableListAdapter);
        return  view;
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
