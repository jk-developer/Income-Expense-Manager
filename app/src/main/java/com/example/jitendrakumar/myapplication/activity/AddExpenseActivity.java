package com.example.jitendrakumar.myapplication.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.jitendrakumar.myapplication.R;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

   private EditText etExpenseAmount, etExpenseDescription;
   private TextView tvExpenseDate, tvExpenseTime, tvHintExpenseDate, tvExpenseType, tvExpenseHintType, tvExpenseHintTime;

   private Button btnExpenseSubmit;

    private CharSequence expense[] = {"Food", "Leisure","Transport", "Medicines", "House Rent", "Maintenace", "Clothes", "Travel","Health","Hobbies","Gifts","Household",
            "Groceries","Gadgets","Kids", "Loans", "Education","Holidays","Savings","Beauty","Sports","Mobile","Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_expense );

        getSupportActionBar().setTitle( "Add Expense" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );


            tvExpenseType = (TextView) findViewById( R.id.tvExpenseType );
            tvExpenseHintType  = (TextView) findViewById( R.id.tvExpenseHintType );
            etExpenseAmount = (EditText) findViewById( R.id.etExpenseAmount );
            etExpenseDescription = (EditText)findViewById( R.id.etExpenseDescription );
            tvExpenseDate = (TextView)findViewById( R.id.tvExpenseDate );
            tvExpenseTime = (TextView)findViewById( R.id.tvExpenseTime );
            btnExpenseSubmit = (Button)findViewById( R.id.btnExpenseSubmit );
            tvHintExpenseDate = (TextView)findViewById( R.id.tvHintExpenseDate );
            tvExpenseHintTime = (TextView)findViewById( R.id.tvExpenseHintTime );


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

            final DatePickerDialog datePickerDialog = new DatePickerDialog(this, AddExpenseActivity.this, year, month, day);
            final TimePickerDialog timePickerDialog = new TimePickerDialog( this, AddExpenseActivity.this, hour, minute, android.text.format.DateFormat.is24HourFormat(this) );

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
                    final AlertDialog.Builder builder = new AlertDialog.Builder( AddExpenseActivity.this);
                    builder.setTitle( "Select Expense Category" );
                    builder.setItems( expense, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvExpenseType.setText( expense[which].toString().trim());
                            Toast.makeText( AddExpenseActivity.this, ""+expense[which], Toast.LENGTH_SHORT ).show();
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
                                Toast.makeText(AddExpenseActivity.this, "Select some  expense category", Toast.LENGTH_SHORT ).show();
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
                    datePickerDialog.show();
                }
            } );

            tvExpenseHintTime.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timePickerDialog.show();
                }
            } );


        }

        public void addDataInExpenseDB() {
            btnExpenseSubmit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        populateSetDate(year, month+1, dayOfMonth );
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        populateSetTime(hourOfDay, minute);
    }

    public void populateSetTime(int hour, int minute) {
        if(minute<=9 ||minute==0)
            tvExpenseTime.setText(hour+":0"+minute);
        else
            tvExpenseTime.setText( hour+":"+minute );

    }

    public void populateSetDate(int year, int month, int day) {
        if(day<=9 && month <=9)
        {
            tvExpenseDate.setText("0"+day+"/"+"0"+month+"/"+year);
        }
        else if(day<=9 && month>9)
        {
            tvExpenseDate.setText("0"+day+"/"+month+"/"+year);
        }
        else if(day>9 && month<=9){
            tvExpenseDate.setText(day+"/"+"0"+month+"/"+year);
        }
        else{
            tvExpenseDate.setText(day+"/"+month+"/"+year);
        }
    }
}

