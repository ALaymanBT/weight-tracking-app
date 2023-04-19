package com.cs360_project_alayman.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.cs360_project_alayman.ui.activities.MainActivity;
import com.cs360_project_alayman.R;
import com.cs360_project_alayman.utils.auth.AuthenticatedUserManager;
import com.cs360_project_alayman.utils.notification.NotificationHelper;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {

    private SwitchMaterial switchNotification;
    private AuthenticatedUserManager authenticatedUserManager;
    private NotificationHelper notificationHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        String title = getString(R.string.title_settings);
        ((MainActivity) getActivity()).setUpToolbar(title, true);

        return rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        authenticatedUserManager = AuthenticatedUserManager.getInstance();
        notificationHelper = NotificationHelper.getInstance();

        long userId = authenticatedUserManager.getUser().getId();
        switchNotification = view.findViewById(R.id.switch_sms_notification);
        switchNotification.setChecked(notificationHelper.getNotificationPreference());

        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                notificationHelper.saveNotificationPreference(switchNotification.isChecked());
            }
        });


    }


}