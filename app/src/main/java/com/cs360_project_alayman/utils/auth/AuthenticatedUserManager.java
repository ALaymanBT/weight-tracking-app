package com.cs360_project_alayman.utils.auth;

import android.content.Context;

public class AuthenticatedUserManager {

    private AuthenticatedUser user;
    private static AuthenticatedUserManager instance;

    private AuthenticatedUserManager(){
        // Empty constructor
    }

    public static AuthenticatedUserManager getInstance() {

        if (instance == null) {
            instance = new AuthenticatedUserManager();
        }
        return instance;
    }

    public AuthenticatedUser getUser() {
        return user;
    }

    public void setUser(AuthenticatedUser user) {
        this.user = user;
    }
}
