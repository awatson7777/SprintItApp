package com.example.sprintitappfinal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class StopWatchFragment extends Fragment implements View.OnClickListener  {

    //initialise variables

    private Chronometer chronometer;
    private long pause;
    private boolean running;
    Button startButton;
    Button pauseButton;
    Button resetButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stop_watch, container, false); // creates the layout view utilised by the fragment
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //state variable usages

        Button startButton = (Button) view.findViewById(R.id.startButton);
        Button pauseButton = (Button) view.findViewById(R.id.pauseButton);
        Button resetButton = (Button) view.findViewById(R.id.resetButton);
        Chronometer chronometer = (Chronometer) getView().findViewById(R.id.chronometer);

    }

    @Override
    public void onClick(View v) {
    switch (v.getId()) {
        case R.id.startButton: // when startButton is pressed
            chronometer.setBase(SystemClock.elapsedRealtime() - pause); // to start from 0, returns ms, - pause counts the numbers passed then subtracts it to produce realtime
            chronometer.start(); // start chronometer
            running = true;
            break;
        case R.id.pauseButton: // when pauseButton is pressed
            chronometer.stop(); // stops chronometer is chronometer does not have a pause method
            pause = SystemClock.elapsedRealtime() - chronometer.getBase(); // stops timer at current number (stops number running in background)
            running = false;
            break;
        case R.id.resetButton: // when resetButton is pressed
            chronometer.setBase(SystemClock.elapsedRealtime());// sets to 0
            pause=0; // sets pause value to 0
            break;
        }
    }

}