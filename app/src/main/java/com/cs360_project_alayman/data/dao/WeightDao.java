package com.cs360_project_alayman.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cs360_project_alayman.data.entities.Weight;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface WeightDao {

    @Query("SELECT * FROM Weight WHERE id = :id")
    Weight getWeight(long id);
    // FIXME: Order by date
    @Query("SELECT * FROM Weight WHERE user_id = :userId")
    LiveData<List<Weight>> getWeightList(long userId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addWeight(Weight weight);

    //FIXME: Need an update annotation
    @Delete
    void deleteWeight(Weight weight);

    @Update
    void updateWeight(Weight weight);
}
