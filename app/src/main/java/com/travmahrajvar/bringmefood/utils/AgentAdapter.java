package com.travmahrajvar.bringmefood.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travmahrajvar.bringmefood.FindDeliveryAgents;
import com.travmahrajvar.bringmefood.R;
import com.travmahrajvar.bringmefood.WelcomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by mi6 on 12/11/17.
 */

public class AgentAdapter extends ArrayAdapter<Agents> {

    public DatabaseReference mRefAgent;
    private String agentDevice;
    final String currentUserID = FirebaseHandler.getCurrentUser().getUid();

    public AgentAdapter(Context context, ArrayList<Agents> agents){
        super(context, 0, agents);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //get the item position
        final Agents agent = getItem(position);

        //check current view is used, else use inflator
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.agent_adapter_view, parent, false);
        }

        TextView strRestaurant = (TextView) convertView.findViewById(R.id.strRestaurant);
        TextView strAgentName    = (TextView) convertView.findViewById(R.id.strAgentName);
        TextView strLocation   = (TextView) convertView.findViewById(R.id.strAgentLocation);
        Button agentReqBtn = (Button) convertView.findViewById(R.id.agentReqBtn);

        //set button text dynamically
        agentReqBtn.setText("REQUEST");

        agentReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add request into the db
                setDBRequest(agent.foodRunID, currentUserID, agent.getAgentTocken());

                //postRequest(agent.getAgentTocken());
                //Log.i("clicked", "adapter click"+agent.getUserID());
                //Toast.makeText(getContext(),"Messaged: "+agent.getAgentName() , Toast.LENGTH_SHORT).show();
            }
        });

        //add values
        strAgentName.setText(agent.getAgentName());
        strLocation.setText(agent.getLocation());
        strRestaurant.setText(agent.getResturant());

        //return the view
        return convertView;
    }


    public void setDBRequest(final String reqAgentID, final String currUserID, final String agentTocken) {
        mRefAgent = FirebaseDatabase.getInstance().getReference().child("getting").child(reqAgentID).child("wanterlist");
        //Log.i("firebase", "datanull"+mRefAgent);

        final ArrayList<String> sendWanterArr = new ArrayList<>();
        sendWanterArr.add(currUserID);

        if (mRefAgent != null) {

            mRefAgent.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        if(dataSnapshot.getValue() instanceof ArrayList){
                            ArrayList<String> current = (ArrayList<String>) dataSnapshot.getValue();

                            if(checkID(current)){
                                Toast.makeText(getContext(), R.string.message_already_sent, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), R.string.sent_message, Toast.LENGTH_SHORT).show();
                                current.addAll(sendWanterArr);
                                updateDB(current, reqAgentID, agentTocken);
                            }
                        }else if(dataSnapshot.getValue() instanceof HashMap){
                            HashMap<String, Object> current = (HashMap) dataSnapshot.getValue();

                            ArrayList<String> curr = new ArrayList<>();

                            //iterate the hashmap
                            Iterator iterator = current.entrySet().iterator();
                            while(iterator.hasNext()){
                                Map.Entry val = (Map.Entry) iterator.next();
                                curr.add(val.getValue().toString());
                            }

                            if(checkID(curr)){
                                Toast.makeText(getContext(), R.string.message_already_sent, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), R.string.sent_message, Toast.LENGTH_SHORT).show();
                                curr.addAll(sendWanterArr);
                                updateDB(curr, reqAgentID, agentTocken);
                            }
                        }else{

                        }

                    }else{
                        updateDB(sendWanterArr, reqAgentID, agentTocken);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    //check duplicate data exists
    public boolean checkID(ArrayList<String> listArr){

        List<String> list = listArr;

        if(list.contains(currentUserID)){
            return true;
        }
        return  false;
    }

    //update the db
    public void updateDB(ArrayList<String> sendWanterArr, String reqAgentID, String agentTocken){

        //send the notification
        postRequest(agentTocken);

        //update the database
        FirebaseHandler.updateAgentWantList(sendWanterArr, reqAgentID);
    }


    //request the notification
    public void postRequest(String agentTocken){

        //set the device token
        agentDevice = agentTocken;

        try{
            String data = new pushNotification().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    //do the async task for notification
    class pushNotification extends AsyncTask<String, Void, String> {

        public String doInBackground(String... params){

            String getResponse = "";

            String postLink = "https://fcm.googleapis.com/fcm/send";

            String FCMKEY = getContext().getResources().getString(R.string.fcm_api_key);

            try {
                URL url = new URL(postLink);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", FCMKEY);
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonOut = new JSONObject();
                jsonOut.put("to", agentDevice);

                //nested message section
                JSONObject notificationBody = new JSONObject();
                notificationBody.put("body", "Firebase");
                notificationBody.put("message", "TestMessage");
                notificationBody.put("priority", "10");
                jsonOut.put("notification", notificationBody);

                Log.i("json", jsonOut.toString());

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonOut.toString());
                os.flush();
                os.close();

                Log.i("MSG", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());
                getResponse = conn.getResponseMessage();

                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return getResponse;
        }
    }
}
