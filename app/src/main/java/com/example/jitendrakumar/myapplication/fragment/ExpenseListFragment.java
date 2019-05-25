package com.example.jitendrakumar.myapplication.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.adapters.ExpenseListAdapter;
import com.example.jitendrakumar.myapplication.adapters.IncomeListAdapter;
import com.example.jitendrakumar.myapplication.models.ExpenseData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpenseListFragment extends Fragment {
    private RecyclerView rvExpenseList;
    private ArrayList<ExpenseData> expenseList;
    private DatabaseReference expensedbRef;
    private ExpenseListAdapter expenseListAdapter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_expense_list, container, false );
        rvExpenseList = view.findViewById( R.id.rvExpenseList );

        progressDialog = new ProgressDialog( getContext() );

        expenseList = new ArrayList<ExpenseData>(  );
        expensedbRef = FirebaseDatabase.getInstance().getReference("expense");
        rvExpenseList.setLayoutManager( new LinearLayoutManager( getContext() ) );

        progressDialog.setMessage( "Fetching Expense data..." );
        progressDialog.show();

        expensedbRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseList.clear();
                for(DataSnapshot expenseSnapshot: dataSnapshot.getChildren())
                {
                    ExpenseData expenseData = expenseSnapshot.getValue( ExpenseData.class );
                    expenseList.add( expenseData );
                }
                expenseListAdapter = new ExpenseListAdapter( expenseList, getContext() );
                progressDialog.dismiss();

                rvExpenseList.setAdapter(expenseListAdapter  );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


        return view;
    }

}
