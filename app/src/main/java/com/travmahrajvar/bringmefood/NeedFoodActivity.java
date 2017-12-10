package com.travmahrajvar.bringmefood;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NeedFoodActivity extends AppCompatActivity {

    TextView windowTxt;
    ImageButton mainMenu;
    EditText txtDeliveryLocation;
    ImageButton btnGetCurrLoc;

    //handle the google maps
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude, longitude;
    private static final int REQUEST_GEOLOCATION_PERMS = 100;

    //start activity for data
    static final int GET_DATA = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_food);

        windowTxt = (TextView) findViewById(R.id.windowTitle);
        windowTxt.setText("Want Food");
        mainMenu = (ImageButton) findViewById(R.id.mainMenuButton);
        txtDeliveryLocation = (EditText) findViewById(R.id.txtDelivery);
        btnGetCurrLoc = (ImageButton) findViewById(R.id.btnGetCurrLoc);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Log.i("data", "Lat: "+latitude);


                txtDeliveryLocation.setText(Double.toString(latitude));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent enableGEO = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(enableGEO);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 100);
                return;
            }
        }else{
            getCurrLocation();
        }
    }

    public void orderListPage(View v){
        Intent displayListPage = new Intent(this, CreateOrderList.class);
        startActivityForResult(displayListPage, GET_DATA);
    }

    /*
    * Handle activity retrun result
     */
    protected void onActivtyResult(int reqCode, int resCode, Intent data){
        //onActivtyResult(resCode, resCode, data);

        if(reqCode == GET_DATA){
            if(resCode == RESULT_OK){
                //get data from list

            }
        }
    }

    /*
    * Display main menu
     */
    public void displayMenu(View v){
        PopupMenu popupMenu = new PopupMenu(NeedFoodActivity.this, mainMenu);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu_nav, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.i("selected", menuItem.getTitle().toString());
                return true;
            }
        });

        popupMenu.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] perms, int[] results) {

        switch (requestCode){
            case 100:
                if(results.length >0 && results[0] == PackageManager.PERMISSION_GRANTED){
                    getCurrLocation();
                }
                return;
        }
    }

    private void getCurrLocation() {
        btnGetCurrLoc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i("clicked", "clicked");
                String recommendedProvider = "gps";

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(recommendedProvider,
                            5000, 0, locationListener);
                    Log.i("map", "requestLocationUpdates()");
                }
            }
        });
    }


}
