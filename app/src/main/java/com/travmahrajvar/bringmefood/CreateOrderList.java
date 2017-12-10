package com.travmahrajvar.bringmefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateOrderList extends AppCompatActivity {

    Button btnCloseList;
    EditText txtFoodItem;
    ArrayList<String> foodListArray;
    ListView listOrder;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order_list);

        btnCloseList = (Button) findViewById(R.id.btnCloseList);
        txtFoodItem = (EditText) findViewById(R.id.txtFoodItem);
        listOrder = (ListView) findViewById(R.id.listOrder);

        //set the array for storing food list
        foodListArray = new ArrayList<>();


        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, foodListArray);
        listOrder.setAdapter(arrayAdapter);
    }

    public void onStart(View v){
        super.onStart();

        arrayAdapter.clear();
        arrayAdapter.addAll(foodListArray);
        arrayAdapter.notifyDataSetChanged();
    }

    /*
    * Add Items to list
     */
    public void addOrderItem(View v){
        //makesure the entry is not empty
        if(txtFoodItem.getText().toString().trim().length() != 0){
            foodListArray.add(txtFoodItem.getText().toString());
            Log.i("item", txtFoodItem.getText().toString());

            arrayAdapter.notifyDataSetChanged();

            //clear the input for new entry
            txtFoodItem.setText(null);
        }
    }

    public void updateList(){
        arrayAdapter.clear();
        arrayAdapter.addAll(foodListArray);
        listOrder.setAdapter(arrayAdapter);
    }

    /*
    * Close activity
     */
    public void finishActivityList(View v){
        finish();
    }

}
