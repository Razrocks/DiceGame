package com.example.w23csci2020uprojectteam43;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws/{roomID}")
public class GameServer {

    @OnOpen
    public void onOpen(Session session, @PathParam("roomID") String roomID) {
        System.out.println("Connected to room: " + roomID);
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Disconnected");
    }
}
