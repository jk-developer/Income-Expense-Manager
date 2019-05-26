package com.example.jitendrakumar.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.example.jitendrakumar.myapplication.fragment.HomePageFragment;
import com.example.jitendrakumar.myapplication.fragment.LoginFragment;
import com.example.jitendrakumar.myapplication.fragment.SignupFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    EditText etemail, etpassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    Button btnLogin;
    TextView tvRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start );

        inputLayoutEmail = (TextInputLayout) findViewById( R.id.input_layout_email );
        inputLayoutPassword = (TextInputLayout) findViewById( R.id.input_layout_password );

        etemail = (EditText) findViewById( R.id.etemail );
        etpassword = (EditText) findViewById( R.id.etpassword );
        btnLogin = (Button) findViewById( R.id.btnLogin );
        tvRegister = (TextView) findViewById( R.id.tvRegister);

        progressDialog = new ProgressDialog( StartActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();

        etpassword.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etemail.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

        etemail.setTextColor( Color.parseColor( "#00ff00" ) );
        etpassword.setTextColor( Color.parseColor( "#00ff00" ) );

        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        } );

        progressDialog = new ProgressDialog(StartActivity.this);
        // firebaseAuth = FirebaseAuth.getInstance();
        //    if(firebaseAuth.getCurrentUser() != null)
        {
            //start the profile activity
            //     finish();
            //   startActivity( new Intent( getApplicationContext(), ProfileActivity.class ) );

        }

        tvRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( StartActivity.this, MainActivity.class );
                intent.addFlags( intent.FLAG_ACTIVITY_CLEAR_TOP );
                intent.addFlags( intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity( intent );
                Toast.makeText( StartActivity.this, " clicked", Toast.LENGTH_SHORT ).show();
            }
        } );


    }

    private void userLogin() {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();

        if (TextUtils.isEmpty( email )) {
            Toast.makeText( StartActivity.this, "Please enter email", Toast.LENGTH_SHORT ).show();
            //stoping the funcction further
            return;
        }

        if (TextUtils.isEmpty( password )) {
            Toast.makeText(StartActivity.this, "Please enter Password", Toast.LENGTH_SHORT ).show();
            return;
        }

        progressDialog.setMessage( "Signing in please wait..." );
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword( email, password ).addOnCompleteListener( StartActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                String userId = firebaseAuth.getUid();
                if (task.isSuccessful()) {
                    startActivity( new Intent( StartActivity.this, MainActivity.class ) );
                }

            }
        } );
    }

}

