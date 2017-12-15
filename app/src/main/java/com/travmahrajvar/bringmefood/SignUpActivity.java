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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

public class SignUpActivity extends AppCompatActivity {

	
    EditText txtEmail, txtFName, txtLName, txtPassw1, txtPassw2;
    

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
		//First, verify all the given info, email, first and last name, and password, are OK


        if(txtEmail.getText().toString().trim().length() == 0){
            txtEmail.setError(getString(R.string.error_emailMissing));
            txtEmail.requestFocus();
        }

        else if(txtFName.getText().toString().trim().length() == 0){
            txtFName.setError(getString(R.string.error_fnameMissing));
            txtFName.requestFocus();
        }

        else if(txtLName.getText().toString().trim().length() == 0){
            txtLName.setError(getString(R.string.error_lnameMissing));
            txtLName.requestFocus();
        }

        else if(txtPassw1.getText().toString().trim().length() == 0){
            txtPassw1.setError(getString(R.string.error_passwordMissing));
            txtPassw1.requestFocus();
        }

        else if(txtPassw2.getText().toString().trim().length() == 0){
            txtPassw2.setError(getString(R.string.error_repasswordMissing));
            txtPassw2.requestFocus();
        }

        else if(!txtPassw1.getText().toString().equals(txtPassw2.getText().toString())){
            txtPassw1.setError(getString(R.string.error_passwordsDontMatch));
            txtPassw2.setError(getString(R.string.error_passwordsDontMatch));
            txtPassw1.requestFocus();
            txtPassw2.requestFocus();
        }
        
        else if(txtPassw1.getText().toString().trim().length() < 6){
        	txtPassw1.setError(getString(R.string.error_passwordTooShort));
        	txtPassw1.setError(getString(R.string.error_passwordTooShort));
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
										.setDisplayName(txtFName.getText().toString() + " " + txtLName.getText().toString())
										.build();


								
								FirebaseHandler.getCurrentUser().updateProfile(initialUserSetupInfo)
										.addOnCompleteListener(new OnCompleteListener<Void>() {
											@Override
											public void onComplete(@NonNull Task<Void> task) {
												if(!task.isSuccessful()) {
													Log.w("onUpdateProfile", task.getException());
													Toast.makeText(SignUpActivity.this, R.string.error_login_fail_user_info, Toast.LENGTH_SHORT).show();
												} else {
													// Update the public database info with user name and email and send back to welcome activity
													FirebaseHandler.putPublicUserInfoInDatabase(FirebaseHandler.getCurrentUser());
													finish();
												}
											}
										});
							} else {
								//Something went wrong. Print the error to the console.
								Log.w("signInWithEmail:failure", task.getException());
								Toast.makeText(SignUpActivity.this, R.string.error_login_fail, Toast.LENGTH_SHORT).show();
							}
			            }
		            });
        }
    }
	
	public void close(View v){
        finish();
    }
	
}
