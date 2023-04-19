package com.cs360_project_alayman.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.cs360_project_alayman.data.dao.WeightDao;
import com.cs360_project_alayman.data.dao.WeightGoalDao;
import com.cs360_project_alayman.data.database.UserWeightDatabase;
import com.cs360_project_alayman.data.entities.User;
import com.cs360_project_alayman.data.dao.UserDao;
import com.cs360_project_alayman.data.entities.Weight;
import com.cs360_project_alayman.data.entities.WeightGoal;

import java.time.LocalDate;
import java.util.List;

public class UserWeightRepository {

    private static UserWeightRepository weightRepo;
    private final UserDao userDao;
    private final WeightDao weightDao;
    private final WeightGoalDao weightGoalDao;

    /**
     *
     * @param context Context of the activity
     * @return UserWeightRepository instance of the repository class
     */
    public static UserWeightRepository getInstance(Context context) {

        if (weightRepo == null) {
            weightRepo = new UserWeightRepository(context);
        }
        return weightRepo;
    }
    private UserWeightRepository(Context context) {
        UserWeightDatabase db = Room.databaseBuilder(context, UserWeightDatabase.class, "user_weight.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        userDao = db.userDao();
        weightDao = db.weightDao();
        weightGoalDao = db.weightGoalDao();
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User getUser(String username) {
        return userDao.getUser(username);
    }

    public void addWeight(Weight weight) {
        weightDao.addWeight(weight);
    }
    public void deleteWeight(Weight weight) {
        weightDao.deleteWeight(weight);
    }
    public void updateWeight(Weight weight) {
        weightDao.updateWeight(weight);
    }
    public Weight getDate(long id, LocalDate date) {
        return weightDao.getDate(id, date);
    }
    public LiveData<List<Weight>> getWeightList(long userId) {
        return weightDao.getWeightList(userId) == null ? null : weightDao.getWeightList(userId);
    }

    public void addWeightGoal(WeightGoal weightGoal) {
        weightGoalDao.addWeightGoal(weightGoal);
    }

    public WeightGoal getWeightGoal(long userId) {
        return weightGoalDao.getWeightGoal(userId);
    }
}
