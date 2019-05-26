package com.example.jitendrakumar.myapplication.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.adapters.TaskAdapter;
import com.example.jitendrakumar.myapplication.models.TaskData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class TaskListActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private RecyclerView rvTaskList;
    private ArrayList<TaskData> taskList;
    private DatabaseReference taskDbRef;
    private TaskAdapter taskAdapter;
    private ProgressDialog progressDialog;
    TextView tvTaskDate, tvHintTaskDate;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    FirebaseAuth.AuthStateListener mAuthListener;

    EditText etTask;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_task_list );



        tvTaskDate = findViewById( R.id.tvTaskDate );
        tvHintTaskDate = findViewById( R.id.tvHintTaskDate );
        etTask = findViewById( R.id.etTask );
        btnAdd = findViewById( R.id.btnAdd );
        rvTaskList = findViewById( R.id.rvTaskList );

        progressDialog = new ProgressDialog(TaskListActivity.this);


        Calendar c = Calendar.getInstance();
        int incyear = c.get( Calendar.YEAR );
        int incmonth = c.get( Calendar.MONTH);
        int incday = c.get( Calendar.DAY_OF_MONTH );

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, (DatePickerDialog.OnDateSetListener) TaskListActivity.this, incyear, incmonth, incday);

        getSupportActionBar().setTitle( "Tasks" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        taskList = new ArrayList<>(  );
        taskDbRef = FirebaseDatabase.getInstance().getReference("task");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        showTaskList();

        rvTaskList.setLayoutManager( new LinearLayoutManager(TaskListActivity.this) );

        tvHintTaskDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        } );

        btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskData();
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
            tvTaskDate.setText("0"+day+"/"+"0"+month+"/"+year);
        }
        else if(day<=9 && month>9)
        {
            tvTaskDate.setText("0"+day+"/"+month+"/"+year);
        }
        else if(day>9 && month<=9){
            tvTaskDate.setText(day+"/"+"0"+month+"/"+year);
        }
        else{
            tvTaskDate.setText(day+"/"+month+"/"+year);
        }
    }

    private void addTaskData()
    {
        String task = etTask.getText().toString().trim();
        String taskDate = tvTaskDate.getText().toString();

        String taskId = taskDbRef.push().getKey();

        TaskData taskData = new TaskData( task, taskId, taskDate );
        taskDbRef.child( firebaseUser.getUid()).child(taskId).setValue( taskData);
        Toast.makeText( this, "Income Data saved successfully..." , Toast.LENGTH_SHORT).show();

        showTaskList();

    }

    private void showTaskList()
    {
        progressDialog.setMessage( "Fetching Income data..." );
        progressDialog.show();

        taskDbRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                taskList.clear();
                for(DataSnapshot incomeSnapshop: dataSnapshot.getChildren())
                {
                    TaskData taskData = incomeSnapshop.getValue( TaskData.class );
                    taskList.add(taskData);
                }
                taskAdapter = new TaskAdapter( taskList, TaskListActivity.this);
                progressDialog.dismiss();
                rvTaskList.setAdapter(taskAdapter);
                Toast.makeText( TaskListActivity.this, "task size "+taskList.size(), Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

}
