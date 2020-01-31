package com.maxmux.itaxi.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.toolbox.Volley;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.maxmux.itaxi.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    MarkerOptions markerOptions;
    Marker marker;


    private MapFragment mapFragment;
    View mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button btnRequestRide = findViewById(R.id.btn_request_ride);
        setSupportActionBar(toolbar);

        if (googleServicesAvailable()) {
            Toast.makeText(this, "Connected to the google play services", Toast.LENGTH_SHORT).show();
            intMap();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btnRequestRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void intMap() {
        MapFragment fragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapFragment);
        fragment.getMapAsync(this);
    }


    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int available = api.isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(available)) {
            Dialog dialog = api.getErrorDialog(this, available, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cannot connect to play services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_payments) {
            Intent intent = new Intent(this, PaymentsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_dispatcher) {

            Intent intent = new Intent(this, Dispatcher.class);
            startActivity(intent);

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, History.class);
            startActivity(intent);

        } else if (id == R.id.nav_change_pwd) {
            Intent intent = new Intent(this, ChangePassword.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            logout();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);

        } else {
            builder = new AlertDialog.Builder(this);

        }
        builder.setTitle("Confirm Log Out")
                .setMessage("Are you sure you want to logout ?")
                .setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

//                        userInfo.clearUserInfo();
//                        userSession.setLoggedIn(false);
                        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

//        goToLocation(39.25,-79.14,15);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        //mGoogleMap.addMarker()

    }

    private void goToLocation(double lat, double lng, int zoom) {

        LatLng ll = new LatLng(lat, lng);

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);

        mGoogleMap.moveCamera(update);
        addMarker(lat, lng);
    }

    private void addMarker(double lat, double lng) {
        if (marker !=null){
            removeMarkers();
        }
        MarkerOptions options = new MarkerOptions()
                .title("You are here")
                .position(new LatLng(lat, lng));
        marker = mGoogleMap.addMarker(options);
    }

    private void removeMarkers() {
        marker.remove();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null){
            Toast.makeText(this, "Cannot get current location", Toast.LENGTH_SHORT).show();
        }else{
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,15);
            mGoogleMap.animateCamera(update);

            markerOptions=new MarkerOptions()
                    .title("You are Here")
                    .position(new LatLng(location.getLatitude(),location.getLongitude()));
            mGoogleMap.addMarker(markerOptions);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
