package com.example.sprintitappfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.r0adkll.slidr.Slidr;

public class TimeActivity extends AppCompatActivity {

    //initialise variables

    private long pause;
    private boolean running;
    BottomNavigationView bottomNavigationView;

    TimerFragment timerFragment = new TimerFragment();
    StopWatchFragment stopWatchFragment = new StopWatchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        //state variable usages

        // Utilises Slidr library to enable swipe to change activity
        Slidr.attach(this);

        bottomNavigationView = findViewById(R.id.bottomnav);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,timerFragment).commit(); // initialises the timer fragment as the first fragment to appear

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
               switch (item.getItemId()){
                   case R.id.Timer:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,timerFragment).commit(); // timer fragment appears first but when clicked, will replace stopwatch fragment
                       return true;
                   case R.id.Stopwatch:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,stopWatchFragment).commit(); // replaces timer fragment with stopwatch
                       return true;
               }
               return false;
            }
        });

        // Layout linked to the xml file, sets the gradient animation background and sets the duration when created
        RelativeLayout relativeLayout = findViewById(R.id.timelayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

    }
}