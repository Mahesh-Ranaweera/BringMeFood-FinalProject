package com.travmahrajvar.bringmefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class SignUpActivity extends AppCompatActivity {

    EditText txtEmail, txtUsername, txtFName, txtLName, txtPassw1, txtPassw2;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("msg");

        myRef.setValue("Testing Firebase storage");

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtFName = (EditText) findViewById(R.id.txtFName);
        txtLName = (EditText) findViewById(R.id.txtLName);
        txtPassw1 = (EditText) findViewById(R.id.txtPassw1);
        txtPassw2 = (EditText) findViewById(R.id.txtPassw2);
    }


    public void close(View v){
        finish();
    }
}
