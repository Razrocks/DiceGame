package com.example.w23csci2020uprojectteam43;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

public class Player {
    public String username;
    public Session session;
    public double hp;
    public Integer roll;
    public boolean actionFlag; //used to keep track of whether the user has performed an action in a round

    public Player(Session session) {
        this.session = session;
        this.hp = 20;
        actionFlag = false;
    }
    
}
