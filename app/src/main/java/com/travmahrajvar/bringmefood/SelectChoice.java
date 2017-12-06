package com.travmahrajvar.bringmefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_choice);
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
