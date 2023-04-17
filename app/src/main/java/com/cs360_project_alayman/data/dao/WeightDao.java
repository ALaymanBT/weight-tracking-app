package com.cs360_project_alayman.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cs360_project_alayman.data.entities.Weight;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface WeightDao {

    @Query("SELECT * FROM Weight WHERE id = :id")
    Weight getWeight(long id);
    // FIXME: Order by date
    @Query("SELECT * FROM Weight WHERE user_id = :user_id")
    List<Weight> getWeightList(long user_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addWeight(Weight weight);

    //FIXME: Need an update annotation
}
