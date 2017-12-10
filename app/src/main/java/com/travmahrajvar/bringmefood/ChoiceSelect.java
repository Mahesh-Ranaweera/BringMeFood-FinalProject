package com.travmahrajvar.bringmefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class ChoiceSelect extends AppCompatActivity {

    TextView windowTxt, txtUserName;
    ImageButton mainMenu;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the app to full screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choice_select);

        windowTxt = (TextView) findViewById(R.id.windowTitle);
        windowTxt.setText("Bring Me Food");
        mainMenu = (ImageButton) findViewById(R.id.mainMenuButton);
        txtUserName = (TextView) findViewById(R.id.txtUserName);

        //get usersID to addto database on initial login
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            userID = user.getUid();
            //update the users data based on id
            updateUserData(userID);
        }
    }


    /*
    * add user data to db if not exists
     */
    public void updateUserData(String userID){
        myRef.child("users").child(userID).child("email").setValue(FirebaseHandler.getCurrentUser().getEmail());
    }


    /*
    * Getting food
    */
    public void gettingFood(View v){
        Intent getFoodPage = new Intent(this, GettingFoodActivity.class);
        startActivity(getFoodPage);

    }

    /*
    * Need food
     */
    public void wantFood(View v){
        Intent getNeedPage = new Intent(this, NeedFoodActivity.class);
        startActivity(getNeedPage);
    }


    /*
    * Display main menu
     */
    public void displayMenu(View v){
        PopupMenu popupMenu = new PopupMenu(ChoiceSelect.this, mainMenu);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu_nav, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.i("selected", menuItem.getTitle().toString());

                if(menuItem.getTitle().toString().equals("Sign Out")){
                    FirebaseHandler.signOutCurrentUser();
                    finish();
                }
                return true;
            }
        });

        popupMenu.show();
    }
}
