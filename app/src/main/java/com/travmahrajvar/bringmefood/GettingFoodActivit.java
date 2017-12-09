package com.travmahrajvar.bringmefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class GettingFoodActivit extends AppCompatActivity {

    Spinner restList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_food);

        restList = (Spinner) findViewById(R.id.spinner);

        ArrayList<String> list = new ArrayList<>();

        list.add("Starbucks");
        list.add("Tim Hortons");
        list.add("McDonalds");

        adapter = new ArrayAdapter<String>(this, R.layout.spinner_list_style, list);
        restList.setAdapter(adapter);
    }
}
