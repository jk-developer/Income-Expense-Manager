package com.example.jitendrakumar.myapplication.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.fragment.TimePickerFragment;
import com.example.jitendrakumar.myapplication.models.IncomeData;

import java.util.ArrayList;

public class IncomeListAdapter extends RecyclerView.Adapter<IncomeListAdapter.IncomeListViewHolder> {

    ArrayList<IncomeData> incomeDataArrayList;
   public IncomeListAdapter(ArrayList<IncomeData> list)
   {
            incomeDataArrayList = list;
   }

    @Override
    public IncomeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        View view = inflater.inflate( R.layout.single_income_item, parent, false );

        return new IncomeListViewHolder( view );
    }

    @Override
    public void onBindViewHolder(IncomeListViewHolder holder, int position) {
          IncomeData incomeData = incomeDataArrayList.get( position );
          holder.tvIncomeReportAmount.setText( incomeData.getIncomeAmount() );
        holder.tvIncomeReportTime.setText( incomeData.getIncomeTime());
        holder.tvIncomeReportDate.setText( incomeData.getIncomeDate());
        holder.tvIncomeReportType.setText( incomeData.getIncomeType());
        holder.tvIncomeReportDesc.setText( incomeData.getIncomeDesc());
    }

    @Override
    public int getItemCount() {
        return incomeDataArrayList.size();
    }

    public  class IncomeListViewHolder extends RecyclerView.ViewHolder{

       TextView tvIncomeReportType, tvIncomeReportAmount, tvIncomeReportDate, tvIncomeReportTime, tvIncomeReportDesc;


        public IncomeListViewHolder(View itemView) {
            super( itemView );
            tvIncomeReportAmount = itemView.findViewById( R.id.tvIncomeReportAmount );
            tvIncomeReportType = itemView.findViewById( R.id.tvIncomeReportType );
            tvIncomeReportDate = itemView.findViewById( R.id.tvIncomeReportDate );
            tvIncomeReportTime = itemView.findViewById( R.id.tvIncomeReportTime );
            tvIncomeReportDesc = itemView.findViewById( R.id.tvIncomeReportDesc );
        }
    }
}


