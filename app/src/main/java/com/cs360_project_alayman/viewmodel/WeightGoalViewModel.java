package com.cs360_project_alayman.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.cs360_project_alayman.data.entities.WeightGoal;
import com.cs360_project_alayman.repository.UserWeightRepository;


public class WeightGoalViewModel extends AndroidViewModel {

    private UserWeightRepository userWeightRepository;

    public WeightGoalViewModel(Application application) {
        super(application);
        userWeightRepository = UserWeightRepository.getInstance(application.getApplicationContext());
    }

    public WeightGoal getWeightGoal(long userId) {
        return userWeightRepository.getWeightGoal(userId);
    }

    public void addWeightGoal(WeightGoal weightGoal) {
        userWeightRepository.addWeightGoal(weightGoal);
    }

}
