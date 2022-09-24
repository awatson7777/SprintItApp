package com.example.sprintitappfinal;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.net.ConnectException;

public class Notifications extends Application {
    public static final String channel1ID = "channel1ID";
    private NotificationManager nManager;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels(); // calls function
    }

    private void createNotificationChannels() { // creates function
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // checks devices minimum build version
            NotificationChannel channel1 = new NotificationChannel(
                    channel1ID,
                    "channel1ID",
                    NotificationManager.IMPORTANCE_HIGH // sets notification to use high importance features such as sound etc...
            );
            channel1.setDescription("Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1); // creates the notification channel that can be called from other classes that uses a notification manager
        }
    }
}
