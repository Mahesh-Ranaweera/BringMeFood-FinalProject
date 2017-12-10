package com.travmahrajvar.bringmefood.utils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

/**
 * Created by Travis on 10/12/17.
 */

public class UserInfo {
	private String name;
	private String email;
	private String uid;
	private List<String> friends;
	
	public UserInfo() {}
	
	public UserInfo(String name, String email, String uid){
		this.name = name;
		this.email = email;
		this.uid = uid;
	}
	
	public static UserInfo createFromFirebaseUser(FirebaseUser fbUser){
		return new UserInfo(fbUser.getDisplayName(), fbUser.getEmail(), fbUser.getUid());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
}
