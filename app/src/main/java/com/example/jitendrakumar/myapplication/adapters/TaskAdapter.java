package com.example.jitendrakumar.myapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.models.IncomeData;
import com.example.jitendrakumar.myapplication.models.TaskData;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    ArrayList<TaskData> taskArrayList;
    TaskData taskData;
    private Context taskContext;
    public TaskAdapter(ArrayList<TaskData> list , Context taskContext)
    {
        taskArrayList = list;
        this.taskContext = taskContext;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        View view = inflater.inflate( R.layout.single_task_layout, parent, false );

        return new TaskViewHolder( view );

    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskData taskData = taskArrayList.get( position );
        Toast.makeText(taskContext, "task data "+taskData.getTask(), Toast.LENGTH_SHORT).show();
        holder.tvTask.setText( taskData.getTask());
        holder.tvTaskDate.setText(taskData.getTaskDate() );
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    public  class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView tvTask, tvTaskDate;
        public TaskViewHolder(View itemView) {
            super( itemView );
            tvTask = itemView.findViewById( R.id.tvTask );
            tvTaskDate = itemView.findViewById( R.id.tvTaskDate );

        }
    }



}
