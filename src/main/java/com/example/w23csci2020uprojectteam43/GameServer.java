package com.example.w23csci2020uprojectteam43;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.util.*;

@ServerEndpoint("/ws/{roomID}")
public class GameServer {
    Arraylist<Player> players = new Arraylist<Player>();

    @OnOpen
    public void onOpen(Session session, @PathParam("roomID") String roomID) {
        System.out.println("Connected to room: " + roomID);

        players.add(new Player(session));
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: " + message);

        //count how many players there are
        Integer count = players.size();

        if (message.contains("username:"))
        {
            message = message.split(":")[1];

            if (session == players[0].session)
            {
                players[0].username = message;
            }
            else if (session == players[1].session)
            {
                players[1].username = message;
            }
        }

        switch (message) {
            // case "username":
            // if (count == 1)
            // {
            //     Player player1 = new Player(username, session);
            // }
            // else if (count == 2)
            // {
            //     Player player2 = new Player2(username, session);
            // }

            //     //start the game here somehow
            //     break;
        
            case "attack":
            if (session == player1.session)
            {
                if (actionFlag == false)
                {
                    //do attack stuff here for player 1
                    player1.actionFlag = true;
                }

            }
            else if (session == player2.session)
            {
                if (actionFlag == false)
                {
                    //do attack stuff here for player 2
                    player2.actionFlag = true;
                }
            }
                break;
            case "defend":
            if (session == player1.session)
            {
                if (actionFlag == false)
                {
                    //do defend stuff here for player 1
                    player1.actionFlag = true;
                }
            }
            else if (session == player2.session)
            {
                if (actionFlag == false)
                {
                    //do defend stuff here for player 2
                    player2.actionFlag = true;
                }
            }
                break;

            case "heal":
            if (session == player1.session)
            {
                if (actionFlag == false)
                {
                    //do heal stuff here for player 1
                    player1.actionFlag = true;
                }
            }
            else if (session == player2.session)
            {
                if (actionFlag == false)
                {
                    //do heal stuff here for player 2
                    player2.actionFlag = true;
                }
            }
                break;
        }


        if (player1.actionFlag == true && player2.actionFlag == true)
        {
            // give updates to the client
            //reset the action flags
            player1.actionFlag = false;
            player2.actionFlag = false;
        }

        
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Disconnected");
    }
}
