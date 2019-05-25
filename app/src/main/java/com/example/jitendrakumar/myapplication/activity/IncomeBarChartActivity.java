package com.example.jitendrakumar.myapplication.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

// import com.github.mikephil.charting.components.Description;

public class IncomeBarChartActivity extends AppCompatActivity {

      BarChart barChart;
      private float monthlyIncomes[] = new float[12];
      public static final String TAG = "incomes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_income_bar_chart  );

        float value = (float) 100.0;
        for(int i=0 ; i<12; i++, value = (float) (value+500.0))
            monthlyIncomes[i]= (float) 0.0 +value;

            BarChart barChart = (BarChart) findViewById( R.id.incomebarChart );

            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add( new BarEntry( monthlyIncomes[0], 0 ) );
            entries.add( new BarEntry( monthlyIncomes[1], 1 ) );
            entries.add( new BarEntry( monthlyIncomes[2], 2 ) );
            entries.add( new BarEntry( monthlyIncomes[3], 3 ) );
            entries.add( new BarEntry( monthlyIncomes[4], 4 ) );
            entries.add( new BarEntry( monthlyIncomes[5], 5 ) );
            entries.add( new BarEntry( monthlyIncomes[6], 6 ) );
            entries.add( new BarEntry( monthlyIncomes[7], 7 ) );
            entries.add( new BarEntry( monthlyIncomes[8], 8 ) );
            entries.add( new BarEntry( monthlyIncomes[9], 9 ) );
            entries.add( new BarEntry( monthlyIncomes[10], 10 ) );
            entries.add( new BarEntry( monthlyIncomes[11], 11 ) );

            BarDataSet bardataset = new BarDataSet( entries, "Cells" );

            ArrayList<String> labels = new ArrayList<String>();
            labels.add( "Jan" );
            labels.add( "Feb" );
            labels.add( "Mar" );
            labels.add( "Apr" );
            labels.add( "May" );
            labels.add( "Jun" );
            labels.add( "Jul" );
            labels.add( "Aug" );
            labels.add( "Sep" );
            labels.add( "Oct" );
            labels.add( "Nov" );
            labels.add( "Dec" );

            BarData data = new BarData(labels, bardataset );
            barChart.setData( data ); // set the data and list of lables into chart

            barChart.setDescription("Income Report for the year 2018");  // set the description

            bardataset.setColors( ColorTemplate.COLORFUL_COLORS );

            barChart.animateY( 5000 );
        }

}
