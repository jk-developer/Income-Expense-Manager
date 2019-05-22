package com.example.jitendrakumar.myapplication.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.models.IncomeData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddIncomeFragment extends Fragment {

    Pattern pattern;
    Matcher matcher;
    final String DATE_PATTERN = "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

    public static final String TAG = "res";
    private DatabaseReference incomeDatabaseReference;


    // TextView tvIncomeDate;
    EditText etIncomeAmount, etIncomeDescription;
    TextView tvIncomeDate, tvIncomeTime, tvIncomeHintDate, tvIncomeHintTime, tvIncomeType, tvIncomeInput;
    Button btnIncomeSubmit;

    private CharSequence income[] = {"Regular Salary", "Buissness Profits", "Rental Income", "Savings", "Gifts", "Pocket Money", " Investments ", "Governmental grants", "Retirement Income",
            "Bonus", "Other"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_add_income, container, false );

        tvIncomeType = (TextView) view.findViewById( R.id.tvIncomeType );
        tvIncomeInput = (TextView) view.findViewById( R.id.tvIncomeInput );
        etIncomeAmount = (EditText) view.findViewById( R.id.etIncomeAmount );
        etIncomeDescription = (EditText) view.findViewById( R.id.etIncomeDescription );
        tvIncomeDate = (TextView) view.findViewById( R.id.tvIncomeDate );
        tvIncomeTime = (TextView) view.findViewById( R.id.tvIncomeTime );
        btnIncomeSubmit = (Button) view.findViewById( R.id.btnIncomeSubmit );
        tvIncomeHintDate = (TextView) view.findViewById( R.id.tvHintIncomeDate );
        tvIncomeHintTime = (TextView) view.findViewById( R.id.tvExpenseHintTime );
        tvIncomeHintDate = (TextView) view.findViewById( R.id.tvHintIncomeDate );
        tvIncomeHintTime = (TextView) view.findViewById( R.id.tvIncomeHintTime );

        incomeDatabaseReference = FirebaseDatabase.getInstance().getReference("income");

        Calendar cal = Calendar.getInstance();
        int year = cal.get( Calendar.YEAR );
        int month = cal.get( Calendar.MONTH );
        int day = cal.get( Calendar.DAY_OF_MONTH );
        int hour = cal.get( Calendar.HOUR_OF_DAY );
        int minute = cal.get( Calendar.MINUTE );
        String Date = "";
        if ((month + 1) <= 9 && day <= 9) {
            Date = "0" + day + "/0" + (month + 1) + "/" + year;
        }
        if ((month + 1) <= 9 && day > 9) {
            Date = day + "/0" + (month + 1) + "/" + year;
        }
        if ((month + 1) > 9 && day <= 9) {
            Date = "0" + day + "/" + (month + 1) + "/" + year;
        } else {
            Date = day + "/" + (month + 1) + "/" + year;
        }

        tvIncomeDate.setText( Date );
        tvIncomeTime.setText( hour + ":" + minute );

        tvIncomeType.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                builder.setTitle( "Select Expense Category" );
                builder.setItems( income, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvIncomeInput.setText( income[which].toString() );
                    }
                } );

                builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable( true );
                    }
                } );

                builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == -1) {

                        } else {
                            tvIncomeInput.setText( income[which].toString() );

                        }


                    }
                } );
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        } );

        tvIncomeType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvIncomeInput.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etIncomeAmount.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etIncomeDescription.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvIncomeDate.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvIncomeTime.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

        tvIncomeDate.setTextColor( Color.parseColor( "#00ff00" ) );
        etIncomeAmount.setTextColor( Color.parseColor( "#00ff00" ) );
        tvIncomeType.setTextColor( Color.parseColor( "#00ff00" ) );
        tvIncomeTime.setTextColor( Color.parseColor( "#00ff00" ) );
        tvIncomeInput.setTextColor( Color.parseColor( "#00ff00" ) );

        addDataInIncomeDB();

        tvIncomeHintTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment( tvIncomeTime );
                newFragment.show( getFragmentManager(), "TimePicker" );


            }
        } );

        tvIncomeHintDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvIncomeDate );
                newFragment.show( getFragmentManager(), "DatePicker" );

            }
        } );

        return view;
    }

    public void addDataInIncomeDB() {
        btnIncomeSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addIncomeData();

            }
        } );

    }

    private void addIncomeData()
    {
        String amount = String.valueOf(etIncomeAmount.getText().toString().trim());
        String date = tvIncomeDate.getText().toString().trim();
        String time = tvIncomeTime.getText().toString().trim();
        String incomeType = tvIncomeType.getText().toString().trim();
        String description = etIncomeDescription.getText().toString().trim();

        if(TextUtils.isEmpty( amount ))
        {
            Toast.makeText( getContext(), "please enter the amount" , Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty( date ))
        {
            Toast.makeText( getContext(), "please enter the amount" , Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty( time ))
        {
            Toast.makeText( getContext(), "please enter the amount" , Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty( incomeType ))
        {
            Toast.makeText( getContext(), "please enter the amount" , Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty( description ))
        {
            Toast.makeText( getContext(), "please enter the amount" , Toast.LENGTH_SHORT).show();
        }

        // now ok so save data

        String incomeId = incomeDatabaseReference.push().getKey();
        IncomeData incomeData = new IncomeData(incomeId, incomeType, amount, date, time, description );
        incomeDatabaseReference.child(incomeId).setValue( incomeData );
        Toast.makeText( getContext(), "Income Data saved successfully..." , Toast.LENGTH_SHORT).show();

    }

}
