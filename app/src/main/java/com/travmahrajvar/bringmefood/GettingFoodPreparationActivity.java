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
	
	public void displayMenu(View view) {
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.getMenuInflater().inflate(R.menu.main_menu_nav, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				if(menuItem.getTitle().toString().equals("Sign Out")){
					FirebaseHandler.signOutCurrentUser();
					finish();
				}
				return true;
			}
		});
		
		popupMenu.show();
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
		    Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
	    }
	}
	
	public void startFoodRun(View view) {
    	//TODO check if boxes are empty and make sure a session code's been generated
    	Intent intent = new Intent(this, GettingFoodActivity.class);
    	startActivity(intent);
	}
}
