package com.example.jitendrakumar.myapplication.activity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.fragment.AboutAppFragment;
import com.example.jitendrakumar.myapplication.fragment.AboutDeveloperFragment;
import com.example.jitendrakumar.myapplication.fragment.HomePageFragment;
import com.example.jitendrakumar.myapplication.fragment.IncomeReportFragment;
import com.example.jitendrakumar.myapplication.fragment.LoginFragment;
import com.example.jitendrakumar.myapplication.fragment.SignupFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
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
                Toast.makeText( MainActivity.this, "Logged out successfully!!!" , Toast.LENGTH_SHORT).show();
                    return true;


            case R.id.action_reset:
                Toast.makeText( MainActivity.this, "Reset is clicked" , Toast.LENGTH_SHORT).show();
                return true;

            default:

                return super.onOptionsItemSelected( item );
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        /// create a fragment
        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new HomePageFragment();
        }
        else if (id == R.id.nav_login) {
            fragment = new LoginFragment();
        }
        else if (id == R.id.nav_signup) {
            fragment = new SignupFragment();

        } else if (id == R.id.nav_about_app) {
            fragment = new AboutAppFragment();

        } else if (id == R.id.nav_about) {
            fragment = new AboutDeveloperFragment();
        }
        else if (id == R.id.nav_income_report) {
            fragment = new IncomeReportFragment();
        }

        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace( R.id.screen_area, fragment );
            ft.commit();
        }

       /// given with navigation drawer activity

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
