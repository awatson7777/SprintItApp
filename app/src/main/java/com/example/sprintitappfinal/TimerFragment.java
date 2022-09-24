package com.example.sprintitappfinal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class TimerFragment extends Fragment implements View.OnClickListener {

    //initialise variables

    private TextView timerText;
    private Button startButton;
    private Button resetButton;
    private CountDownTimer timerCountDown;
    private static final long timeInMS = 600000; // initialises the timer to 10 minutes
    private boolean isTimerRunning;
    private long timeLeftInMS;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, // creates the layout view utilised by the fragment
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //state variable usages

        Button startButton = (Button) view.findViewById(R.id.startButton);
        Button resetButton = (Button) view.findViewById(R.id.resetButton);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                if (isTimerRunning) {
                    pauseTimer(); // calls function
                } else {
                    startTimer(); // calls function
                }
        }

    }

    private void startTimer() {
        timerCountDown = new CountDownTimer(timeLeftInMS, 1000) { // defines length of interval for OnTick
            @Override
            public void onTick(long finishedMS) {
                timeLeftInMS = finishedMS; // number of ms until timer is finished
                updateText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false; // not running
                startButton.setText("Start"); // timer is not running
                startButton.setVisibility(View.INVISIBLE); // sets start button to invisible
                resetButton.setVisibility(View.VISIBLE); // sets reset button to visible
            }
        }.start();// starts the timer

        isTimerRunning = true; // timer is now running
        startButton.setText("pause"); // changes text
        resetButton.setVisibility(View.INVISIBLE); // sets reset button to invisible

    }

    private void updateText() {
        int mins = (int) timeLeftInMS / 1000 / 60; // turns ms in minutes
        int secs = (int) timeLeftInMS / 1000 % 60; // turns ms in seconds

        String formatTimeLeft = String.format(Locale.getDefault(), "%02d:%02d", mins, secs); // coverts into a time string and sets a time format
        timerText.setText(formatTimeLeft);
    }

    private void pauseTimer() {
        timerCountDown.cancel(); // pauses countdown
        isTimerRunning = false; // sets to False
        startButton.setText("Start"); // changes text
        resetButton.setVisibility(View.VISIBLE); // sets reset button to visible

    }

    private void resetTimer() {
        timeLeftInMS = timeInMS;
        updateText();
        resetButton.setVisibility(View.INVISIBLE); // sets reset button to invisible
        startButton.setVisibility(View.VISIBLE); // sets start button to visible
    }

}