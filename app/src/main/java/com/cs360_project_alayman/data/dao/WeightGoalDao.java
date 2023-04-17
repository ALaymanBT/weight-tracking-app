package com.cs360_project_alayman.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cs360_project_alayman.data.entities.WeightGoal;

@Dao
public interface WeightGoalDao {

    @Query("SELECT * FROM WeightGoal WHERE user_id = :userId")
    WeightGoal getWeightGoal(long userId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addWeightGoal(WeightGoal weightGoal);
}
