package com.example.w23csci2020uprojectteam43;

import jakarta.websocket.Session;

public class Player {
    //some of these may not be needed not sure until game logic is implemented
    public Session session;
    public String username;
    public double hp;
    public Integer roll;
    public String action; // holds info about what action the user is currently doing
    public boolean turn; // for keeping track of whether its this user's turn or not

    public Player() {
        this.username = null;
        this.hp = 20;
        this.roll = 0;
        this.action = null;
        this.turn = false;
    }
    
}
