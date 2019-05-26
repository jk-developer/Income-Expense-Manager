package com.example.jitendrakumar.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.fragment.AboutAppFragment;
import com.example.jitendrakumar.myapplication.fragment.AboutDeveloperFragment;
import com.example.jitendrakumar.myapplication.fragment.AddExpenseFragment;
import com.example.jitendrakumar.myapplication.fragment.AddIncomeFragment;
import com.example.jitendrakumar.myapplication.fragment.ExpenseListFragment;
import com.example.jitendrakumar.myapplication.fragment.HomePageFragment;
import com.example.jitendrakumar.myapplication.fragment.IncomeListFragment;
import com.example.jitendrakumar.myapplication.fragment.IncomeReportFragment;
import com.example.jitendrakumar.myapplication.fragment.LoginFragment;
import com.example.jitendrakumar.myapplication.fragment.SignupFragment;
import com.example.jitendrakumar.myapplication.models.ExpenseData;
import com.example.jitendrakumar.myapplication.models.IncomeData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout incomeLayout, expenseLayout;
    private Toolbar toolbar;
    private float ans = (float) 0.0;
   TextView tvHello, incomeTotal, expenseTotal;
    private CharSequence charSequence[] = {"Income", "Expense"};
    boolean[] Checked = new boolean[charSequence.length];

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ArrayList<IncomeData> incomeList;

    private String userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        showHome();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
      //  userid = firebaseUser.getUid();

        incomeLayout = findViewById( R.id.incomeLayout);
        expenseLayout = findViewById( R.id.expenseLayout );
        tvHello = findViewById(  R.id.tvHello);
        incomeTotal = findViewById( R.id.incomeTotal );
        expenseTotal = findViewById( R.id.expenseTotal );

        incomeTotal.setText( getAllIncome().toString() );
        expenseTotal.setText( getAllExpense().toString() );


        if(firebaseUser != null)
        {
            tvHello.setText( "Welcome  "+firebaseUser.getEmail() );
            tvHello.setVisibility( View.VISIBLE );

        }
        else
        {
            tvHello.setText( "You are not logged in or registered " );
            tvHello.setVisibility( View.VISIBLE );
        }




        incomeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseUser == null)
                {
                    Toast.makeText( MainActivity.this, "Sorry you are not logged in", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment mFrag = new AddIncomeFragment();
                    ft.replace(R.id.screen_area, mFrag);
                    ft.addToBackStack( null );
                    ft.commit();
                }

            }
        } );

        expenseLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseUser == null)
                {
                    Toast.makeText( MainActivity.this, "Sorry you are not logged in", Toast.LENGTH_SHORT ).show();
                }
                else {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment mFrag = new AddExpenseFragment();
                    ft.replace( R.id.screen_area, mFrag );
                    ft.addToBackStack( null );
                    ft.commit();
                }
            }
        } );



    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            if(fragment instanceof HomePageFragment)
            {
                super.onBackPressed();
             }
             else
            {
                showHome();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    private void showHome()
    {
        fragment = new HomePageFragment();
        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace( R.id.screen_area, fragment ).commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {

            case R.id.action_rate_us:
                Toast.makeText( MainActivity.this, "Rate us action clicked", Toast.LENGTH_SHORT ).show();
                return true;

            case R.id.action_sendfeedback:
                final AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
                builder.setMessage( "We would love to hear your feedback  or suggestions on how we can improve your experiance!" );
                builder.setTitle( "Feedback" );
                builder.setIcon( R.drawable.ic_mail );
                builder.setPositiveButton( "Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse("mailto:" );
                        Intent intent = new Intent( Intent.ACTION_SENDTO);
                        intent.setData(uri);
                        String[] to = {"jkgupta4398@gmail.com"};
                        intent.putExtra( Intent.EXTRA_EMAIL, to );
                        intent.putExtra( Intent.EXTRA_SUBJECT,"Income Expense Tracker Feedback " );
                        startActivity(intent);
                    }
                } );

                builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable( true );
                    }
                } );

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;

            case R.id.nav_logout:
                if(firebaseUser == null)
                {
                    Toast.makeText( MainActivity.this, "Sorry you are not logged in", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent( MainActivity.this, StartActivity.class );
                    intent.addFlags( intent.FLAG_ACTIVITY_CLEAR_TOP );
                    intent.addFlags( intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity( intent );
                    Toast.makeText( MainActivity.this, "Logged out successfully!!!" , Toast.LENGTH_SHORT).show();

                }

                return true;

            case R.id.action_reset:
                if(firebaseUser == null)
                {
                    Toast.makeText( MainActivity.this, "Sorry you are not logged in", Toast.LENGTH_SHORT ).show();
                }
                else {

                    Toast.makeText( MainActivity.this, "Reset is clicked", Toast.LENGTH_SHORT ).show();

                    for (int i = 0; i < charSequence.length; i++) {
                        Checked[i] = false;
                    }
                    final AlertDialog.Builder builderReset = new AlertDialog.Builder( MainActivity.this );
                    builderReset.setIcon( R.drawable.ic_reset );
                    builderReset.setTitle( "Choose " );
                    builderReset.setMultiChoiceItems( charSequence, new boolean[charSequence.length], new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            Checked[which] = isChecked;

                        }
                    } );

                    builderReset.setPositiveButton( "Reset", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Checked[0] == false && Checked[1] == false) {
                                Toast.makeText( MainActivity.this, "First Choose some items ", Toast.LENGTH_SHORT ).show();
                                builderReset.setCancelable( false );
                            } else {
                                if (Checked[0] == true) {
                                    deleteAllIncomeData();
                                }
                                if (Checked[1] == true) {
                                    deleteAllExpenseData();
                                }

                                getSupportFragmentManager().beginTransaction()
                                        .replace( R.id.screen_area, new HomePageFragment() )
                                        .addToBackStack( null )
                                        .commit();
                                toolbar.setTitle( "Income Expense Tracker" );

                                Toast.makeText( MainActivity.this, "Selected Items records are deleted!!!", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );

                    builderReset.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int j = 0; j < charSequence.length; j++)
                                Checked[j] = false;

                            builderReset.setCancelable( true );
                        }
                    } );

                    AlertDialog alertDialogReset = builderReset.create();
                    alertDialogReset.show();

                }
                return true;

            default:

                return super.onOptionsItemSelected( item );
        }

    }

    /// create a fragment
    Fragment fragment = null;


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new HomePageFragment();
        }
        else if (id == R.id.nav_login) {
            fragment = new LoginFragment();
            toolbar.setTitle( "Sign In" );
        }
        else if (id == R.id.nav_signup) {
            fragment = new SignupFragment();
            toolbar.setTitle( "Sign Up" );

        } else if (id == R.id.nav_about_app) {
            fragment = new AboutAppFragment();
            toolbar.setTitle( "About App" );

        } else if (id == R.id.nav_about) {
            fragment = new AboutDeveloperFragment();
            toolbar.setTitle( "About Developer" );
        }
        else if (id == R.id.nav_income_list) {
            if(firebaseUser == null)
            {
                Toast.makeText( MainActivity.this, "Sorry you are not logged in", Toast.LENGTH_SHORT ).show();
            }
            else
            {
                fragment = new IncomeListFragment();
                toolbar.setTitle( "Income List" );
            }

        }
        else if (id == R.id.nav_expense_list) {
            if(firebaseUser == null)
            {
                Toast.makeText( MainActivity.this, "Sorry you are not logged in", Toast.LENGTH_SHORT ).show();
            }
            else {
                fragment = new ExpenseListFragment();
                toolbar.setTitle( "Expense List" );
            }
        }
        else if (id == R.id.nav_income_report) {
            if(firebaseUser == null)
            {
                Toast.makeText( MainActivity.this, "Sorry you are not logged in", Toast.LENGTH_SHORT ).show();
            }
            else {
                startActivity( new Intent( MainActivity.this, IncomeBarChartActivity.class ) );
                toolbar.setTitle( "Income Report" );
            }
        }
        else if (id == R.id.nav_expense_report) {
            if(firebaseUser == null)
            {
                Toast.makeText( MainActivity.this, "Sorry you are not logged in", Toast.LENGTH_SHORT ).show();
            }
            else {
                startActivity( new Intent(MainActivity.this, ExpenseBarchartActivity.class ) );
                toolbar.setTitle( "Expense Report" );
            }
        }
        else if(id == R.id.nav_add_task){
            if(firebaseUser == null)
            {
                Toast.makeText( MainActivity.this, "Sorry you are not logged in", Toast.LENGTH_SHORT ).show();
            }
            else {
                Intent intent = new Intent( this, TaskListActivity.class );
                startActivity( intent );
            }
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace( R.id.screen_area, fragment ).commit();

        }

       /// given with navigation drawer activity

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    public void deleteAllIncomeData(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("income");
        ref.removeValue();
    }

    private void deleteAllExpenseData()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("expense");
        ref.removeValue();
    }

    private String getAllIncome()
    {

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("income");
        firebaseDatabase.child( firebaseUser.getUid() ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot income: dataSnapshot.getChildren())
                {
                    IncomeData incomeData = income.getValue( IncomeData.class );
                     ans = ans + Float.valueOf( incomeData.getIncomeAmount() );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        return String.valueOf( ans );
    }

    private String getAllExpense()
    {

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("expense");
        firebaseDatabase.child( firebaseUser.getUid() ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot expense: dataSnapshot.getChildren())
                {
                    ExpenseData expenseData = expense.getValue( ExpenseData.class );
                   // Toast.makeText( MainActivity.this, "expense"+ expenseData.getExpenseAmount(), Toast.LENGTH_SHORT).show();
                    ans = ans + Float.valueOf( expenseData.getExpenseAmount() );
                    Toast.makeText( MainActivity.this, "expense"+ String.valueOf( ans ), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        return String.valueOf( ans );
    }
}
