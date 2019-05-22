package com.example.jitendrakumar.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.adapters.IncomeListAdapter;
import com.example.jitendrakumar.myapplication.models.IncomeData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

@SuppressLint("ValidFragment")
public class IncomeReportFragment extends Fragment {

    private RecyclerView rvIncomeList;
    private ArrayList<IncomeData> incomeList;
    private DatabaseReference incomedbRef;
    private IncomeListAdapter incomeListAdapter;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate( R.layout.fragment_income_report, container, false );
            rvIncomeList = view.findViewById( R.id.rvIncomeList );

            incomeList = new ArrayList<>(  );
            incomedbRef = FirebaseDatabase.getInstance().getReference("income");
            rvIncomeList.setLayoutManager( new LinearLayoutManager( getContext() ) );


            incomedbRef.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    incomeList.clear();
                    for(DataSnapshot incomeSnapshop: dataSnapshot.getChildren())
                    {
                        IncomeData incomeData = incomeSnapshop.getValue( IncomeData.class );
                        incomeList.add( incomeData );
                    }
                    incomeListAdapter = new IncomeListAdapter( incomeList );

                    rvIncomeList.setAdapter(incomeListAdapter  );
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            } );


             return view;
        }

}
