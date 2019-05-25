package com.example.jitendrakumar.myapplication.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.activity.IncomeBarChartActivity;
import com.example.jitendrakumar.myapplication.adapters.IncomeListAdapter;
import com.example.jitendrakumar.myapplication.models.IncomeData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class IncomeReportFragment extends Fragment {

     private Spinner spinnerIncome;
    private DatabaseReference incomedbRef;
    private ProgressDialog progressDialog;
    private ArrayList<IncomeData> incomeArrayList;
    private LinearLayout incomebwDatesId;

    private TextView tvIncomeType, tvIncomeTypeSelect;
    Button btnIncomeTypeReport;

    private CharSequence income[] = {"Regular Salary", "Buissness Profits", "Rental Income", "Savings", "Gifts", "Pocket Money", " Investments ", "Governmental grants", "Retirement Income",
            "Bonus", "Other"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_income_report, container, false );

        progressDialog = new ProgressDialog( getContext() );

        incomeArrayList = new ArrayList<>(  );
        incomedbRef = FirebaseDatabase.getInstance().getReference("income").child( "incomeType" );

        spinnerIncome = view.findViewById( R.id.spinnerIncome );
        incomebwDatesId = view.findViewById( R.id.incomebwDatesId);
        tvIncomeTypeSelect = view.findViewById( R.id.tvIncomeTypeSelect );
        tvIncomeType = view.findViewById( R.id.tvIncomeType );
        btnIncomeTypeReport = view.findViewById( R.id.btnIncomeTypeReport );

        // String selectedItem = spinnerIncome.getSelectedItem().toString().trim();

        spinnerIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString().trim();
                if(selectedItem.equals("Between two Dates".trim()))
                {
                    // do your stuff
                    Toast.makeText( getContext(), "dates is cliced ", Toast.LENGTH_SHORT ).show();


                }

                if(selectedItem.equals("Between two Months".trim()))
                {
                    // do your stuff
                    Toast.makeText( getContext(), "month is cliced ", Toast.LENGTH_SHORT ).show();
                 //

                }

                if(selectedItem.equals("Income Type".trim()))
                {
                    // do your stuff
                    Toast.makeText( getContext(), "income type is cliced ", Toast.LENGTH_SHORT ).show();
              /*      incomebwDatesId.setVisibility( View.VISIBLE );
                    String selectedType = tvIncomeType.getText().toString().trim();

                    btnIncomeTypeReport.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                         //   getIncomeDataTypewise();
                        }
                    } );

             */
              }
              if(selectedItem.equals("Bar Graph".trim()))
              {
                    // do your stuff
                    Toast.makeText( getContext(), "bar graph is cliced ", Toast.LENGTH_SHORT ).show();
                    Intent in = new Intent( getActivity(), IncomeBarChartActivity.class );
                    startActivity(in);

              }

            } // to close the onItemSelected

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }

         });

        return view;
    }
/*
    private  void getIncomeDataTypewise()
    {
        progressDialog.setMessage( "Fetching Income data..." );
        progressDialog.show();


        incomedbRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                incomeArrayList.clear();
                for (DataSnapshot incomeSnapshop : dataSnapshot.getChildren()) {
                    IncomeData incomeData = incomeSnapshop.getValue( IncomeData.class );
                    incomeArrayList.add( incomeData );
                }

                progressDialog.dismiss();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

*/

}
