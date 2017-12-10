package com.travmahrajvar.bringmefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateOrderList extends AppCompatActivity {

    Button btnCloseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order_list);

        btnCloseList = (Button) findViewById(R.id.btnCloseList);
    }

    public void finishActivityList(View v){
        finish();
    }
}
