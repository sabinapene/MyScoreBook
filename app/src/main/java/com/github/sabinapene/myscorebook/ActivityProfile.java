package com.github.sabinapene.myscorebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityProfile extends AppCompatActivity {

    //firebase authentification
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        findViewById(R.id.changePasswordTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( ActivityProfile.this, ActivityChangePassword.class));
            }
        });

        findViewById(R.id.changeEmailTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getApplicationContext(), ActivityChangeEmail.class));
            }
        });

        findViewById(R.id.signOutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ActivityProfile.this, ActivitySignIn.class));
                firebaseAuth.signOut();
            }

        });

    }

}
