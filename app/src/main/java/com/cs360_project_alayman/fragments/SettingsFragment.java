package com.cs360_project_alayman.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs360_project_alayman.controller.MainActivity;
import com.cs360_project_alayman.R;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        String title = getString(R.string.title_settings);
        ((MainActivity) getActivity()).setUpToolbar(title, true);

        return rootView;
    }


}