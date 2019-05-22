package com.example.jitendrakumar.myapplication.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.jitendrakumar.myapplication.R;


public class AboutDeveloperFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_about_developer, container, false );

        ImageView ivFacebook, ivMail;

           // initAnimation();

            ivFacebook = (ImageView) view.findViewById( R.id.ivFacebook );
            ivMail = (ImageView) view.findViewById( R.id.ivMail );

            ivFacebook.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://m.facebook.com/profile.php";
                    Uri uri = Uri.parse( url );
                    try {
                        Intent i = new Intent( Intent.ACTION_VIEW, uri );
                        startActivity( i );
                    }catch (ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                }
            } );

            ivMail.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("mailto:" );
                    Intent intent = new Intent( Intent.ACTION_SENDTO);
                    intent.setData(uri);
                    String[] to = {"jkgupta4398@gmail.com"};
                    intent.putExtra( Intent.EXTRA_EMAIL, to );
                    startActivity(intent);
                }
            } );

           return view;
        }
/*
        @Override
        public boolean onSupportNavigateUp() {
            finishAfterTransition();
            return true;    }

        public void initAnimation(){
            Explode explode = new Explode( );
            explode.setDuration( 800 );
            getTargetFragment().setEnterTransition( explode );

        }


*/

}
