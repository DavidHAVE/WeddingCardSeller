package com.mustika.weddingcardseller.weddingcardseller.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mustika.weddingcardseller.weddingcardseller.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    private MapFragment mapFragment;

    private EditText mLokasiEditText;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected LocationRequest mLocationRequest;
    protected double latitude, longtitude;
    protected CameraPosition PLACE = null;
    public static LatLng latLng;
    LocationManager mLocationManager;
    GoogleMap gMap;
    Marker currLocationMarker;

    boolean mapReady=false;
    private static final int REQUEST_FINE_RESULT = 202;

//    static final CameraPosition YOGYAKARTA = CameraPosition.builder()
//            .target(latLng)
//            .zoom(10)
//            .bearing(0)
//            .tilt(45)
//            .build();

    public static String LATITUDE = "";

    public static String LONGTITUDE = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mLokasiEditText = (EditText) findViewById(R.id.edit_schedule_location);

        mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                stopLocationUpdates();
                mGoogleApiClient.disconnect();
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //MapsInitializer.initialize(getApplicationContext());
        mapReady=true;
        gMap = googleMap;

        Log.d("MapActivity", "onMapReady");


        if (gMap != null && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return;
            accessSetup();
        }else if (gMap != null){
            setup();
        }
//        else {
//
//            flyTo(YOGYAKARTA);
//
//            gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//                @Override
//                public void onMapLongClick(LatLng latLng) {
//                    gMap.clear();
//                    gMap.addMarker(new MarkerOptions().position(latLng).title("Custom location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//                    Toast.makeText(MapActivity.this, latLng.latitude + " : " + latLng.longitude, Toast.LENGTH_SHORT).show();
//                    LATITUDE = "" + latLng.latitude;
//                    LONGTITUDE = "" + latLng.longitude;
//                    mLokasiEditText.setText(LATITUDE + "," + LONGTITUDE);
//                }
//            });
//        }
    }

    private void accessSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                setup();
                Log.d("MapActivity", "Not Granted");
                return;
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(getApplicationContext(), "Butuh Akses GPS", Toast.LENGTH_SHORT).show();
                }
                Log.d("Map Activity", "Granted");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_RESULT);
            }
        } else {
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_FINE_RESULT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setup();
                return;
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d("MapActivity", "finishh");
                finish();
            }
//        }else if
// (requestCode == REQUEST_COARSE_RESULT){
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                return;
//            }else {
//                finish();
//            }
        }
    }

    @SuppressLint("MissingPermission")
    private void setup() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
            Log.d("MapActivity", "lastLocation");
            buildGoogleApiClient();
            mGoogleApiClient.connect();
            gMap.setMyLocationEnabled(true);
            //   buildGoogleApiClient();
//            gMap = googleMap;
//            Log.e(TAG, "onMapReady");
//            googleMap.setMyLocationEnabled(true);
//            gMap.clear();  //clears all Markers and Polylines

        }
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Hidupkan GPS")
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

//        mGoogleApiClient.connect();

        Log.d("MapActivity", "buidGoogleApiClient");
    }

    private void flyTo(CameraPosition target)
    {
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(800);
        //   mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(0.45F);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onReq uestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("MapActivity", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("MapActivity", "Connection failed: ConnectionRewsult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude()); //you already have this

        Log.d("MapActivity", "latitude :"+location.getLatitude()+", longtitude :"+location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Posisition");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currLocationMarker = gMap.addMarker(markerOptions);

//        flyTo(YOGYAKARTA);

        if(gMap!=null){
            LatLng SYDNEY = latLng;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            gMap.animateCamera(cameraUpdate);
        }

        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                gMap.clear();
                gMap.addMarker(new MarkerOptions().position(latLng).title("Custom location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                Toast.makeText(MapActivity.this, latLng.latitude + " : " + latLng.longitude, Toast.LENGTH_SHORT).show();
                LATITUDE = "" + latLng.latitude;
                LONGTITUDE = "" + latLng.longitude;
                mLokasiEditText.setText(LATITUDE + "," + LONGTITUDE);
            }
        });
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }
}
