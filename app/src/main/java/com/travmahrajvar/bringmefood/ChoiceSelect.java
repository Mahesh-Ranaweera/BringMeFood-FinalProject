package com.travmahrajvar.bringmefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

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
	 * Action for "Getting food"
	 * @param v
	 */
    public void gettingFood(View v){
        Intent getFoodPage = new Intent(this, GettingFoodPreparationActivity.class);
        startActivity(getFoodPage);

    }
	
	/**
	 * Action for "Wanting food"
	 * @param v
	 */
	public void wantFood(View v){
        Intent getNeedPage = new Intent(this, NeedFoodActivity.class);
        startActivity(getNeedPage);
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
				return true;
			}
		});
		
		popupMenu.show();
	}
}
