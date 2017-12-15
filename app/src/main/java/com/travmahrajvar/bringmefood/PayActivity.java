package com.travmahrajvar.bringmefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

public class PayActivity extends AppCompatActivity {
EditText payment;
String uid;
int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        payment = (EditText) findViewById(R.id.addBalance);
        uid = getIntent().getStringExtra("uid");
    }

    public void chargeme(View view) {
        price = Integer.parseInt(payment.getText().toString());
        System.out.println(uid);
        Toast.makeText(PayActivity.this, "Charge Successful",Toast.LENGTH_SHORT).show();
        FirebaseHandler.transaction(price,uid);
    }
}
