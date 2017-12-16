package com.travmahrajvar.bringmefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travmahrajvar.bringmefood.utils.AgentAdapter;
import com.travmahrajvar.bringmefood.utils.Agents;
import com.travmahrajvar.bringmefood.utils.FirebaseHandler;
import com.travmahrajvar.bringmefood.utils.UserAdapter;
import com.travmahrajvar.bringmefood.utils.Users;

import java.util.ArrayList;
import java.util.Map;

public class FriendManageList extends AppCompatActivity {

    //firebase stuff
    private DatabaseReference mRefUsers, mRefUsers2;

    //ui import
    ListView listUsers;

    //Array adapter
    ArrayAdapter userAdapter;

    //Agent arraylist
    ArrayList<Users> userslist;

    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_manage_list);

        //set the agents array
        userslist = new ArrayList<Users>();

        //access getting table
        mRefUsers = FirebaseDatabase.getInstance().getReference().child("users");

        listUsers = (ListView) findViewById(R.id.userListView);

        //add adapter to listview
        userAdapter = new UserAdapter(this, userslist);
        listUsers.setAdapter(userAdapter);

        //get the user list and update the adpater
        getDBuserList();
    }

    /**
     * Get the user list for the user and update adapter
     */
    public void getDBuserList(){
        if(mRefUsers != null) {
            mRefUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null)
                        collectUsers((Map<String, Object>) dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     * Get users information from the database
     * @param usersInfo
     */
    private void collectUsers(Map<String, Object> usersInfo){

        userAdapter.clear();

        //iterate through the recieved objects
        for(Map.Entry<String, Object> entry : usersInfo.entrySet()){

            //makesure current user is excluded from the list
            if(!FirebaseHandler.getCurrentUser().getUid().equals(entry.getKey())){
                Map row = (Map) entry.getValue();
                    Log.i("data", "data" + row);

                    //create the agent object
                    user = new Users(entry.getKey(), row.get("name").toString(), row.get("deviceToken").toString());
                    userAdapter.add(user);
            }
        }

        //update the agentadapter
        userAdapter.notifyDataSetChanged();
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
                    Intent mainPage = new Intent(FriendManageList.this, ChoiceSelect.class);
                    mainPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainPage);
                }

                //Getting Balance
                if(menuItem.getTitle().toString().equals(getString(R.string.menu_balance))){
                    //close all intents and goto main
                    Intent accountPage = new Intent(FriendManageList.this, MyAccount.class);
                    accountPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(accountPage);
                }

                //page signout
                if(menuItem.getTitle().toString().equals(getString(R.string.menu_signOut))){
                    //Sign out user
                    FirebaseHandler.signOutCurrentUser();

                    //close all intents and goto main
                    Intent mainPage = new Intent(FriendManageList.this, WelcomeActivity.class);
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
