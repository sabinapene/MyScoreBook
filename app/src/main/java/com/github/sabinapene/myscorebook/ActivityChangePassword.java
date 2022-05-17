package com.github.sabinapene.myscorebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.FirebaseDatabase;

public class ActivityChangePassword extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        findViewById(R.id.changePasswordBtn).setOnClickListener(new View.OnClickListener() {
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
        EditText editTextNewPassword = findViewById(R.id.changeNewPasswordEt);
        String newPassword = editTextNewPassword.getText().toString().trim();

        EditText editTextOldPassword = findViewById(R.id.changeOldPasswordEt);
        String oldPassword = editTextOldPassword.getText().toString().trim();

        if(TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(oldPassword)){

            Toast.makeText(getApplicationContext(), "Insert password", Toast.LENGTH_SHORT).show();

        }
        else {
            //valid data, proceed with firebase sign up
            firebaseLogin(newPassword, oldPassword);
        }
    }

    private void firebaseLogin(String newPassword, String oldPassword) {

        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), oldPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult){


                        firebaseChangePassword(newPassword);


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

    private void firebaseChangePassword(String newPassword){
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_SHORT).show();

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