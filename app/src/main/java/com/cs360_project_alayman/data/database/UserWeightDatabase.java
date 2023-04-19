package com.cs360_project_alayman.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cs360_project_alayman.data.converters.LocalDateConverter;
import com.cs360_project_alayman.data.dao.WeightDao;
import com.cs360_project_alayman.data.dao.WeightGoalDao;
import com.cs360_project_alayman.data.entities.User;
import com.cs360_project_alayman.data.dao.UserDao;
import com.cs360_project_alayman.data.entities.Weight;
import com.cs360_project_alayman.data.entities.WeightGoal;

@Database(entities = {User.class, Weight.class, WeightGoal.class}, version = 1)
@TypeConverters({LocalDateConverter.class})
public abstract class UserWeightDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract WeightDao weightDao();
    public abstract WeightGoalDao weightGoalDao();
}
