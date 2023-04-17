package com.cs360_project_alayman.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cs360_project_alayman.data.dao.WeightDao;
import com.cs360_project_alayman.data.entities.Weight;
import com.cs360_project_alayman.repository.UserWeightRepository;

import java.util.List;

public class WeightViewModel extends AndroidViewModel {

    private LiveData<List<Weight>> weightList;
    private UserWeightRepository userWeightRepository;

    public WeightViewModel(Application application) {
        super(application);
        userWeightRepository = UserWeightRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Weight>> getWeightList(long userId) {
        return userWeightRepository.getWeightList(userId);
    }

    public void deleteWeight(Weight weight) {
        userWeightRepository.deleteWeight(weight);
    }

    public void updateWeight(Weight weight) {
        userWeightRepository.updateWeight(weight);
    }

}
