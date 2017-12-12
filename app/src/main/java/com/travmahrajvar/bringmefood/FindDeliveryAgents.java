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
    ImageButton btnSearchAgents;
    EditText txtAgentSearch;

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
        selectionRadio = (RadioGroup) findViewById(R.id.selectionGroup);
        btnSearchAgents = (ImageButton) findViewById(R.id.btnSearchAgents);
        txtAgentSearch = (EditText) findViewById(R.id.txtAgentSearch);

        //add adapter to listview
        agentAdapter = new AgentAdapter(this, agents);
        listAgents.setAdapter(agentAdapter);

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
            agent = new Agents(row.get("getter").toString(), row.get("location").toString(), row.get("restaurant").toString(), row.get("deviceTok").toString(), row.get("gettername").toString());
            agentAdapter.add(agent);
        }

        //update the agentadapter
        agentAdapter.notifyDataSetChanged();
    }

    public void searchAgents(View v){
        int selected = selectionRadio.getCheckedRadioButtonId();
        choice = (RadioButton) findViewById(selected);

        Log.i("radio", "radioID: "+selected);

        if(selected == 2131296312){

        }else if(selected == 2131296313){

        }else{

        }
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

//    //request the notification
//    public void postRequest(View view){
//        try{
//            String data = new pushNotification().execute().get();
//            Log.i("postreq", data);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //do the async task for notification
//    class pushNotification extends AsyncTask<String, Void, String>{
//
//        public String doInBackground(String... params){
//
//                String getResponse = "";
//
//                String postLink = "https://fcm.googleapis.com/fcm/send";
//
//                try {
//                    URL url = new URL(postLink);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("POST");
//                    conn.setRequestProperty("Content-Type", "application/json");
//                    conn.setRequestProperty("Authorization", getString(R.string.fcm_api_key));
//                    conn.setDoOutput(true);
//                    conn.setDoInput(true);
//
//                    JSONObject jsonOut = new JSONObject();
//                    jsonOut.put("to", "<deviceTock from firbase database entry>");
//
//                    //nested message section
//                    JSONObject notificationBody = new JSONObject();
//                    notificationBody.put("body", "Firebase");
//                    notificationBody.put("message", "TestMessage");
//                    notificationBody.put("priority", "10");
//                    jsonOut.put("notification", notificationBody);
//
//                    Log.i("json", jsonOut.toString());
//
//                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//                    os.writeBytes(jsonOut.toString());
//                    os.flush();
//                    os.close();
//
//                    Log.i("MSG", String.valueOf(conn.getResponseCode()));
//                    Log.i("MSG", conn.getResponseMessage());
//                    getResponse = conn.getResponseMessage();
//
//                    conn.disconnect();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return getResponse;
//        }
//    }
}
