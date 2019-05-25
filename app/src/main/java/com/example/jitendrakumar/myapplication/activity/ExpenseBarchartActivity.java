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

public class ExpenseBarchartActivity extends AppCompatActivity {

    BarChart barChart;

    private float monthlyExpenses[] = new float[12];
    public static final String TAG = "incomes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_expense_bar_chart );
        barChart = (BarChart) findViewById(R.id.expensebarChart);

        float val = (float) 500.0;

        for(int i=0;i<12;i++, val = (float) (val+509.0))
            monthlyExpenses[i]= (float) 0.0+val;


        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(monthlyExpenses[0], 0));
        entries.add(new BarEntry(monthlyExpenses[1], 1));
        entries.add(new BarEntry(monthlyExpenses[2], 2));
        entries.add(new BarEntry(monthlyExpenses[3], 3));
        entries.add(new BarEntry(monthlyExpenses[4], 4));
        entries.add(new BarEntry(monthlyExpenses[5], 5));
        entries.add(new BarEntry(monthlyExpenses[6], 6));
        entries.add(new BarEntry(monthlyExpenses[7], 7));
        entries.add(new BarEntry(monthlyExpenses[8], 8));
        entries.add(new BarEntry(monthlyExpenses[9], 9));
        entries.add(new BarEntry(monthlyExpenses[10], 10));
        entries.add(new BarEntry(monthlyExpenses[11], 11));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");
        labels.add("Jul");
        labels.add("Aug");
        labels.add("Sep");
        labels.add("Oct");
        labels.add("Nov");
        labels.add("Dec");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Expense Report for the year 2018");  // set the description

        bardataset.setColors( ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);


    }
}
