package com.example.jitendrakumar.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jitendrakumar.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_login, container, false );

        etUsername = view.findViewById( R.id.etUsername );
        etPassword = view.findViewById( R.id.etPassword );
        btnLogin = view.findViewById( R.id.btnLogin );
        tvRegister = view.findViewById( R.id.tvRegister );

        firebaseAuth = FirebaseAuth.getInstance();
    /*    if(firebaseAuth.getCurrentUser() != null)
        {
            //start the profile activity
            finish();
            startActivity( new Intent( getApplicationContext(), ProfileActivity.class ) );

        }
*/
        progressDialog = new ProgressDialog(getContext());



        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        } );

        tvRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // finish();
              //  startActivity(new Intent( LoginActivity.this, MainActivity.class ) );
            }
        } );

        return view;
    }

    private void userLogin() {
        String email = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty( email )) {
            Toast.makeText( getContext(), "Please enter email", Toast.LENGTH_SHORT ).show();
            //stoping the funcction further
            return;
        }

        if (TextUtils.isEmpty( password )) {
            Toast.makeText( getContext(), "Please enter Password", Toast.LENGTH_SHORT ).show();
            return;
        }

        progressDialog.setMessage( "Signing in please wait..." );
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword( email, password ).addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment mFrag = new HomePageFragment();
                    ft.replace( R.id.screen_area, mFrag );
                    ft.commit();
                }
            }
        } );
    }

}
