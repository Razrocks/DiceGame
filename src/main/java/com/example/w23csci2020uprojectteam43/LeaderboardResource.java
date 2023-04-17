package com.example.w23csci2020uprojectteam43;

import java.io.File;
import java.net.URISyntaxException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/leaderboard")
public class MainServerResource {
    @GET
    @Produces("application/json")
    public Response getLeaderboard() throws URISyntaxException
    {
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type","application/json")
                .entity(new File(getClass().getClassLoader().getResource("leaderboard.json").toURI()))
                .build();
    }
}