package com.example.sprintitappfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.sprintitappfinal.databinding.ActivityMapsBinding;
import com.r0adkll.slidr.Slidr;

@SuppressWarnings("ALL") // add relevant interfaces for functions
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener { // sets appropraite functions according to Google API (deprecation ignored)

    // Initialises variables

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button button;
    Location gLocation;
    Marker locationMarker;
    GoogleApiClient gGoogleClient;
    LocationRequest gRequestLocation;

    private static int PERMISSION_REQUEST_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //state variable usages

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        button = findViewById(R.id.gotoButton);

        Slidr.attach(this);

        checkLocationPermission();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LatLng brighton = new LatLng(50.8229, 0.1363); // brings user to set location (adapted from google library)
                mMap.addMarker(new MarkerOptions().position(brighton).title("Marker in Brighton"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(brighton, 12));

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Layout linked to the xml file, sets the gradient animation background and sets the duration when created
        RelativeLayout relativeLayout = findViewById(R.id.mapsLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){ // ask for permission when minimum build is met
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                buildGoogleClient();
                mMap.setMyLocationEnabled(true);
            }
        } else { // asks users for permission
                buildGoogleClient();
                mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleClient() {
        gGoogleClient = new GoogleApiClient.Builder(this).addConnectionCallbacks (this).addOnConnectionFailedListener (this).addApi (LocationServices.API).build(); // utilise google API functions
        gGoogleClient.connect();
        }

    private void checkLocationPermission() { // check if user has granted permission for location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("This permission is neccessary to access your current location")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
            } else {
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        } else {
            // empty
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) { // interval reuqest fromm server
        gRequestLocation = new LocationRequest(); // requests new user location
        gRequestLocation.setInterval (1000); // every second
        gRequestLocation.setFastestInterval(1000);
        gRequestLocation.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(gGoogleClient, gRequestLocation, this); // requests current location using google location services
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) { // remove marker and update on new loaction
        gLocation = location;
        if(locationMarker != null) {
            locationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position (latLng);
        markerOptions.title("Current Location");
        markerOptions.icon (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        locationMarker = mMap.addMarker(markerOptions);

    }
}