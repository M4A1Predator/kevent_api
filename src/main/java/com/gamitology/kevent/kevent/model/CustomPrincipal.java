package com.gamitology.kevent.kevent.model;

public class CustomPrincipal {
    private int id;
    private String username;

    public CustomPrincipal(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
}
