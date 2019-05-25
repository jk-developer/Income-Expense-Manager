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
import com.example.jitendrakumar.myapplication.models.ExpenseData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddExpenseFragment extends Fragment {

   private DatabaseReference expenseDatabaseReference;
   private FirebaseAuth firebaseAuth;
   private FirebaseUser firebaseUser;

    EditText etExpenseAmount, etExpenseDescription;
    TextView tvExpenseDate, tvExpenseTime, tvHintExpenseDate, tvExpenseType, tvExpenseHintType, tvExpenseHintTime;

    Button btnExpenseSubmit;
    private int eyear, emonth, eday, ehour, eminute;

    private CharSequence expense[] = {"Food", "Leisure","Transport", "Medicines", "House Rent", "Maintenace", "Clothes", "Travel","Health","Hobbies","Gifts","Household",
    "Groceries","Gadgets","Kids", "Loans", "Education","Holidays","Savings","Beauty","Sports","Mobile","Other"};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_add_expense, container, false );

        tvExpenseType = (TextView) view.findViewById( R.id.tvExpenseType );
        tvExpenseHintType  = (TextView) view.findViewById( R.id.tvExpenseHintType );
        etExpenseAmount = (EditText) view.findViewById( R.id.etExpenseAmount );
        etExpenseDescription = (EditText)view.findViewById( R.id.etExpenseDescription );
        tvExpenseDate = (TextView) view.findViewById( R.id.tvExpenseDate );
        tvExpenseTime = (TextView) view.findViewById( R.id.tvExpenseTime );
        btnExpenseSubmit = (Button) view.findViewById( R.id.btnExpenseSubmit );
        tvHintExpenseDate = (TextView) view.findViewById( R.id.tvHintExpenseDate );
        tvExpenseHintTime = (TextView) view.findViewById( R.id.tvExpenseHintTime );

        expenseDatabaseReference = FirebaseDatabase.getInstance().getReference("expense");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Calendar c = Calendar.getInstance();
        int year = c.get( Calendar.YEAR );
        int month = c.get( Calendar.MONTH);
        int day = c.get( Calendar.DAY_OF_MONTH );
        int hour = c.get( Calendar.HOUR_OF_DAY );
        int minute = c.get( Calendar.MINUTE );
        String Date = "";
        if((month+1)<=9 && day<=9)
        {
            Date = "0"+day +"/0"+(month+1) +"/"+year ;
        }
        if((month+1)<=9 && day>9){
            Date = day +"/0"+(month+1)+"/"+year ;
        }
        if((month+1)>9 && day<=9){
            Date = "0"+day +"/"+(month+1) +"/"+year ;
        }
        else {
            Date = day +"/"+(month+1) +"/"+year ;
        }

        tvExpenseDate.setText(Date);
        tvExpenseTime.setText( hour+":"+minute );

        tvExpenseType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseHintType.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etExpenseAmount.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseDate.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        tvExpenseTime.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

        etExpenseAmount.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseDate.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseType.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseHintType.setTextColor( Color.parseColor( "#00ff00" ) );
        tvExpenseTime.setTextColor( Color.parseColor( "#00ff00" ) );

        addDataInExpenseDB();

        tvExpenseHintType.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle( "Select Expense Category" );
                builder.setItems( expense, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvExpenseType.setText( expense[which].toString().trim());
                       Toast.makeText( getContext(), ""+expense[which], Toast.LENGTH_SHORT ).show();
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
                        if(which==-1)
                        {
                            Toast.makeText( getContext(), "Select some  expense category", Toast.LENGTH_SHORT ).show();
                          //  tvExpenseType.setError( "Select some expense category!!!" );
                        }else {
                            tvExpenseType.setText( expense[which].toString());

                        }


                    }
                } );
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        } );

        tvHintExpenseDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment( tvExpenseDate );
                newFragment.show( getFragmentManager(), "DatePicker" );
            }
        } );

        tvExpenseHintTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment( tvExpenseTime );
                newFragment.show( getFragmentManager(), "TimePicker" );
            }
        } );


        return view;
    }

    public void addDataInExpenseDB() {
        btnExpenseSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              addExpenseData();
            }

        } );


    }

    private void addExpenseData()
    {

            String amount = String.valueOf(etExpenseAmount.getText().toString().trim());
            String date = tvExpenseDate.getText().toString().trim();
            String time = tvExpenseTime.getText().toString().trim();
            String expenseType = tvExpenseType.getText().toString().trim();
            String description = etExpenseDescription.getText().toString().trim();

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
            if(TextUtils.isEmpty( expenseType))
            {
                Toast.makeText( getContext(), "please enter the amount" , Toast.LENGTH_SHORT).show();
            }
            if(TextUtils.isEmpty( description ))
            {
                Toast.makeText( getContext(), "please enter the amount" , Toast.LENGTH_SHORT).show();
            }

            // now ok so save data

            String expenseId = expenseDatabaseReference.push().getKey();
            ExpenseData incomeData = new ExpenseData(expenseId, expenseType, amount, date, time, description );
            expenseDatabaseReference.child( firebaseUser.getUid() ).child(expenseId).setValue( incomeData );
            Toast.makeText( getContext(), "Expense Data saved successfully..." , Toast.LENGTH_SHORT).show();

        }

    }


