package com.cs360_project_alayman.utils.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cs360_project_alayman.R;
import com.cs360_project_alayman.ui.activities.MainActivity;

public class NotificationHelper {

    private static SharedPreferences sharedPrefs;
    private static SharedPreferences.Editor editor;

    private NotificationManager notificationManager;
    private static NotificationHelper instance;
    private Context context;

    private String userId;
    private static final String INFO = "notification_prefs";
    private static final String CHANNEL_ID = "Goal-Met";

    /**
     *  Constructor - converts userId from long to String to be used as a file name for sharedPrefs
     * @param context - the application context
     */
    private NotificationHelper(Context context) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences(INFO, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        editor.apply();
    }

    /**
     *  Initializes the NotificationHelper
     * @param context - the application context
     */
    public static void initialize(Context context) {
        if (instance == null)  {
            instance = new NotificationHelper(context);
        }
    }

    /**
     * Get the singleton instance
     * @throws IllegalStateException if notificationHelper is not initialized
     * @return - the NotificationHelper instance
     */
    public static NotificationHelper getInstance() {
        if (instance == null) {
            throw new IllegalStateException("NotificationHelper not initialized");
        }
        return instance;
    }

    public void setUserId(long userId) {
        this.userId = String.valueOf(userId);
    }

    /**
     * Saves the current user's notification preference to device
     * @param isEnabled - the value of the notification option
     */
    public void saveNotificationPreference(boolean isEnabled) {
        editor.putBoolean(userId, isEnabled);
        editor.apply();
    }


    public Boolean getNotificationPreference() {
        return sharedPrefs.getBoolean(userId, false);
    }

    /**
     * Create a notification channel, CHANNEL_ID, to be used by createNotification()
     */
    public void createNotificationChannel() {

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "Goal",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("User reached their weight goal");
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * Builds a notification to be displayed when the user meets their goal weight
     */
    public void createNotification() {

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Congratulations!")
                .setContentText("You've met your weight goal!");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent notif = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(notif);
        notificationManager.notify(0, builder.build());
    }

    public void userLoggedOut() {
        this.userId = null;
        notificationManager.cancelAll();
    }
}
