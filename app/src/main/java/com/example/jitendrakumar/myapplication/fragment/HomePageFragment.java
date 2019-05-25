package com.example.jitendrakumar.myapplication.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class HomePageFragment extends Fragment {
    TextView tvHello;
    public String id;
    private FirebaseAuth firebaseAuth;
    LinearLayout  incomeLayout, expenseLayout, incomeReportLayout, expenseReportLayout, borrowReportLayout, lendReportLayout, loginLayout, borrowLayout, lendLayout, aboutLayout;

    TextView incomeTotal, expenseTotal, savingTotal, tvHomeTitle, homeTime, homeIncome;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home_page, container, false );

        tvHello = (TextView) view.findViewById( R.id.tvHello );
        incomeLayout = (LinearLayout) view.findViewById( R.id.incomeLayout );
        expenseLayout = (LinearLayout) view.findViewById( R.id.expenseLayout );
        incomeTotal = (TextView) view.findViewById( R.id.incomeTotal );
        expenseTotal = (TextView) view.findViewById( R.id.expenseTotal );
        tvHomeTitle = (TextView) view.findViewById( R.id.tvHomeTitle );

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Calendar cal = Calendar.getInstance();
        final int year = cal.get( Calendar.YEAR );
        final int month = cal.get( Calendar.MONTH );
        final int day = cal.get( Calendar.DAY_OF_MONTH );

        if(user != null)
        {
            tvHello.setText( "Welcome  "+user.getEmail() );
            tvHello.setVisibility( View.VISIBLE );
        }
        else
        {
            tvHello.setVisibility( View.INVISIBLE );
        }


        incomeTotal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        expenseTotal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        tvHomeTitle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );


        incomeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(), "add income", Toast.LENGTH_SHORT ).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment mFrag = new AddIncomeFragment();
                ft.replace(R.id.screen_area, mFrag);
                ft.commit();
            }
        } );

        expenseLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment mFrag = new AddExpenseFragment();
                ft.replace(R.id.screen_area, mFrag);
                ft.commit();
            }
        } );

        return view;
    }


}

