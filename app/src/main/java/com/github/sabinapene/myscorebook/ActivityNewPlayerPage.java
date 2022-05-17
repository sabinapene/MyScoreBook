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
import com.github.sabinapene.myscorebook.Models.Player;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityNewPlayerPage extends AppCompatActivity {

    //firebase authentification
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private String playerName ="";
    private static String gameID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player_page);

        findViewById(R.id.createPlayerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }
    private void validateData() {
        //get data
        EditText playerEditText = findViewById(R.id.playerEditText);
        playerName = playerEditText.getText().toString().trim();

        //validate if not empty
        if(!TextUtils.isEmpty(playerName)){
            //valid data, proceed with firebase sign up
            addPlayerFirebase();
            Toast.makeText(this, "Adding player...", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Enter player name", Toast.LENGTH_SHORT).show();

        }
    }

    private void addPlayerFirebase() {

        //initialising firebase authentification
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        //db.getReference("players").child("Users").orderByChild("title").equalTo(gameID);

        //set up info to add to firebase
        Player player = new Player(gameID, playerName, 0);


        //add to firebase
        DatabaseReference reference = db.getReference("players");
        reference.push().setValue(player)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //game added successfully
                        Toast.makeText(ActivityNewPlayerPage.this, "Player created ", Toast.LENGTH_SHORT).show();

                        //open main activity
                        startActivity(new Intent(ActivityNewPlayerPage.this, ActivityScorePage.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //game added failure
                        Toast.makeText(ActivityNewPlayerPage.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public static void setGameID(String ID)
    {
        gameID = ID;
    }

}