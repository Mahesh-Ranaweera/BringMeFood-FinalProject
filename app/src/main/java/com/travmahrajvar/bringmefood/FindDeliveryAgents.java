package com.travmahrajvar.bringmefood;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travmahrajvar.bringmefood.utils.AgentAdapter;
import com.travmahrajvar.bringmefood.utils.Agents;
import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FindDeliveryAgents extends AppCompatActivity {

    //firebase stuff
    private DatabaseReference mRef;

    //ui import
    ListView listAgents;
    RadioGroup selectionRadio;
    RadioButton choice;

    //ArrayAdapter
    AgentAdapter agentAdapter;

    //Agent arraylist
    ArrayList<Agents> agents;
    Agents agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_delivery_agents);

        //set the agents array
        agents = new ArrayList<Agents>();

        //access getting table
        mRef = FirebaseDatabase.getInstance().getReference().child("getting");

        listAgents = (ListView) findViewById(R.id.listAgents);

        //add adapter to listview
        agentAdapter = new AgentAdapter(this, agents);
        listAgents.setAdapter(agentAdapter);

        //listen to db and update list
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i("dbvalues", "data: "+dataSnapshot.getValue());
                if(dataSnapshot.getValue() != null)
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

            //create the agent object
            agent = new Agents(row.get("getter").toString(), row.get("location").toString(), row.get("restaurant").toString(), row.get("deviceTok").toString(), row.get("gettername").toString(), entry.getKey());
            agentAdapter.add(agent);
        }

        //update the agentadapter
        agentAdapter.notifyDataSetChanged();
    }

    /**
     * Sets up and displays the sidebar menu
     * @param view
     */
    public void displayMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.global_menu_nav, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                //goback to previous page
                if(menuItem.getTitle().toString().equals(getString(R.string.go_back))){
                    //finish page
                    finish();
                }

                //goback to choice page
                if(menuItem.getTitle().toString().equals(getString(R.string.home))){
                    //close all intents and goto main
                    Intent mainPage = new Intent(FindDeliveryAgents.this, ChoiceSelect.class);
                    mainPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainPage);
                }

                //page signout
                if(menuItem.getTitle().toString().equals(getString(R.string.menu_signOut))){
                    //Sign out user
                    FirebaseHandler.signOutCurrentUser();

                    //close all intents and goto main
                    Intent mainPage = new Intent(FindDeliveryAgents.this, WelcomeActivity.class);
                    mainPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainPage);
                    //finish();
                }
                return true;
            }
        });

        popupMenu.show();
    }
}
