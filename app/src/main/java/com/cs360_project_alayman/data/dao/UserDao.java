package com.cs360_project_alayman.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cs360_project_alayman.data.entities.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User WHERE username = :username")
    User getUser(String username);

    //FIXME: Refer to comment in database class
    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User getRegisteredUser(String username, String password);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addUser(User user);

}
