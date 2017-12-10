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

public class NeedFoodActivity extends AppCompatActivity {

    TextView windowTxt;
    ImageButton mainMenu;

    //start activity for data
    static final int GET_DATA = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_food);

        windowTxt = (TextView) findViewById(R.id.windowTitle);
        windowTxt.setText("Want Food");
        mainMenu = (ImageButton) findViewById(R.id.mainMenuButton);

    }

    public void orderListPage(View v){
        Intent displayListPage = new Intent(this, CreateOrderList.class);
        startActivityForResult(displayListPage, GET_DATA);
    }

    /*
    * Handle activity retrun result
     */
    protected void onActivtyResult(int reqCode, int resCode, Intent data){
        //onActivtyResult(resCode, resCode, data);

        if(reqCode == GET_DATA){
            if(resCode == RESULT_OK){
                //get data from list

            }
        }
    }

    /*
    * Display main menu
     */
    public void displayMenu(View v){
        PopupMenu popupMenu = new PopupMenu(NeedFoodActivity.this, mainMenu);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu_nav, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.i("selected", menuItem.getTitle().toString());
                return true;
            }
        });

        popupMenu.show();
    }
}
