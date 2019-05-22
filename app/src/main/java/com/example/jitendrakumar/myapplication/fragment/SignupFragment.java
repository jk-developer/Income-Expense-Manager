package com.example.jitendrakumar.myapplication.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;


public class SignupFragment extends Fragment {

    EditText etemail, etpassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    Button btnSignup;
    TextView tvLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_signup, container, false );

        inputLayoutEmail = (TextInputLayout) view.findViewById( R.id.input_layout_email );
        inputLayoutPassword = (TextInputLayout) view.findViewById( R.id.input_layout_password );

        etemail = (EditText) view.findViewById( R.id.email );
        etpassword = (EditText) view.findViewById( R.id.password );
        btnSignup = (Button) view.findViewById( R.id.btnSignup );
        tvLogin = (TextView) view.findViewById( R.id.tvLogin );

        progressDialog = new ProgressDialog( getContext() );
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

        progressDialog = new ProgressDialog( getContext() );
        // firebaseAuth = FirebaseAuth.getInstance();
        //    if(firebaseAuth.getCurrentUser() != null)
        {
            //start the profile activity
            //     finish();
            //   startActivity( new Intent( getApplicationContext(), ProfileActivity.class ) );

        }

        return view;
    }

    private void registerUser() {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();

        if (TextUtils.isEmpty( email )) {
            Toast.makeText( getContext(), "Please enter email", Toast.LENGTH_SHORT ).show();
            //stoping the funcction further
            return;
        }

        if (TextUtils.isEmpty( password )) {
            Toast.makeText( getContext(), "Please enter Password", Toast.LENGTH_SHORT ).show();
            return;
        }

        // if validates are ok
        // then we will show a progressbar
        progressDialog.setMessage( "Registering User..." );
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment mFrag = new HomePageFragment();
                    ft.replace(R.id.screen_area, mFrag);
                    ft.commit();


                }
                else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content),
                            task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

}




