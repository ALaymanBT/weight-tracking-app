package com.cs360_project_alayman.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.cs360_project_alayman.ui.fragments.LoginFragment;
import com.cs360_project_alayman.R;
import com.cs360_project_alayman.utils.notification.NotificationHelper;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationHelper notificationHelper;
        try {
            notificationHelper = NotificationHelper.getInstance();
        }
        catch (IllegalStateException e) {
            NotificationHelper.initialize(getApplicationContext());
            notificationHelper = NotificationHelper.getInstance();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setReorderingAllowed(true)
                    .add(R.id.nav_host_fragment_container, LoginFragment.class, null)
                    .commit();
        }
    notificationHelper.createNotificationChannel();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Generate the title and back button of the toolbar
     * @param title - the title of the fragment
     * @param enableBackButton - true if the fragment should have a back button, false otherwise
     */
    public void setUpToolbar(String title, Boolean enableBackButton) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enableBackButton);
    }

}