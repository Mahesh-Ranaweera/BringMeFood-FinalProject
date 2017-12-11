package com.travmahrajvar.bringmefood.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.travmahrajvar.bringmefood.R;

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
        Agents agent = getItem(position);

        //check current view is used, else use inflator
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.agent_adapter_view, parent, false);
        }

        TextView strRestaurant = (TextView) convertView.findViewById(R.id.strRestaurant);
        TextView strAgentName    = (TextView) convertView.findViewById(R.id.strAgentName);
        TextView strLocation   = (TextView) convertView.findViewById(R.id.strAgentLocation);

        //add values
        strAgentName.setText(agent.getUserID());
        strLocation.setText(agent.getLocation());
        strRestaurant.setText(agent.getResturant());

        //return the view
        return convertView;
    }

}
