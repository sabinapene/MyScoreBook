package com.github.sabinapene.myscorebook;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityChangeEmail extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);


        findViewById(R.id.changeEmaildBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initialising firebase authentification
                firebaseAuth = FirebaseAuth.getInstance();
                user = FirebaseAuth.getInstance().getCurrentUser();

                validateData();

            }

        });

    }

    private void validateData() {
        //get data
        EditText editTextPassword = findViewById(R.id.passwordEt);
        String password = editTextPassword.getText().toString().trim();

        EditText editTextOldEmail = findViewById(R.id.changeEmailEt);
        String email = editTextOldEmail.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format

            Toast.makeText(getApplicationContext(), "Invalid Email Format", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(password)){

            Toast.makeText(getApplicationContext(), "Insert password", Toast.LENGTH_SHORT).show();

        }
        else {
            //valid data, proceed with firebase sign up
            firebaseLogin(email, password);
        }
    }

    private void firebaseLogin(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult){


                        firebaseChangeEmail(email);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //sign up failed
                        Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void firebaseChangeEmail(String email){
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Email changed", Toast.LENGTH_SHORT).show();

                            //open main activity
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}