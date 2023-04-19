package com.cs360_project_alayman.utils.auth;

public class AuthenticatedUser {
    private final long userId;

    public AuthenticatedUser(long id) {
        this.userId = id;
    }

    public long getId() {
        return userId;
    }
}
