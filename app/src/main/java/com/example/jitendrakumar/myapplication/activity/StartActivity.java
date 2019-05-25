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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    EditText etemail, etpassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    Button btnSignup;
    TextView tvLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start );

        inputLayoutEmail = (TextInputLayout) findViewById( R.id.input_layout_email );
        inputLayoutPassword = (TextInputLayout) findViewById( R.id.input_layout_password );

        etemail = (EditText) findViewById( R.id.email );
        etpassword = (EditText) findViewById( R.id.password );
        btnSignup = (Button) findViewById( R.id.btnSignup );
        tvLogin = (TextView) findViewById( R.id.tvLogin );

        progressDialog = new ProgressDialog( StartActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();

        // etemail.addTextChangedListener( new MyTextWatcher( etemail ) );
        //  etpassword.addTextChangedListener( new MyTextWatcher( etpassword ) );

        etpassword.setHintTextColor( getResources().getColor( R.color.colorTexts ) );
        etemail.setHintTextColor( getResources().getColor( R.color.colorTexts ) );

        etemail.setTextColor( Color.parseColor( "#00ff00" ) );
        etpassword.setTextColor( Color.parseColor( "#00ff00" ) );

        btnSignup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
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

        tvLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment mFrag = new LoginFragment();
                ft.replace(R.id.screen_area, mFrag);
                ft.addToBackStack( null );
                ft.commit();
            }
        } );


    }

    private void registerUser() {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();

        if (TextUtils.isEmpty( email )) {
            Toast.makeText( this, "Please enter email", Toast.LENGTH_SHORT ).show();
            //stoping the funcction further
            return;
        }

        if (TextUtils.isEmpty( password )) {
            Toast.makeText( this, "Please enter Password", Toast.LENGTH_SHORT ).show();
            return;
        }

        // if validates are ok
        // then we will show a progressbar
        progressDialog.setMessage( "Registering User..." );
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( StartActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent( StartActivity.this, MainActivity.class );
                    startActivity( intent );

                }
                else {
                    Toast.makeText( StartActivity.this, "Something went wrong ? try again", Toast.LENGTH_SHORT ).show();
                }

            }
        });
    }

}

