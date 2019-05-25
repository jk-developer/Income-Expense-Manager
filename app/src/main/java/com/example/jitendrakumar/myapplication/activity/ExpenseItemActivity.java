package com.example.jitendrakumar.myapplication.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.models.ExpenseData;
import com.example.jitendrakumar.myapplication.models.IncomeData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ExpenseItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextInputLayout input_layout_value;
    EditText etValue, etIncomeDesc;
    TextView tvDate, tvHintDate, tvCategory, tvHintCategory, tvDelete, tvSave, tvTime, tvHintTime;
    private String incomeId, date, time, type, desc;
    private String id;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String amt;

    private DatabaseReference expensedbRef;

    private CharSequence expenseItems[] = {"Food", "Leisure","Transport", "Medicines", "House Rent", "Maintenace", "Clothes", "Travel","Health","Hobbies","Gifts","Household",
            "Groceries","Gadgets","Kids", "Loans", "Education","Holidays","Savings","Beauty","Sports","Mobile","Other"};


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_expense_item);

        initAnimation();

        input_layout_value = (TextInputLayout) findViewById( R.id.input_layout_value );
        tvDate = (TextView) findViewById( R.id.tvDate );
        tvCategory= (TextView) findViewById( R.id.tvCategory );
        tvHintDate = (TextView) findViewById( R.id.tvHintDate );
        tvHintCategory = (TextView) findViewById( R.id.tvHintCategory );
        tvDelete= (TextView) findViewById( R.id.tvDelete );
        tvSave = (TextView) findViewById( R.id.tvSave );
        tvTime = (TextView) findViewById( R.id.tvTime );
        tvHintTime = (TextView) findViewById( R.id.tvHintTime );
        etValue = (EditText) findViewById( R.id.etValue );
        etIncomeDesc = (EditText)findViewById( R.id.etIncomeDesc );

        Calendar c = Calendar.getInstance();
        int incyear = c.get( Calendar.YEAR );
        int incmonth = c.get( Calendar.MONTH);
        int incday = c.get( Calendar.DAY_OF_MONTH );

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, ExpenseItemActivity.this, incyear, incmonth, incday);

        Calendar cTime = Calendar.getInstance();
        int inchour = cTime.get( Calendar.HOUR_OF_DAY );
        int incminute = cTime.get( Calendar.MINUTE );
        final TimePickerDialog timePickerDialog = new TimePickerDialog( this,ExpenseItemActivity.this, inchour, incminute, android.text.format.DateFormat.is24HourFormat(this) );

        getSupportActionBar().setTitle( "Edit Income" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //The key argument here must match that used in the other activity
              amt = extras.getString( "amount");
              date = extras.getString( "Date" );
              type = extras.getString( "Type" );
              time = extras.getString( "Time" );
              id = extras.getString( "incomeId" );
              desc = extras.getString( "incomeDesc" );
              Toast.makeText( this, "income id : "+id, Toast.LENGTH_SHORT ).show();
        }

        tvDate.setText(date);
        etValue.setText(amt);
        tvTime.setText( time );
        tvCategory.setText( type );
        etIncomeDesc.setText( desc );
       // etValue.setSelection(String.valueOf( amt ).toString().length());

        tvDate.setHintTextColor( getResources().getColor(R.color.colorPrimaryDark) );
        tvCategory.setHintTextColor( getResources().getColor(R.color.colorPrimaryDark) );

        tvDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder item_builder = new AlertDialog.Builder(ExpenseItemActivity.this);
                item_builder.setTitle( "Delete this Record?" );
                item_builder.setIcon( R.drawable.ic_reset);
                item_builder.setPositiveButton( "DELETE RECORD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        expensedbRef = FirebaseDatabase.getInstance().getReference("expense").child( id);
                        expensedbRef.removeValue();

                        Intent i = new Intent( ExpenseItemActivity.this, ExpenseItemActivity.class );
                        startActivity( i );
                      //  Toast.makeText( IncomeItemsActivity.this, "this income is deleted"+id , Toast.LENGTH_SHORT).show();
                        }
                } );
                item_builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item_builder.setCancelable( true );
                    }
                } );
                AlertDialog alertDialog = item_builder.create();
                alertDialog.show();

            }
        } );


        tvSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder save_builder = new AlertDialog.Builder(ExpenseItemActivity.this);
                save_builder.setTitle( "Finally save the changes ?" );

                save_builder.setPositiveButton( "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newDate = tvDate.getText().toString().trim();
                       String newTime = tvTime.getText().toString().trim();
                       String newAmount = etValue.getText().toString().trim();
                       String newDesc = etIncomeDesc.getText().toString().trim();
                       String newExpenseType = tvCategory.getText().toString().trim();

                        ExpenseData incomeData = new ExpenseData( id, newExpenseType, newAmount, newDate, newTime, newDesc );
                        expensedbRef = FirebaseDatabase.getInstance().getReference("expense").child( id);
                        expensedbRef.setValue( incomeData );

                    }
                } );
                save_builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save_builder.setCancelable( true );
                    }
                } );
                AlertDialog alertDialog = save_builder.create();
                alertDialog.show();

            }
        } );

        tvHintDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
        }
        });

        tvHintTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              timePickerDialog.show();
            }
        } );

        tvHintCategory.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder income_builder = new AlertDialog.Builder(ExpenseItemActivity.this);
                income_builder.setTitle( "Select Expense Category" );
               income_builder.setItems( expenseItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvCategory.setText( expenseItems[which].toString());
                        Toast.makeText( ExpenseItemActivity.this, ""+expenseItems[which], Toast.LENGTH_SHORT ).show();
                    }
                } );

               income_builder.setNegativeButton( "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        income_builder.setCancelable( true );
                    }
                } );

                income_builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        income_builder.setCancelable( true );
                    }
                } );
                AlertDialog alertDialog = income_builder.create();
                alertDialog.show();
            }
        } );

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        populateSetDate(year, month+1, dayOfMonth );
    }

    public void populateSetDate(int year, int month, int day) {
        if(day<=9 && month <=9)
        {
            tvDate.setText("0"+day+"/"+"0"+month+"/"+year);
        }
        else if(day<=9 && month>9)
        {
            tvDate.setText("0"+day+"/"+month+"/"+year);
        }
        else if(day>9 && month<=9){
            tvDate.setText(day+"/"+"0"+month+"/"+year);
        }
        else{
            tvDate.setText(day+"/"+month+"/"+year);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        populateSetTime(hourOfDay, minute);
    }

    public void populateSetTime(int hour, int minute) {
        if(minute<=9 ||minute==0)
            tvTime.setText(hour+":0"+minute);
        else
            tvTime.setText( hour+":"+minute );

    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;    }

    public void initAnimation(){
        Slide enterTransition = new Slide( );
        enterTransition.setSlideEdge( Gravity.RIGHT);
        enterTransition.setInterpolator( new AnticipateOvershootInterpolator(  ));
        enterTransition.setDuration( 1000 );
        getWindow().setEnterTransition( enterTransition );


    }
}
