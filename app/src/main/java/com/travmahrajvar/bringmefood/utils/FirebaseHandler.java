package com.travmahrajvar.bringmefood.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Travis on 07/11/17.
 */

public class FirebaseHandler {
	
	/** The app's connection to the Firebase authentication methods */
	private static FirebaseAuth fbAuthenticator;
	
	/** The app's connection to the Firebase database */
	private static FirebaseDatabase fbDatabase;
	private static DatabaseReference fbDatabaseReference;
	
	/**
	 * Starts this app's connection to Firebase.
	 */
	public static void initialize(){
		fbDatabase = FirebaseDatabase.getInstance();
		fbDatabaseReference = fbDatabase.getReference();
		fbAuthenticator = FirebaseAuth.getInstance();
		//fbAuthenticator.signOut();
	}
	
	//region Authentication
	
	/**
	 * Gets the currently authenticated user, if signed in.
	 * @return The currently authenticated user, null otherwise
	 */
	public static FirebaseUser getCurrentUser(){
		return fbAuthenticator.getCurrentUser();
	}
	
	/**
	 * Signs in a user to their appropriate account using their OAuth2 token.
	 * @param credential The OAuth2 token received from by the sign-in providers
	 * @return A Task that will perform the sign in to Firebase
	 */
	public static Task<AuthResult> signInWithCredentials(@NonNull AuthCredential credential){
		fbAuthenticator.createUserWithEmailAndPassword("", "");
		return fbAuthenticator.signInWithCredential(credential);
	}
	
	/**
	 * Creates a new user in the Firebase authentication database.
	 * @param email The email address to use for this account
	 * @param password The password to use for this account
	 * @return A Task that will perform the account creation and sign in to Firebase
	 */
	public static Task<AuthResult> createUserWithEmailAndPassword(@NonNull String email, @NonNull String password){
		return fbAuthenticator.createUserWithEmailAndPassword(email, password);
	}
	
	/**
	 * Signs in a user using their email and password, if they've made an account already.
	 * @param email The email address associated with this account
	 * @param password The password associated with this account
	 * @return A Task that will perform the sign in to Firebase
	 */
	public static Task<AuthResult> signInWithEmailAndPassword(@NonNull String email, @NonNull String password){
		return fbAuthenticator.signInWithEmailAndPassword(email, password);
	}
	
	/**
	 * Signs out the currently authenticated user.
	 */
	public static void signOutCurrentUser(){
		if(getCurrentUser() != null){
			fbAuthenticator.signOut();
		}
	}
	
	//endregion
	
	//region Database
	
	public static void putPublicUserInfoInDatabase(FirebaseUser fbUser){
		UserInfo user = UserInfo.createFromFirebaseUser(fbUser);
		fbDatabaseReference.child("users").child(fbUser.getUid()).setValue(user);
	}
	
	//endregion
	
}
