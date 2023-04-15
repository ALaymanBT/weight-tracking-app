package com.cs360_project_alayman.repository;

import android.content.Context;

import androidx.room.Room;

import com.cs360_project_alayman.model.User;
import com.cs360_project_alayman.controller.UserDao;

public class UserWeightRepository {

    private static UserWeightRepository weightRepo;
    private final UserDao userDao;

    public static UserWeightRepository getInstance(Context context) {

        if (weightRepo == null) {
            weightRepo = new UserWeightRepository(context);
        }
        return weightRepo;
    }
    // FIXME: Remove test from db name after testing2
    private UserWeightRepository(Context context) {
        UserWeightDatabase db = Room.databaseBuilder(context, UserWeightDatabase.class, "user_weight_test.db")
                .allowMainThreadQueries()
                .build();

        userDao = db.userDao();
    }

    public void addUser(User user) {
        long userId = userDao.addUser(user);
        user.setId(userId);
    }

    public User getUser(String username) {
        return userDao.getUser(username);
    }

    public User getRegisteredUser(String username, String password) {
        return userDao.getRegisteredUser(username, password);
    }
}
