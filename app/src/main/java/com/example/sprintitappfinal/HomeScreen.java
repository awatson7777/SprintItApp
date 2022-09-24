package com.example.sprintitappfinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeScreen extends AppCompatActivity {

    // Initialises variables

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //state variable usages

        ImageButton timeButton = findViewById(R.id.timeactivity);
        ImageButton gpsButton = findViewById(R.id.locationactivity);
        ImageButton stepButton = findViewById(R.id.walkingactivity);
        ImageButton notificationButton = findViewById(R.id.notificationactivity);
        Button logoutButton = findViewById(R.id.logout);

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTime(); // call function
            }
        });

        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStep(); // call function
            }
        });

        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGPS(); // call function
            }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotification(); // call function
            }

        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToLogin(); // call function
                Toast.makeText(HomeScreen.this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

// Layout linked to the xml file, sets the gradient animation background and sets the duration
        RelativeLayout relativeLayout = findViewById(R.id.homelayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        Calendar calendar = Calendar.getInstance(); // get instance of calendar
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()); // format date

        TextView textViewDate = findViewById(R.id.date);
        textViewDate.setText(currentDate); // set date according to string

        Thread liveTime = new Thread() { // set live time
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000); // update seconds in ms
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tDate = (TextView) findViewById(R.id.time);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh-mm-ss a"); // time format
                                String dateString = dateFormat.format(date);
                                tDate.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        liveTime.start();
    }

    public void openTime() {
        Intent intent = new Intent(this, TimeActivity.class); // open activity
        startActivity(intent);
    }

    public void openStep() {
        Intent intent = new Intent(this, stepActivity.class); // open activity
        startActivity(intent);
    }

    public void openGPS() {
        Intent intent = new Intent(this, MapsActivity.class); // open activity
        startActivity(intent);
    }

    public void openNotification() {
        Intent intent = new Intent(this, notificationActivity.class); // open activity
        startActivity(intent);
    }

    public void returnToLogin() {
        Intent intent = new Intent(this, SetLogin.class); // open activity
        startActivity(intent);
    }

    public void buttonAnimation(View view) { // basic button animation (set from anim folder
        Button button = (Button)findViewById(R.id.logout);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounceanim);
        button.startAnimation(animation);
    }

}