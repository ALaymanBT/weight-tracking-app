package com.cs360_project_alayman.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.cs360_project_alayman.ui.fragments.LoginFragment;
import com.cs360_project_alayman.R;
import com.cs360_project_alayman.utils.auth.AuthenticatedUserManager;
import com.cs360_project_alayman.viewmodel.WeightViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "Goal-Met";
    private WeightViewModel weightViewModel;
    private AuthenticatedUserManager authenticatedUserManager;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.setReorderingAllowed(true)
                    .add(R.id.nav_host_fragment_container, LoginFragment.class, null)
                    .commit();
        }

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "Goal",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("User reached their weight goal");
        notificationManager.createNotificationChannel(channel);


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
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enableBackButton);
    }

    /**
     * Builds a notification to be displayed when the user meets their goal weight
     */
    public void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Congratulations!")
                .setContentText("You've met your weight goal!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        notificationManager.notify(0, builder.build());
    }
}