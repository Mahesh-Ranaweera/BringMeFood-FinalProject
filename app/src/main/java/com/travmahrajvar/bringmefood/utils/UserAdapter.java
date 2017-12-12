package com.travmahrajvar.bringmefood.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.travmahrajvar.bringmefood.R;

import java.util.ArrayList;

/**
 * Created by mi6 on 12/12/17.
 */

public class UserAdapter extends ArrayAdapter<Users> {

    public UserAdapter(Context context, ArrayList<Users> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the item position
        final Users user = getItem(position);

        //check current view is used, else use inflator
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_adapter_view, parent, false);
        }

        TextView strUserName = (TextView) convertView.findViewById(R.id.strUserName);
        Button userAddBtn = (Button) convertView.findViewById(R.id.userAddBtn);

        //set button text dynamically
        userAddBtn.setText("Add Friend");

        userAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //append to friend list
                FirebaseHandler.updateFriendList(user.getUserID());
                Toast.makeText(getContext(), "User Added", Toast.LENGTH_SHORT).show();
            }
        });

        //add values
        strUserName.setText(user.getUserName());

        //return the view
        return convertView;
    }
}