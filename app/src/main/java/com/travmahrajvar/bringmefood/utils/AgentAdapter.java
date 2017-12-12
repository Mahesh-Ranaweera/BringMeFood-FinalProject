package com.travmahrajvar.bringmefood.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.travmahrajvar.bringmefood.R;
import com.travmahrajvar.bringmefood.WelcomeActivity;

import java.util.ArrayList;

/**
 * Created by mi6 on 12/11/17.
 */

public class AgentAdapter extends ArrayAdapter<Agents> {

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
        agentReqBtn.setText("Message");

        agentReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i("clicked", "adapter click"+agent.getUserID());
                Toast.makeText(getContext(),"Messaged: "+agent.getAgentName() , Toast.LENGTH_SHORT).show();
            }
        });

        //add values
        strAgentName.setText(agent.getAgentName());
        strLocation.setText(agent.getLocation());
        strRestaurant.setText(agent.getResturant());

        //return the view
        return convertView;
    }

}
