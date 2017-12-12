package com.travmahrajvar.bringmefood.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Travis on 10/12/17.
 */

public class NotificationInstanceIDServices extends FirebaseInstanceIdService {
	
	@Override
	public void onTokenRefresh() {
		super.onTokenRefresh();

		String refreshedToken = FirebaseInstanceId.getInstance().getToken();

		//remove please
		Log.d("logtocken", "token: " + refreshedToken);

		//sendRegistrationToServer(refreshedToken);
	}
	
	private void sendRegistrationToServer(String token) {
		// TODO: Implement this method to send token to your app server.
	}
}
