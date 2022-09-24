package com.example.sprintitappfinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class stepActivity extends AppCompatActivity implements SensorEventListener {

    //initialise variables

    private TextView textStepCounterTotal, textStepCounterCurrent;
    private SensorManager sensorManager;
    private Sensor stepCounter, stepDetector;
    private boolean sensorWorking,detectorWorking;
    int countSteps = 0, stepDetection = 0;
    private TextView lightText;
    private float lightValue;
    private Sensor lightSensor;
    private String cameraID;
    private CameraManager cameraManager;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){ //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        //state variable usages

        lightText = findViewById(R.id.lightlevel);
        textStepCounterTotal = findViewById(R.id.stepCounterTotal);
        textStepCounterCurrent = findViewById(R.id.stepCounterCurrent);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        try {
            cameraID = cameraManager.getCameraIdList()[0]; // set cameramanager
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){ // get light sensor
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        } else {
            Toast.makeText(this, "Light Sensor Not Detected", Toast.LENGTH_SHORT).show();
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) // get step counter
        {
            stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorWorking = true;
        }else {
            Toast.makeText(stepActivity.this, "Sensor Not Detected", Toast.LENGTH_SHORT).show();
            sensorWorking = false;
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) // get step detector
        {
            stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            detectorWorking = true;
        }else {
            Toast.makeText(stepActivity.this, "Sensor Not Detected", Toast.LENGTH_SHORT).show();
            detectorWorking = false;
        }

        // Utilises Slidr library to enable swipe to change activity
        Slidr.attach(this);

        // Layout linked to the xml file, sets the gradient animation background and sets the duration when created
        RelativeLayout relativeLayout = findViewById(R.id.steplayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.M) // requires minimum build
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == stepCounter){
            countSteps = (int) sensorEvent.values[0];
            textStepCounterTotal.setText(String.valueOf(countSteps));
        }

        if(sensorEvent.sensor == stepDetector){
            stepDetection = (int) (stepDetection+sensorEvent.values[0]);
            textStepCounterCurrent.setText(String.valueOf((stepDetection)));
        }

        lightValue = sensorEvent.values[0];
        lightText.setText(String.valueOf(lightValue));

        if (lightValue < 50){ // if light sensor value falls under, or raises above 50, manipulate flashlight
            try {
                cameraManager.setTorchMode(cameraID, true);
                Toast.makeText(stepActivity.this, "It's Dark. Wear Hi-Vis Clothing and Be Aware Of Your Surroundings!", Toast.LENGTH_SHORT).show();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                cameraManager.setTorchMode(cameraID, false);
                Toast.makeText(stepActivity.this, "It's Light. Be Safe When On Public Roads!", Toast.LENGTH_SHORT).show();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    //
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) !=null)
        sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) !=null)
            sensorManager.registerListener(this, lightSensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            sensorManager.unregisterListener(this, stepCounter);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) !=null)
            sensorManager.unregisterListener(this, stepDetector);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) !=null)
            sensorManager.unregisterListener(this, lightSensor);
    }
}