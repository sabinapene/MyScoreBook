package com.github.sabinapene.myscorebook.Models;

public class Player {


    private String ID="";
    private String gameID = "";
    private String name ="";
    private int score = -1;

    public Player(){

    }
    public Player( String gameID, String name, int score){

        this.gameID = gameID;
        this.name = name;
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
