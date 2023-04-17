package com.cs360_project_alayman.utils.auth;

public class AuthenticatedUser {
    private long userId;

    public AuthenticatedUser(long id) {
        this.userId = id;
    }

    public long getId() {
        return userId;
    }
}
