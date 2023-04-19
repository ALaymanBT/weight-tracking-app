package com.cs360_project_alayman.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cs360_project_alayman.data.entities.Weight;
import com.cs360_project_alayman.repository.UserWeightRepository;

import java.time.LocalDate;
import java.util.List;

public class WeightViewModel extends AndroidViewModel {

    private final UserWeightRepository userWeightRepository;

    public WeightViewModel(Application application) {
        super(application);
        userWeightRepository = UserWeightRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Weight>> getWeightList(long userId) {
        return userWeightRepository.getWeightList(userId) == null ? null : userWeightRepository.getWeightList(userId);
    }

    public void deleteWeight(Weight weight) {
        userWeightRepository.deleteWeight(weight);
    }

    public void addWeight(Weight weight) {
        userWeightRepository.addWeight(weight);
    }

    public void updateWeight(Weight weight) {
        userWeightRepository.updateWeight(weight);
    }
    public Weight getDate(long id, LocalDate date) {
        return userWeightRepository.getDate(id, date);
    }

}
