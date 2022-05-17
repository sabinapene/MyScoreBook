package com.github.sabinapene.myscorebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sabinapene.myscorebook.Models.Game;
import com.github.sabinapene.myscorebook.Models.Player;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityScorePage extends AppCompatActivity {

    static String staticGameId = "-1";
    static String gameName = "";
    static String gameID="";
    static int tempScore=0;
    RecyclerView recyclerView;

    //firebase authentification
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Player> currentPlayers = new ArrayList<Player>();

    DatabaseReference reference;
    PlayerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page2);
        recyclerView = findViewById(R.id.rv);

        //initialising firebase authentification
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("players");
        players.clear();

        retrieveData();

        TextView textView = findViewById(R.id.gametextView);
        textView.setText(gameName);

        findViewById(R.id.playerfloatingActionButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Floating Act Btn Action goes here
                    Intent intent = new Intent(getApplicationContext(), ActivityNewPlayerPage.class);
                    v.getContext().startActivity(intent);
                }});


        findViewById(R.id.calculatebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //iterate through views and players, they have the same count and order
                for (int i = 0; i < recyclerView.getChildCount(); i++)
                {
                    //recyclerView.getChildAt(i);

                    Player player = currentPlayers.get(i);
                    String stringScore = ((TextView) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.playeredittext)).getText().toString();
                    int currentChildScore = 0;

                    if(!stringScore.equals("")){currentChildScore = Integer.parseInt( stringScore );}

                    int newscore = currentChildScore+ player.getScore();

                    //update in  firebase
                    reference.child(player.getID()).child("score").setValue(newscore).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //score modified successfully
                            Toast.makeText(getApplicationContext(), "Score Calculated", Toast.LENGTH_SHORT).show();

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


            }


        });




    }


    private void retrieveData(){
        ValueEventListener valueEventListener = new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    players.clear();
                    for (DataSnapshot ds: dataSnapshot.getChildren()){

                        Player player = ds.getValue(Player.class);
                        player.setID(ds.getKey());
                        players.add(player);

                    }
                    search();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityScorePage.this, "Connection Error", Toast.LENGTH_SHORT).show();

            }
        };

        reference.addValueEventListener(valueEventListener);


    }

    private void search(){

        currentPlayers.clear();
        if(players.size()!=0 && players.get(0)!=null) {
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                if (player.getGameID().equals(gameID)) {
                    currentPlayers.add(player);
                }

            }
        }
        else{
            Toast.makeText(ActivityScorePage.this, "No players", Toast.LENGTH_SHORT).show();

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        adapter = new PlayerAdapter(currentPlayers);
        recyclerView.setAdapter(adapter);


    }


    private void deletePlayers(){

        //iterate players
        for (int i = 0; i < currentPlayers.size(); i++)
        {
            Player player = currentPlayers.get(i);
            String playerID = player.getID();
            //delete from firebase
            reference.child(playerID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    //score cleared successfully
                    Toast.makeText(getApplicationContext(), "Table Cleared", Toast.LENGTH_SHORT).show();

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
    }

    public static void setGameId(String newGameId){
        staticGameId = newGameId;
    }

    public static void setGameName(String newGameName){
        gameName = newGameName;
    }

    public static void setGameID(String newGameID){
        gameID = newGameID;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)   {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.scoremenu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)   {

        switch (item.getItemId())
        {
            case R.id.resetScoreItem:

                //iterate through views and players, they have the same count and order
                for (int i = 0; i < recyclerView.getChildCount(); i++)
                {
                    Player player = currentPlayers.get(i);

                    //update in firebase
                    reference.child(player.getID()).child("score").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //score reseted successfully
                            Toast.makeText(getApplicationContext(), "Score Reseted", Toast.LENGTH_SHORT).show();

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
                break;

            case R.id.clearTableItem:
               deletePlayers();
                break;

            case R.id.deleteGameItem:
                deletePlayers();

                //delete from firebase
                db.getReference("games").child(gameID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //score cleared successfully
                        Toast.makeText(getApplicationContext(), "Game deleted", Toast.LENGTH_SHORT).show();

                        //open main activity

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                break;

            case R.id.changeNameItem:

                startActivity(new Intent(getApplicationContext(), ActivityChangeGameName.class));
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);

    }





}