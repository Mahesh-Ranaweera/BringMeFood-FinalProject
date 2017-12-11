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
import android.widget.Toast;

import com.travmahrajvar.bringmefood.utils.FirebaseHandler;
import com.travmahrajvar.bringmefood.utils.NeedData;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NeedFoodActivity extends AppCompatActivity {

    ImageButton mainMenu;
    EditText txtDeliveryLocation;
    ImageButton btnGetCurrLoc;

    //handle the google maps
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude, longitude;
    Geocoder geocoder;
    List<Address> addresses;

    //order list
    ArrayList<String> USERFOODARR;

    //NeedData object
    NeedData needData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_food);

        mainMenu = (ImageButton) findViewById(R.id.mainMenuButton);
        txtDeliveryLocation = (EditText) findViewById(R.id.txtDelivery);
        btnGetCurrLoc = (ImageButton) findViewById(R.id.btnGetCurrLoc);

        //declare food array
        USERFOODARR = new ArrayList<>();

        geocoder = new Geocoder(this, Locale.getDefault());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    if(addresses.get(0).getAddressLine(0) != null){
                        txtDeliveryLocation.setText(addresses.get(0).getAddressLine(0));
                    }else{
                        Toast toast = Toast.makeText(NeedFoodActivity.this, "Could not find your location, Try Again", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 100);
                return;
            }
        else{
            getCurrLocation();
        }
    }


    /*
    * Order page intent
     */
    public void orderListPage(View v){
        Intent displayListPage = new Intent(this, CreateOrderList.class);
        displayListPage.putExtra("CURARR", USERFOODARR);
        startActivityForResult(displayListPage, 1);
    }

    /*
    * Handle activity return result
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                USERFOODARR = (ArrayList<String>) bundle.getSerializable("USERFOODLIST");
                Log.i("arr", "size"+USERFOODARR.size());
            }
        }
    }

    /*
    * Display main menu
     */
    public void displayMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu_nav, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle().toString().equals(getString(R.string.menu_signOut))){
                    //Sign out user
                    FirebaseHandler.signOutCurrentUser();
                    finish();
                }
                return true;
            }
        });
    
        popupMenu.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] perms, int[] results) {
        switch (requestCode){
            case 100:
                if(results.length >0 && results[0] == PackageManager.PERMISSION_GRANTED)
                    getCurrLocation();
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
                            0, 0, locationListener);
                    Log.i("map", "requestLocationUpdates()");
                }
            }
        });
    }

    /*
    * Order page intent
     */
    public void deliveryAgentPage(View v){
        Intent deliveryAgent = new Intent(this, FindDeliveryAgents.class);
        startActivity(deliveryAgent);
    }
}
