package com.travmahrajvar.bringmefood;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;
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
            
            FirebaseHandler.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassw1.getText().toString())
		            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
			            @Override
			            public void onComplete(@NonNull Task<AuthResult> task) {
							if(task.isSuccessful()){
								//We've created the user
								//Now we need to set the user's names
								UserProfileChangeRequest initialUserSetupInfo = new UserProfileChangeRequest.Builder()
										.setDisplayName(txtFName + " " + txtLName)
										.build();
								
								FirebaseHandler.getCurrentUser().updateProfile(initialUserSetupInfo)
										.addOnCompleteListener(new OnCompleteListener<Void>() {
											@Override
											public void onComplete(@NonNull Task<Void> task) {
												if(task.isSuccessful())
													Toast.makeText(SignUpActivity.this, "User created successfully!", Toast.LENGTH_SHORT).show();
											}
										});
								
								finish();
							} else {
								Log.w("signInWithEmail:failure", task.getException());
								Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
							}
			            }
		            });
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
