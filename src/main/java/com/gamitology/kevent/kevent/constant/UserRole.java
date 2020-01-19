package com.gamitology.kevent.kevent.constant;

public enum UserRole {
    ADMIN(1, "admin"),
    USER(2, "user");

    private int id;
    private String name;

    UserRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

