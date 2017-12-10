package com.travmahrajvar.bringmefood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.travmahrajvar.bringmefood.utils.FirebaseHandler;

public class WelcomeActivity extends AppCompatActivity {

	EditText getEmail, getPassw;
	
	/** The sign in manager for Google accounts. */
	GoogleSignInClient mGoogleSignInClient;
	
	/** The sign in manager for Facebook accounts. */
	private CallbackManager fbCallbackManager;

	private static final String TAG = "SignInActivity";
	private static final int REQUEST_SIGNIN_GOOGLE = 83;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//set the app to full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_welcome);

		getEmail = (EditText) findViewById(R.id.txtEmail_login);
		getPassw = (EditText) findViewById(R.id.txtPassw_login);
		
		//Initialize the app's Firebase connection
		FirebaseHandler.initialize();
		
		//Initialize the sign in buttons
		initializeGoogleSignInButton();
		initializeFacebookSignInButton();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		//If the user is still signed in from a previous session, load it
		FirebaseUser currentUser = FirebaseHandler.getCurrentUser();
		verifyUser(currentUser);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// Check if this was a sign in from Google or Facebook
		if (requestCode == REQUEST_SIGNIN_GOOGLE) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				// Google Sign In was successful, authenticate with Firebase
				GoogleSignInAccount account = task.getResult(ApiException.class);
				firebaseAuthWithGoogle(account);
			} catch (ApiException e) {
				// Google Sign In failed, update UI appropriately
				Log.w(TAG, "Google sign in failed", e);
				// ...
			}
		} else {
			// Pass the activity result back to the Facebook SDK, if signed in with Facebook
			// (the callback manager contains its own request and result code handling)
			fbCallbackManager.onActivityResult(requestCode, resultCode, data);
		}
		
	}
	
	/**
	 * Initializes the Facebook sign in button.
	 *
	 * Referenced from:
	 * https://developers.facebook.com/docs/facebook-login/android/
	 * https://firebase.google.com/docs/auth/android/facebook-login
	 */
	private void initializeFacebookSignInButton() {
		fbCallbackManager = CallbackManager.Factory.create();
		LoginButton fbLoginButton = findViewById(R.id.fb_login_button);
		fbLoginButton.setReadPermissions("email", "public_profile");
		fbLoginButton.registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				Log.d(TAG, "facebook:onSuccess:" + loginResult);
				handleFacebookAccessToken(loginResult.getAccessToken());
			}
			
			@Override
			public void onCancel() {
				Log.d(TAG, "facebook:onCancel");
				// ...
			}
			
			@Override
			public void onError(FacebookException error) {
				Log.d(TAG, "facebook:onError", error);
				// ...
			}
		});
	}
	
	/**
	 * Initializes the Google sign in button.
	 *
	 * Referenced from:
	 * https://developers.google.com/identity/sign-in/android/sign-in
	 * https://firebase.google.com/docs/auth/android/google-signin
	 */
	private void initializeGoogleSignInButton() {
		findViewById(R.id.google_login_button).setOnClickListener(googleSignIn_onClick);
		
		// Configure sign-in to request the user's ID, email address, and basic
		// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
		// Configure Google Sign In
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.default_web_client_id))
				.requestEmail()
				.build();
		
		// Build a GoogleSignInClient with the options specified by gso.
		mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
		
		//Customization
		SignInButton signInButton = findViewById(R.id.google_login_button);
		signInButton.setSize(SignInButton.SIZE_STANDARD);
		signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
	}
	
	/**
	 * The OnClickListener that handles the Sign in with Google button's onClick handler.
	 */
	OnClickListener googleSignIn_onClick = new OnClickListener(){
		public void onClick(View v) {
			Intent signInIntent = mGoogleSignInClient.getSignInIntent();
			startActivityForResult(signInIntent, REQUEST_SIGNIN_GOOGLE);
		}
	};
	
	/**
	 * Authenticates the user's Google account and signs into the app.
	 *
	 * Referenced from:
	 * https://developers.google.com/identity/sign-in/android/sign-in
	 * https://firebase.google.com/docs/auth/android/google-signin
	 *
	 * @param acct The Google account to verify
	 */
	private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
		AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
		FirebaseHandler.signInWithCredentials(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							FirebaseUser user = FirebaseHandler.getCurrentUser();
							FirebaseHandler.putPublicUserInfoInDatabase(user);
							verifyUser(user);
						} else {
							// If sign in fails, display a message to the user.
							Toast.makeText(WelcomeActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
							verifyUser(null);
						}
						
						// ...
					}
				});
	}
	
	/**
	 * Authenticates the user's Facebook account and signs into the app.
	 *
	 * Referenced from:
	 * https://developers.facebook.com/docs/facebook-login/android/
	 * https://firebase.google.com/docs/auth/android/facebook-login
	 *
	 * @param token The Facebook OAuth2 token to verify
	 */
	private void handleFacebookAccessToken(AccessToken token) {
		AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
		FirebaseHandler.signInWithCredentials(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							FirebaseUser user = FirebaseHandler.getCurrentUser();
							FirebaseHandler.putPublicUserInfoInDatabase(user);
							verifyUser(user);
						} else {
							// If sign in fails, display a message to the user.
							Toast.makeText(WelcomeActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
							verifyUser(null);
						}
						
						// ...
					}
				});
	}
	
	/**
	 * Verifies the user's current account and moves to the choice activity if so.
	 * Otherwise, stay here, and ask the user to authenticate.
	 *
	 * @param account The authenticated user account, or null.
	 */
	public void verifyUser(FirebaseUser account){
		if(account != null){
			Toast.makeText(this, "Logged in as " + account.getEmail(), Toast.LENGTH_LONG).show();
			
			//Redirect to the choice page
			Intent userChoice = new Intent(WelcomeActivity.this, ChoiceSelect.class);
			startActivity(userChoice);
		}
	}
	
	/**
	 * Goes to the sign up activity.
	 * @param v
	 */
	public void signUPPage(View v){
		Intent signupPage = new Intent(this, SignUpActivity.class);
		startActivity(signupPage);
	}
	
	
	/**
	 * Used for testing.
	 * Remove after finishing.
	 * @param v
	 */
	public void apptest(View v){
		Intent apptest = new Intent(this, ChoiceSelect.class);
		startActivity(apptest);
	}
	
	/**
	 * Signs in a user using an email and password.
	 *
	 * Referenced from:
	 * https://firebase.google.com/docs/auth/android/password-auth
	 * https://firebase.google.com/docs/auth/android/manage-users
	 *
	 * @param v The "Sign in" button view
	 */
	public void signINUser(View v) {
		//First, make sure there's not nothing in the boxes
		if (getEmail.getText().toString().trim().length() == 0) {
			getEmail.setError("Enter Email");
			getEmail.requestFocus();
		}

		if (getPassw.getText().toString().trim().length() == 0) {
			getPassw.setError("Enter Password");
			getPassw.requestFocus();
		} else {
			//Now, authenticate the user, and sign in if the process was successful
			FirebaseHandler.signInWithEmailAndPassword(getEmail.getText().toString(), getPassw.getText().toString())
					.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							if(task.isSuccessful()){
								verifyUser(FirebaseHandler.getCurrentUser());
							} else {
								// If sign in fails, display a message to the user.
								Toast.makeText(WelcomeActivity.this, "Authentication failed.",
										Toast.LENGTH_SHORT).show();
								verifyUser(null);
							}
						}
					});
		}
	}
}
