package com.example.w23csci2020uprojectteam43;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

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

        // get current and other player (so we can differentiate between who sent the current message)
        Player currentPlayer = players.get(session);
        Player otherPlayer = null;
        for (Map.Entry<Session, Player> entry : players.entrySet()) 
        {
            if (!entry.getKey().equals(session)) {
                otherPlayer = entry.getValue();
                break;
            }
        }

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
                    entry.getValue().session.getAsyncRemote().sendText("ROLL," + entry.getValue().roll);
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

        // switch case to determine what the player wants to do
        switch (message)
        {
            case "ATTACK":
            
            //first check if its the player's turn
            if (currentPlayer.turn == true)
            {
                // deal the damage to the other player
                otherPlayer.hp -= currentPlayer.roll;

                if (otherPlayer.hp <= 0)
                {
                    // send message to both players that the game is over and who won
                    currentPlayer.session.getAsyncRemote().sendText("WIN");
                    otherPlayer.session.getAsyncRemote().sendText("LOSE");

                    // update the leaderboard data
                    updateLeaderboard(currentPlayer.username);

                    return; // end the game
                }
                else
                {
                    // let both players know the other players hp
                    currentPlayer.session.getAsyncRemote().sendText("OTHER," + otherPlayer.hp);
                    otherPlayer.session.getAsyncRemote().sendText("TAKEDMG," + otherPlayer.hp);

                    // roll a new number for the current player
                    currentPlayer.roll = (int)(Math.random() * 6) + 1;
                    currentPlayer.session.getAsyncRemote().sendText("ROLL," + currentPlayer.roll);

                    // update the turns
                    currentPlayer.turn = false;
                    otherPlayer.turn = true;
                }
            }
            else
            {
                currentPlayer.session.getAsyncRemote().sendText("NOTYOURTURN");
                return;
            }

            break;

            case "HEAL":

            //first check if its the player's turn
            if (currentPlayer.turn == true)
            {
                if (currentPlayer.hp == 20)
                {
                    currentPlayer.session.getAsyncRemote().sendText("FULLHP");
                    return;
                }
                else if (currentPlayer.hp+currentPlayer.roll > 20)
                {
                    currentPlayer.hp = 20;
                }
                else
                {
                    // heal the current player
                    currentPlayer.hp += currentPlayer.roll;

                    // let both players know the current players hp
                    currentPlayer.session.getAsyncRemote().sendText("HP," + currentPlayer.hp);
                    otherPlayer.session.getAsyncRemote().sendText("OTHER," + currentPlayer.hp);

                    // roll a new number for the current player
                    currentPlayer.roll = (int)(Math.random() * 6) + 1;
                    currentPlayer.session.getAsyncRemote().sendText("ROLL," + currentPlayer.roll);

                    // update the turns
                    currentPlayer.turn = false;
                    otherPlayer.turn = true;
                }
            }
            else
            {
                currentPlayer.session.getAsyncRemote().sendText("NOTYOURTURN");
                return;
            }
        }

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

    // method to update the leaderboard data
    public void updateLeaderboard(String username)
    {
        try {
            File file = new File(getClass().getClassLoader().getResource("leaderboard.json").toURI());

            // read the json file into a list of leaderboard entries class
            ObjectMapper mapper = new ObjectMapper();
            List<LeaderboardEntry> entries = mapper.readValue(file, new TypeReference<List<LeaderboardEntry>>(){});
            
            // check if the the user is already in the list and if so add 1 to their wins
            boolean found = false;
            for (LeaderboardEntry entry : entries)
            {
                if (entry.name.equals(username))
                {
                    entry.wins += 1;
                    break;
                }
            }

            // if the user is not in the list add them to the list with a win of 1
            if (!found)
            {
                entries.add(new LeaderboardEntry(username, 1));
            }

            mapper.writeValue(file, entries);

        } catch (URISyntaxException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
