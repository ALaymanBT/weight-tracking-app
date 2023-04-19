package com.cs360_project_alayman.utils.auth;

public class AuthenticatedUserManager {

    private AuthenticatedUser user;
    private static AuthenticatedUserManager instance;

    private AuthenticatedUserManager(){
        // Empty constructor
    }

    /**
     * Singleton pattern to ensure only one instance of the AuthenticatedUserManager is created
     * @return
     */
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
