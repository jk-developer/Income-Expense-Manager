package com.example.jitendrakumar.myapplication.session;

import android.app.Application;
import android.content.Intent;

import com.example.jitendrakumar.myapplication.activity.IncomeItemActivity;
import com.example.jitendrakumar.myapplication.activity.MainActivity;
import com.example.jitendrakumar.myapplication.activity.StartActivity;
import com.example.jitendrakumar.myapplication.fragment.AboutDeveloperFragment;
import com.example.jitendrakumar.myapplication.fragment.HomePageFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firbaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firbaseAuth.getCurrentUser();

        if(firebaseUser == null)
        {
            Intent intent = new Intent( Home.this, StartActivity.class);
            intent.addFlags( intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
        }

    }
}
