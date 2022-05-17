package com.github.sabinapene.myscorebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.sabinapene.myscorebook.Models.Player;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityChangeGameName extends AppCompatActivity {


    private FirebaseDatabase db;
    public static String gameID;
    public static String gameName;
    public static String newGameName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_game_name);




        findViewById(R.id.changeGameNameBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialising firebase authentification
                db = FirebaseDatabase.getInstance();

                EditText editTextNewGameName = findViewById(R.id.changeGameNameNameEt);
                newGameName = editTextNewGameName.getText().toString().trim();

                //update in firebase
                db.getReference("games").child(gameID).child("name").setValue(newGameName).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //game updated successfully
                        Toast.makeText(getApplicationContext(), "Game updated", Toast.LENGTH_SHORT).show();


                        ActivityScorePage.setGameName(newGameName);
                        //open main activity

                        startActivity(new Intent(getApplicationContext(), ActivityScorePage.class));
                        finish();

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //failure
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                            }
                        });
            }

        });

    }

    public static void setGameID(String ID) {
        gameID = ID;
    }

    public static void setGameName(String name) {
        gameName = name;
    }


}