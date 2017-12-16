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
        uid = getIntent().getStringExtra(getString(R.string.uid));
    }

    //Charges the wanter the price of the requested items as set by getter
    public void chargeme(View view) {
        price = Integer.parseInt(payment.getText().toString());
        Toast.makeText(PayActivity.this, R.string.charge_success,Toast.LENGTH_SHORT).show();
        FirebaseHandler.transaction(price,uid);
    }
}
