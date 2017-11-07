package com.travmahrajvar.bringmefood;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Travis on 07/11/17.
 */

public class FirebaseHandler {
	
	/** The app's connection to Firebase */
	private static FirebaseAuth fb;
	
	/**
	 * Starts this app's connection to Firebase.
	 */
	public static void initialize(){
		fb = FirebaseAuth.getInstance();
		//fb.signOut();
	}
	
	/**
	 * Gets the currently authenticated user, if signed in.
	 * @return The currently authenticated user, null otherwise
	 */
	public static FirebaseUser getCurrentUser(){
		return fb.getCurrentUser();
	}
	
	/**
	 * Signs in a user to their appropriate account using their OAuth2 token.
	 * @param credential The OAuth2 token received from by the sign-in providers
	 * @return A Task that will perform the sign in to Firebase
	 */
	public static Task<AuthResult> signInWithCredentials(@NonNull AuthCredential credential){
		return fb.signInWithCredential(credential);
	}
	
}
