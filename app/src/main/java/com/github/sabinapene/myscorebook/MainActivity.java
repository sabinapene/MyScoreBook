package com.github.sabinapene.myscorebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.sabinapene.myscorebook.Models.Game;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    //firebase authentification
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private ArrayList<Game> games = new ArrayList<Game>();
    private ArrayList<Game> currentGames = new ArrayList<Game>();
    private String  userID;
    DatabaseReference reference;
    GameAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);


        //initialising firebase authentification
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("games");
        userID = firebaseAuth.getUid();
        games.clear();

        retrieveData();


    }

    private void retrieveData(){
        ValueEventListener valueEventListener = new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    games.clear();
                    for (DataSnapshot ds: dataSnapshot.getChildren()){

                        Game game = ds.getValue(Game.class);
                        game.setGameID(ds.getKey());
                        // game = new Game(ds.child("id").getValue(String.class), ds.child("name").getValue(String.class));
                        games.add(game);

                    }
                    search();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();

            }

        };

        reference.addValueEventListener(valueEventListener);


    }

    private void search(){

        currentGames.clear();
        if(games.size()!=0 && games.get(0)!=null) {
            for (int i = 0; i < games.size(); i++) {
                Game item = games.get(i);
                if (item.getID().equals(userID)) {
                    currentGames.add(item);
                }

            }
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.hasFixedSize();
            adapter = new GameAdapter(currentGames);
            recyclerView.setAdapter(adapter);
        }
        else{
            Toast.makeText(MainActivity.this, "No games", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)   {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)   {

        switch (item.getItemId())
        {
            case R.id.profileItem:
                Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
                break;
        }

        return super.onOptionsItemSelected(item);

    }

}