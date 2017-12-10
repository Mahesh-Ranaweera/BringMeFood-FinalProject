package com.travmahrajvar.bringmefood;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

	
    EditText txtEmail, txtFName, txtLName, txtPassw1, txtPassw2;
    private DatabaseReference databaseReference; //database reference for stroring user data
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtFName = (EditText) findViewById(R.id.txtFName);
        txtLName = (EditText) findViewById(R.id.txtLName);
        txtPassw1 = (EditText) findViewById(R.id.txtPassw1);
        txtPassw2 = (EditText) findViewById(R.id.txtPassw2);
    }
	
	/**
	 * Prepares the given user info to be added to the user list on Firebase.
	 *
	 * Referenced from:
	 * https://firebase.google.com/docs/auth/android/password-auth
	 * https://firebase.google.com/docs/auth/android/manage-users
	 *
	 * @param v The "Sign Up" button view
	 */
	public void signUPUser(View v){
		//First, verify all the given info is OK.s
        if(txtEmail.getText().toString().trim().length() == 0){
            txtEmail.setError("Enter Email");
            txtEmail.requestFocus();
        }

        else if(txtFName.getText().toString().trim().length() == 0){
            txtFName.setError("Enter First Name");
            txtFName.requestFocus();
        }

        else if(txtLName.getText().toString().trim().length() == 0){
            txtLName.setError("Enter Last Name");
            txtLName.requestFocus();
        }

        else if(txtPassw1.getText().toString().trim().length() == 0){
            txtPassw1.setError("Enter Password");
            txtPassw1.requestFocus();
        }

        else if(txtPassw2.getText().toString().trim().length() == 0){
            txtPassw2.setError("Re-enter Password");
            txtPassw2.requestFocus();
        }

        else if(!txtPassw1.getText().toString().equals(txtPassw2.getText().toString())){
            txtPassw1.setError("Password should be matched");
            txtPassw2.setError("Password should be matched");
            txtPassw1.requestFocus();
            txtPassw2.requestFocus();
        }
        
        else if(txtPassw1.getText().toString().trim().length() < 6){
        	txtPassw1.setError("Password should be at least 6 characters.");
        	txtPassw1.setError("Password should be at least 6 characters.");
        	txtPassw1.requestFocus();
        	txtPassw2.requestFocus();
        }

        //If everything is OK, then start creating the user credentials
        else{
	        // Everything is contained within this function Firebase provides
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
												if(!task.isSuccessful()) {
													Log.w("onUpdateProfile", task.getException());
													Toast.makeText(SignUpActivity.this, "User information could not be updated.", Toast.LENGTH_SHORT).show();
												} else {
													// Return to the main activity new that everything's all good.
													finish();
												}
											}
										});
							} else {
								//Something went wrong. Print the error to the console.
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
	
}
