package com.github.sabinapene.myscorebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sabinapene.myscorebook.Models.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder>{
    List<Player> players;
    Player currentPlayer = new Player();

    public Player getCurrentGame()
    {
        return currentPlayer;
    }
    public PlayerAdapter(List<Player> players){
        this.players = players;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.player_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.playerTextView.setText(players.get(position).getName()+": "+players.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView playerTextView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerTextView = itemView.findViewById(R.id.playertextview);

            /*playerTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    currentPlayer = players.get(getAdapterPosition());
                    ActivityScorePage.setPlayerId(currentPlayer.getID());
                    Intent intent = new Intent(itemView.getContext(), ActivityScorePage.class);
                    v.getContext().startActivity(intent);

            });}*/



        }

    }
}
