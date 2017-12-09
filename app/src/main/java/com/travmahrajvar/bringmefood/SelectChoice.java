package com.travmahrajvar.bringmefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

public class SelectChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_choice);

        FirebaseUser user = FirebaseHandler.getCurrentUser();

        if(user != null){
            //user signed in
            Log.i("signed", "User signed in");
        }else{
            //user not signed
            Log.i("signed", "User not signed in");
        }
    }

    /*
    Getting food
     */
    public void gettingFood(View v){
        Intent getFoodPage = new Intent(this, GettingFoodActivit.class);
        startActivity(getFoodPage);

    }

    /*
    Need food
     */
    public void needFood(View v){
        Intent getNeedPage = new Intent(this, NeedFoodActivity.class);
        startActivity(getNeedPage);
    }
}
