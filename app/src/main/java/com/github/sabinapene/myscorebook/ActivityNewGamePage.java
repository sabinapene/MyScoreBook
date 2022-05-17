package com.github.sabinapene.myscorebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.sabinapene.myscorebook.Models.Game;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityNewGamePage extends AppCompatActivity {
    //firebase authentification
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private String gameName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_page);


        findViewById(R.id.createGameButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }
    private void validateData() {
        //get data
        EditText gameEditText = findViewById(R.id.gameEditText);
        gameName = gameEditText.getText().toString().trim();

        //validate if not empty
        if(!TextUtils.isEmpty(gameName)){
            //valid data, proceed with firebase sign up
            addGameFirebase();
            Toast.makeText(this, "Adding game...", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Enter game name", Toast.LENGTH_SHORT).show();

        }
    }

    private void addGameFirebase() {


        //initialising firebase authentification
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();


        //set up info to add to firebase
        Game game = new Game(firebaseAuth.getUid(), gameName);

        //add to firebase
        DatabaseReference reference = db.getReference("games");
        reference.push().setValue(game)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //game added successfully
                        Toast.makeText(ActivityNewGamePage.this, "Game created "+firebaseAuth.getUid(), Toast.LENGTH_SHORT).show();

                        //open main activity
                        startActivity(new Intent(ActivityNewGamePage.this, MainActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //game added failure
                        Toast.makeText(ActivityNewGamePage.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });

        }

    }