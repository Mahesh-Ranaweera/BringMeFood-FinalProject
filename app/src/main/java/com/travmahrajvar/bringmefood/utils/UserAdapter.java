package com.travmahrajvar.bringmefood.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travmahrajvar.bringmefood.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by mi6 on 12/12/17.
 */

public class UserAdapter extends ArrayAdapter<Users> {

    private DatabaseReference mRefFriends;
    public ArrayList<String> currFriendID = new ArrayList<String>();

    public UserAdapter(Context context, ArrayList<Users> users) {
        super(context, 0, users);

        //get current data from db
        getDBFriendsList();
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
        final Button userAddBtn = (Button) convertView.findViewById(R.id.userAddBtn);

        final boolean[] exists = {false};

        if(!checkID(user.getUserID())){
            exists[0] = true;

            //set button text dynamically
            userAddBtn.setText("Add Friend");
        }else{
            //set button text dynamically
            userAddBtn.setText("Remove Friend");
        }

        final boolean[] finalExists = {exists[0]};
        userAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //update the list to check data is updtotime
                getDBFriendsList();

                if(finalExists[0]){
                    //put the current friend id to array
                    currFriendID.add(user.getUserID());
                    updateDB();
                    finalExists[0] = false;
                    userAddBtn.setText("Remove Friend");
                    Toast.makeText(getContext(), "User Added", Toast.LENGTH_SHORT).show();
                }else{
                    currFriendID.remove(user.getUserID());
                    updateDB();
                    finalExists[0] = false;
                    userAddBtn.setText("Add Friend");
                    Toast.makeText(getContext(), "User Deleted", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //add values
        strUserName.setText(user.getUserName());

        //return the view
        return convertView;
    }


    public void getDBFriendsList() {
        mRefFriends = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseHandler.getCurrentUser().getUid()).child("friendlist");

        ArrayList<String> returnArr = new ArrayList<>();

        if (mRefFriends != null) {
            Log.i("dbvalues", "friends" + mRefFriends);

            mRefFriends.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        Log.i("getdb", "data"+dataSnapshot.getValue());
                        collectFriends((ArrayList<String>) dataSnapshot.getValue());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    public void collectFriends(ArrayList<String> currData){
        //clear current array
        currFriendID.clear();

        for(int i=0; i < currData.size(); i++){
            //add the new data to array
            currFriendID.add(currData.get(i));
        }
    }

    //check if the id exists and returns true
    public boolean checkID(String id){

        List<String> list = currFriendID;

        if(list.contains(id)){
            return true;
        }
        return  false;
    }

    //update the database entry
    public void updateDB(){
        //append to friend list
        FirebaseHandler.updateFriendList(currFriendID);
    }
}