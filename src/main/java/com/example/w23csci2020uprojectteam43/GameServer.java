package com.example.w23csci2020uprojectteam43;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.File;
import java.util.*;

@ServerEndpoint("/ws/{roomID}")
public class GameServer {
    private static LinkedHashMap<Session, Player> players = new LinkedHashMap<Session, Player>(); //linked hash map so the order of the players is preserved (may not be needed)

    @OnOpen
    public void onOpen(Session session, @PathParam("roomID") String roomID) 
    {
        System.out.println("Connected to room: " + roomID); //debug

        players.put(session,new Player(session)); //adding the new session to the list of players
    }

    @OnMessage
    public void onMessage(String message, Session session) 
    {
        System.out.println("Received message: " + message); //debug

        // setting username of player
        if (message.contains("username:"))
        {
            String username = message.split(":")[1];
            if (username == null || username == "")
            {
                players.get(session).username = "John Doe"; // defualt username
            }
            else
            {
                players.get(session).username = username;
            }

            
            // starting the game if this is the second player
            if (players.size() == 2)
            {
                // sends a message to all players that the game is starting and rolls a random number for each player for first round
                players.entrySet().forEach(entry -> 
                {
                    entry.getValue().session.getAsyncRemote().sendText("START");
                    entry.getValue().roll = (int)(Math.random() * 6) + 1;
                });

                // setting the last player as the first player to go
                players.get(session).turn = true;
            }
            else
            {
                // send message to player that they are waiting for another player
                session.getAsyncRemote().sendText("WAIT");
            }
        }

        // game loop goes here or take it into other functions/threads or something

        // after game ends make sure to do:
        //File file = new File(getClass().getClassLoader().getResource("leaderboard.json").toURI()); // throw this in try catch
        // then add +1 to the wins of the winner if they already exist in the list, if not add them to the list with a win of 1

    }

        

    @OnClose
    public void onClose(Session session) 
    {
        System.out.println("Disconnected"); //debug

        players.remove(session); //removing the session from the list of players
        // probably need to do something other stuff here too 
    }
}
