package com.example.sprintitappfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.r0adkll.slidr.Slidr;

public class notificationActivity extends AppCompatActivity {

    //initialise variables

    private EditText textTitle;
    private EditText textBody;
    private NotificationManagerCompat notificationManager; // utilises notification manager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //state variable usages

// Utilises Slidr library to enable swipe to change activity
        Slidr.attach(this);

        textTitle = (EditText) findViewById(R.id.notifyTitle);
        textTitle = (EditText) findViewById(R.id.notifyBody);

        notificationManager = NotificationManagerCompat.from(this);
        textTitle = findViewById(R.id.notifyTitle);
        textBody = findViewById(R.id.notifyBody);

// Layout linked to the xml file, sets the gradient animation background and sets the duration
        RelativeLayout relativeLayout = findViewById(R.id.notificationlayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

    }

    public void sendToChannel1(View v) {
        String title= textTitle.getText().toString();
        String message= textBody.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, Notifications.channel1ID)// sets all attributes for the notification
                .setSmallIcon(R.drawable.ic_baseline_filter_1_24) // sets logo in notification
                .setContentTitle(title) // displays title
                .setContentText(message) // displays message body
                .setPriority(NotificationCompat.PRIORITY_HIGH) // sets priority to enable category features
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true) // once swiped off, don't show again
                .build(); // build notification according to the above

        notificationManager.notify(1, notification); // sets notification to id 1 (used in cases with multiple channels
    }
}