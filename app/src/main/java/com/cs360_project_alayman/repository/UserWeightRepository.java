package com.cs360_project_alayman.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.cs360_project_alayman.data.dao.WeightDao;
import com.cs360_project_alayman.data.database.UserWeightDatabase;
import com.cs360_project_alayman.data.entities.User;
import com.cs360_project_alayman.data.dao.UserDao;
import com.cs360_project_alayman.data.entities.Weight;

import java.util.ArrayList;
import java.util.List;

public class UserWeightRepository {

    private static UserWeightRepository weightRepo;
    private final UserDao userDao;
    private final WeightDao weightDao;

    //FIXME: ADD JAVADOC COMMENTS

    /**
     *
     * @param context
     * @return
     */
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
                .fallbackToDestructiveMigration()
                .build();

        userDao = db.userDao();
        weightDao = db.weightDao();
    }

    public void addUser(User user) {
        long userId = userDao.addUser(user);
        //user.setId(userId);
    }

    public User getUser(String username) {
        return userDao.getUser(username);
    }

    public void addWeight(Weight weight) {
        long weightId = weightDao.addWeight(weight);
    }
    public void deleteWeight(Weight weight) {
        weightDao.deleteWeight(weight);
    }
    public void updateWeight(Weight weight) {
        weightDao.updateWeight(weight);
    }
    public Weight getWeight(long id) {
        return weightDao.getWeight(id);
    }
    public LiveData<List<Weight>> getWeightList(long userId) {
        return weightDao.getWeightList(userId);
    }


    //FIXME: Don't think I actually need this, should be using authenticated user class
    public User getRegisteredUser(String username, String password) {
        return userDao.getRegisteredUser(username, password);
    }
}
