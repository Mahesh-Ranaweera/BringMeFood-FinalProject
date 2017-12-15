package com.travmahrajvar.bringmefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

import java.util.Map;

public class ChoiceSelect extends AppCompatActivity {

    TextView windowTxt, txtUserName;
    ImageButton mainMenu;
    
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the app to full screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choice_select);

        windowTxt = (TextView) findViewById(R.id.windowTitle);
        windowTxt.setText(R.string.app_name);
        mainMenu = (ImageButton) findViewById(R.id.mainMenuButton);
        txtUserName = (TextView) findViewById(R.id.txtUserName);

        FirebaseUser user = FirebaseHandler.getCurrentUser();

        if(user != null){
            userID = user.getUid();
            txtUserName.setText(getString(R.string.welcome_with_username, user.getDisplayName()));
        }
    }
	
	/**
	 * Checks if we have a previous food-getting session,
	 *  and resumes that session in the "Getting food" activity,
	 *  or sends them to the "Getting food preparation" activity if not.
	 * @param v
	 */
    public void gettingFood(View v){
	    FirebaseDatabase.getInstance().getReference().child("getting").orderByChild("getter")
			    .equalTo(FirebaseHandler.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {
			    if(dataSnapshot.getChildrenCount() > 0) { //There's a previous unclosed session
			    	//Get the response from Firebase
				    Map<String, Object> prevSessionInfo = ((Map<String, Object>)(dataSnapshot.getValue()));
				    
				    //Just in case we crash, we can see what the data we got back was.
				    Crashlytics.log("dataSnapshot: " + prevSessionInfo.toString());
				
				    //Get the session info to put into the intent
				    Map.Entry<String,Object> sessionKeyEntry = prevSessionInfo.entrySet().iterator().next();
				    String sessionKey = sessionKeyEntry.getKey();
				    prevSessionInfo = (Map<String, Object>) sessionKeyEntry.getValue();
				    
				    //Set up the intent and go
				    Intent getFoodPage = new Intent(getApplicationContext(), GettingFoodActivity.class);
				    getFoodPage.putExtra(getString(R.string.curSessionKey_identifier), sessionKey);
				    getFoodPage.putExtra(getString(R.string.curSessionRestaurant_identifier), prevSessionInfo.get("restaurant").toString());
				    getFoodPage.putExtra(getString(R.string.curSessionLocation_identifier), prevSessionInfo.get("location").toString());
				    startActivity(getFoodPage);
			    } else { //There are no previous sessions
				    Intent getFoodPrepPage = new Intent(getApplicationContext(), GettingFoodPreparationActivity.class);
				    startActivity(getFoodPrepPage);
			    }
		    }
		
		    @Override
		    public void onCancelled(DatabaseError databaseError) { }
	    });
    }
	
	/**
	 * Sends the user to the "Want Food" activity
	 * @param v
	 */
	public void wantFood(View v){
        Intent getNeedPage = new Intent(this, NeedFoodActivity.class);
        startActivity(getNeedPage);
    }

	/**
	 * Action for Adding Managing friends
	 */
	public void manageFriends(View v){
		Intent getFriendPage = new Intent(this, FriendManageList.class);
		startActivity(getFriendPage);
	}

	/**
	 * Sets up and displays the sidebar menu
	 * @param view
	 */
	public void displayMenu(View view) {
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.getMenuInflater().inflate(R.menu.choice_menu_nav, popupMenu.getMenu());

		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				if(menuItem.getTitle().toString().equals(getString(R.string.menu_signOut))){
					//Sign out user
					FirebaseHandler.signOutCurrentUser();

					//close all intents and goto main
					Intent mainPage = new Intent(ChoiceSelect.this, WelcomeActivity.class);
					mainPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(mainPage);
					//finish();
				}
                if(menuItem.getTitle().toString().equals(getString(R.string.menu_balance))){
                    //close all intents and goto main
                    Intent accountPage = new Intent(ChoiceSelect.this, MyAccount.class);
                    accountPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(accountPage);
                }
				return true;
			}
		});
		
		popupMenu.show();
	}
}
