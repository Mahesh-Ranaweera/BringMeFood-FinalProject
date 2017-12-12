package com.travmahrajvar.bringmefood;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

import java.util.ArrayList;

public class GettingFoodPreparationActivity extends AppCompatActivity {

    Spinner restList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_food_preparation);

        //restList = (Spinner) findViewById(R.id.spinner);

        ArrayList<String> list = new ArrayList<>();

        list.add("Starbucks");
        list.add("Tim Horton's");
        list.add("McDonald's");

//        adapter = new ArrayAdapter<String>(this, R.layout.spinner_list_style, list);
//        restList.setAdapter(adapter);
    }
    
    public void generateSessionKey(View view) {
	    EditText keybox = findViewById(R.id.txtKeyBox);
	    String restaurant = ((EditText)findViewById(R.id.txtGettingRestaurant)).getText().toString();
	    String location = ((EditText)findViewById(R.id.txtGettingLocation)).getText().toString();
	    
	    keybox.setText(FirebaseHandler.createGettingFoodSession(restaurant, location));
    }
	
	public void copyKeyToClipboard(View view) {
    	EditText keybox = findViewById(R.id.txtKeyBox);
    	if(keybox.getText().toString().trim().length() != 0) {
		    String key = keybox.getText().toString();
		    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		    ClipData clip = ClipData.newPlainText("Session code", key);
		    clipboard.setPrimaryClip(clip);
		    Toast.makeText(this, getString(R.string.clipboard_copied), Toast.LENGTH_SHORT).show();
	    }
	}
	
	public void startFoodRun(View view) {
		String restaurant = ((EditText)findViewById(R.id.txtGettingRestaurant)).getText().toString();
		String location = ((EditText)findViewById(R.id.txtGettingLocation)).getText().toString();
		String key = ((EditText)findViewById(R.id.txtKeyBox)).getText().toString();
		
		//Make sure that the required information isn't missing
		if(restaurant.trim().length() > 0 && location.trim().length() > 0){
			Intent intent = new Intent(this, GettingFoodActivity.class);
			
			//If we don't have a session key yet, create a new one, otherwise use the current one.
			if(key.trim().isEmpty())
				intent.putExtra(getString(R.string.curSessionKey_identifier), FirebaseHandler.createGettingFoodSession(restaurant, location));
			else intent.putExtra(getString(R.string.curSessionKey_identifier), key);
			
			intent.putExtra(getString(R.string.curSessionRestaurant_identifier), restaurant);
			intent.putExtra(getString(R.string.curSessionLocation_identifier), location);
			startActivity(intent);
		} else if(restaurant.trim().length() > 0){
			((EditText)findViewById(R.id.txtGettingRestaurant)).setError(getString(R.string.error_gettingFood_restaurantEmpty));
		} else if(location.trim().length() > 0){
			((EditText)findViewById(R.id.txtGettingLocation)).setError(getString(R.string.error_gettingFood_locationEmpty));
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
					Intent mainPage = new Intent(GettingFoodPreparationActivity.this, ChoiceSelect.class);
					mainPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(mainPage);
				}

				//page signout
				if(menuItem.getTitle().toString().equals(getString(R.string.menu_signOut))){
					//Sign out user
					FirebaseHandler.signOutCurrentUser();

					//close all intents and goto main
					Intent mainPage = new Intent(GettingFoodPreparationActivity.this, WelcomeActivity.class);
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
