package com.cs360_project_alayman.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cs360_project_alayman.data.entities.Weight;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface WeightDao {

    @Query("SELECT * FROM Weight WHERE user_id = :userId ORDER BY date DESC")
    LiveData<List<Weight>> getWeightList(long userId);

    @Query("SELECT * FROM Weight WHERE user_id = :userId AND date = :date")
    Weight getDate(long userId, LocalDate date);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addWeight(Weight weight);

    @Delete
    void deleteWeight(Weight weight);

    @Update
    void updateWeight(Weight weight);
}
