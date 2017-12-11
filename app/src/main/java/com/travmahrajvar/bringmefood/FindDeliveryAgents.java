package com.travmahrajvar.bringmefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Map;

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
            agent = new Agents(row.get("getter").toString(), row.get("location").toString(), row.get("restaurant").toString());
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
}
