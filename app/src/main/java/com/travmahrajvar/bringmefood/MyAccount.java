package com.travmahrajvar.bringmefood;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

import java.math.BigDecimal;

public class MyAccount extends AppCompatActivity {
    TextView response;
    EditText price;

    //Paypal payment gateway configuration information
    PayPalConfiguration m_configuration;
    String m_paypalClientId = "ATThx2GdqRyhVJb1mMgGfVsSBX3iI_dq0NDThbxkmfLlFwgcVATgWmWcwia1rVZiBmDUyPA60AIg4Dwq";
    Intent m_service;
    int m_paypalRequestCode = 999;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //Displays the user's current balance
        response = (TextView) findViewById(R.id.txtBalance);
        price = (EditText) findViewById(R.id.addBalance);
        response.setText("$ "+FirebaseHandler.getBalance(FirebaseHandler.getCurrentUser().getUid()));

        //setup the paypal environment
        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId((m_paypalClientId));

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configuration);
        startService(m_service);
    }

    //Redirect to the paypal's payment platform once user agrees to refill wallet
    void pay(View view){
        PayPalPayment payment = new PayPalPayment(new BigDecimal(Integer.parseInt(price.getText().toString())),getString(R.string.currency),getString(R.string.reload),
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent (this,PaymentActivity.class);
        intent.putExtra (PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        startActivityForResult(intent,m_paypalRequestCode);

    }


    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == m_paypalRequestCode){
            if(resultCode == Activity.RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirmation !=null){
                    String state = confirmation.getProofOfPayment().getState();

                    //Once the payment is successfull, creates a transaction to the firebase database
                    if (state.equals(getString(R.string.approved))){
                        Toast.makeText(MyAccount.this, R.string.paymentAdded,Toast.LENGTH_SHORT).show();
                        FirebaseHandler.transaction((Integer.parseInt(price.getText().toString())),null);
                    }
                    else{
                        Toast.makeText(MyAccount.this, R.string.transac_unsuccessful,Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(MyAccount.this, R.string.confirmFail,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
