package com.travmahrajvar.bringmefood;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText txtEmail, txtUsername, txtFName, txtLName, txtPassw1, txtPassw2;

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    Map<String, String> userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child("users");

        userInfo = new HashMap<>();

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtFName = (EditText) findViewById(R.id.txtFName);
        txtLName = (EditText) findViewById(R.id.txtLName);
        txtPassw1 = (EditText) findViewById(R.id.txtPassw1);
        txtPassw2 = (EditText) findViewById(R.id.txtPassw2);
    }

    public void signUPUser(View v){
        if(txtEmail.getText().toString().trim().length() == 0){
            txtEmail.setError("Enter Email");
            txtEmail.requestFocus();
        }

        if(txtUsername.getText().toString().trim().length() == 0){
            txtUsername.setError("Enter Username");
            txtUsername.requestFocus();
        }

        if(txtFName.getText().toString().trim().length() == 0){
            txtFName.setError("Enter First Name");
            txtFName.requestFocus();
        }

        if(txtLName.getText().toString().trim().length() == 0){
            txtLName.setError("Enter Last Name");
            txtLName.requestFocus();
        }

        if(txtPassw1.getText().toString().trim().length() == 0){
            txtPassw1.setError("Enter Password");
            txtPassw1.requestFocus();
        }

        if(txtPassw2.getText().toString().trim().length() == 0){
            txtPassw2.setError("Re-enter Password");
            txtPassw2.requestFocus();
        }

        if(!txtPassw1.getText().toString().equals(txtPassw2.getText().toString())){
            txtPassw1.setError("Password should be matched");
            txtPassw2.setError("Password should be matched");
            txtPassw1.requestFocus();
            txtPassw2.requestFocus();
        }

        else{
            //signup

            Log.i("state","Sign Up completed");
            userInfo.put("email", txtEmail.getText().toString());
            userInfo.put("username", txtUsername.getText().toString());
            userInfo.put("fname", txtFName.getText().toString());
            userInfo.put("lname", txtLName.getText().toString());
            userInfo.put("password", txtPassw1.getText().toString());

            userRef.push().setValue(userInfo);
            popUP("User Registered");
            //finish();
        }
    }

    public void close(View v){
        finish();
    }

    public void popUP(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
