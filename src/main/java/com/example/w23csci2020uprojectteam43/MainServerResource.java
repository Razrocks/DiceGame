package com.example.w23csci2020uprojectteam43;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class MainServerResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}