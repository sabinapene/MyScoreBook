package com.github.sabinapene.myscorebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivitySignIn extends AppCompatActivity {

    private String email="", password="";
    private FirebaseAuth firebaseAuth;

    private String currentUserID="";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //initialise firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.createAccountView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getApplicationContext(), ActivitySignUp.class));
            }
        });

        findViewById(R.id.signInBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }

        });

    }

    private void validateData() {
        //get data
        EditText editTextEmail = findViewById(R.id.emailEt);
        email = editTextEmail.getText().toString().trim();
        EditText editTextPassword = findViewById(R.id.passwordEt);
        password = editTextPassword.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format

            Toast.makeText(ActivitySignIn.this, "Invalid Email Format", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(password)){
            //no password entered

            Toast.makeText(ActivitySignIn.this, "Enter Password", Toast.LENGTH_SHORT).show();

        }
        else {
            //valid data, proceed with firebase sign up
            firebaseLogin();
        }
    }

    private void firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult){
                        //sign up succeeded
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        Toast.makeText(ActivitySignIn.this, "Signed in", Toast.LENGTH_SHORT).show();

                        //open main activity
                        startActivity(new Intent(ActivitySignIn.this, MainActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //sign up failed
                        Toast.makeText(ActivitySignIn.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}