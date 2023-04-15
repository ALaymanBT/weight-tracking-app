package com.cs360_project_alayman.controller;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cs360_project_alayman.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User WHERE username = :username")
    User getUser(String username);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User getRegisteredUser(String username, String password);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addUser(User user);

}
