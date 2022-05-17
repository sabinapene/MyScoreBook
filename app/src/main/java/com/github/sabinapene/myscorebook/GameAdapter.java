package com.github.sabinapene.myscorebook;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sabinapene.myscorebook.Models.Game;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>{
    List<Game> games;
    Game currentGame = new Game();

    public Game getCurrentGame()
    {
        return currentGame;
    }
    public GameAdapter(List<Game> games){
        this.games = games;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.game_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.buttongamename.setText(games.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button buttongamename;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buttongamename = itemView.findViewById(R.id.gamenamebutton);

            buttongamename.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    currentGame = games.get(getAdapterPosition());
                    ActivityScorePage.setGameName(currentGame.getName());
                    ActivityScorePage.setGameID(currentGame.getGameID());
                    ActivityChangeGameName.setGameID(currentGame.getGameID());
                    ActivityChangeGameName.setGameName(currentGame.getName());
                    ActivityNewPlayerPage.setGameID(currentGame.getGameID());
                    Intent intent = new Intent(itemView.getContext(), ActivityScorePage.class);
                    v.getContext().startActivity(intent);
                }
            });



        }

    }
}
