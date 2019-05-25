package com.example.jitendrakumar.myapplication.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.activity.ExpenseItemActivity;
import com.example.jitendrakumar.myapplication.models.ExpenseData;

import java.util.ArrayList;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder>{

    ArrayList<ExpenseData> expenseDataArrayList;
    private Context expenseContext;
    private ExpenseData expenseData;
    public ExpenseListAdapter(ArrayList<ExpenseData> list, Context expenseContext)
    {
        expenseDataArrayList = list;
        this.expenseContext = expenseContext;
    }


    @Override
    public ExpenseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        View view = inflater.inflate( R.layout.single_expense_item, parent, false );

        return new ExpenseListViewHolder( view );
    }

    @Override
    public void onBindViewHolder(ExpenseListViewHolder holder, int position) {
        ExpenseData expenseData = expenseDataArrayList.get( position );
        holder.tvExpenseReportAmount.setText( expenseData.getExpenseAmount() );
        holder.tvExpenseReportTime.setText( expenseData.getExpenseTime());
        holder.tvExpenseReportDate.setText( expenseData.getExpenseDate());
        holder.tvExpenseReportType.setText( expenseData.getExpenseType());
        holder.tvExpenseReportDesc.setText( expenseData.getExpenseDesc());
    }

    @Override
    public int getItemCount() {
        return expenseDataArrayList.size();
    }

    public class ExpenseListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvExpenseReportType, tvExpenseReportAmount, tvExpenseReportDate, tvExpenseReportTime, tvExpenseReportDesc;
        public ExpenseListViewHolder(View itemView) {

            super( itemView );
            itemView.setOnClickListener(this);
            tvExpenseReportAmount = itemView.findViewById( R.id.tvExpenseReportAmount );
            tvExpenseReportType = itemView.findViewById( R.id.tvExpenseReportType );
            tvExpenseReportDate = itemView.findViewById( R.id.tvExpenseReportDate );
            tvExpenseReportTime = itemView.findViewById( R.id.tvExpenseReportTime );
            tvExpenseReportDesc = itemView.findViewById( R.id.tvExpenseReportDesc );
        }


        @Override
        public void onClick(View v) {
            expenseData = new ExpenseData(expenseDataArrayList.get(getAdapterPosition()).getExpenseId(), expenseDataArrayList.get(getAdapterPosition()).getExpenseType(), expenseDataArrayList.get( getAdapterPosition()).getExpenseAmount(), expenseDataArrayList.get( getAdapterPosition()).getExpenseDate(), expenseDataArrayList.get(getAdapterPosition()).getExpenseTime(), expenseDataArrayList.get( getAdapterPosition()).getExpenseDesc());
            // Toast.makeText( mContext, getPosition()+ incomeData.getInputType()+ incomeData.getInputAmount()+"is clicked", Toast.LENGTH_SHORT).show();
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation( (Activity) expenseContext );
            Intent i = new Intent( expenseContext, ExpenseItemActivity.class );
            i.putExtra( "amount",expenseData.getExpenseAmount());
            i.putExtra( "Date", expenseData.getExpenseDate() );
            i.putExtra( "Time", expenseData.getExpenseTime() );
            i.putExtra( "Type", expenseData.getExpenseType());
            i.putExtra( "incomeId", expenseData.getExpenseId() );
            i.putExtra( "incomeDesc", expenseData.getExpenseDesc() );
            expenseContext.startActivity( i, options.toBundle() );
        }
    }
}