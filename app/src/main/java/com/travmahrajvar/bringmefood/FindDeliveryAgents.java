package com.travmahrajvar.bringmefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

import java.util.ArrayList;
import java.util.Map;

public class FindDeliveryAgents extends AppCompatActivity {

    //firebase stuff
    private DatabaseReference mRef;

    //ui import
    ListView listAgents;
    CheckBox checkLocation, checkRestaurant;
    ImageButton btnSearchAgents;
    EditText txtAgentSearch;

    //ArrayAdapter
    ArrayAdapter<String> arrayAdapter;

    //ArrayLists
    ArrayList<String> location;
    ArrayList<String> getterID;
    ArrayList<String> restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_delivery_agents);

        location = new ArrayList<>();
        getterID = new ArrayList<>();
        restaurant = new ArrayList<>();

        mRef = FirebaseDatabase.getInstance().getReference().child("getting");

        listAgents = (ListView) findViewById(R.id.listAgents);
        checkLocation = (CheckBox) findViewById(R.id.checkLocation);
        checkRestaurant = (CheckBox) findViewById(R.id.checkRestaurant);
        btnSearchAgents = (ImageButton) findViewById(R.id.btnSearchAgents);
        txtAgentSearch = (EditText) findViewById(R.id.txtAgentSearch);

        //listview
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, location);
        listAgents.setAdapter(arrayAdapter);

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i("dbvalues", "data: "+dataSnapshot.getValue());
                collectGetters((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void collectGetters(Map<String, Object> getters){
        //iterate through the recieved objects
        for(Map.Entry<String, Object> entry : getters.entrySet()){
            Map row = (Map) entry.getValue();
            getterID.add((String) row.get("getter"));
            location.add((String) row.get("location"));
            restaurant.add((String) row.get("restaurant"));
        }

        //update the arrayadapter
        arrayAdapter.notifyDataSetChanged();

        System.out.println(location.toString());
    }
}
