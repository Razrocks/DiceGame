package com.example.w23csci2020uprojectteam43;

import jakarta.websocket.Session;

public class Player 
{
    // class variables
    public Session session;
    public String username;
    public double hp;
    public Integer roll;
    public boolean turn;

    // Constructor for player
    public Player(Session session) 
    {
        this.session = session;
        this.username = null;
        this.hp = 20;
        this.roll = 0;
        this.turn = false;
    }
    
}
