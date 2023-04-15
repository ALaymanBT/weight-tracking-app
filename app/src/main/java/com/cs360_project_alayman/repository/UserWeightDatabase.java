package com.cs360_project_alayman.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cs360_project_alayman.model.User;
import com.cs360_project_alayman.controller.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class UserWeightDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
