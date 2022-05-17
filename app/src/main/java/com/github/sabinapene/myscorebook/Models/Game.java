package com.github.sabinapene.myscorebook.Models;

public class Game {
    private String userID = "";
    private String name = "";
    private String gameID = "";

    public Game(){

    }

    public Game(String userID, String name){
        this.userID = userID;
        this.name = name;
    }

    public String getID() {
        return userID;
    }

    public void setID(String userID) {
        this.userID = userID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
