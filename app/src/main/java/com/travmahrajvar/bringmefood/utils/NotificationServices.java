package com.travmahrajvar.bringmefood.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.travmahrajvar.bringmefood.WelcomeActivity;

/**
 * Created by Travis on 10/12/17.
 */

public class NotificationServices extends FirebaseMessagingService {
	
	private static NotificationServices notificationService;
	
	public NotificationServices(){
		super();
		notificationService = this;
	}
	
	@Override
	public void onMessageReceived(RemoteMessage message){
		Log.d("Messaging", message.getFrom());

		//remove please
		//String value = message.getData().get("payload");
		//Log.d("payload", "NotiPayload" + value);


//		if(message.getNotification() != null){
//			notifyUser(message.getNotification().getBody());
//		}

		if(message.getData().get("payload") != null){
			notifyUser(message.getNotification().getBody());
		}
	}
	
	public static void notifyUser(String body){
		if(body != null) {
			Intent intent = new Intent(notificationService, WelcomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			PendingIntent pendingIntent = PendingIntent.getActivity(notificationService, 0, intent, PendingIntent.FLAG_ONE_SHOT);
			
			String channelId = "Friends";
			Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(notificationService, channelId)
					.setContentTitle("Notification title")
					.setContentText(body)
					.setAutoCancel(true)
					.setSound(defaultSoundUri)
					.setContentIntent(pendingIntent);
			
			NotificationManager notificationManager = (NotificationManager) notificationService.getSystemService(Context.NOTIFICATION_SERVICE);
			
			notificationManager.notify(0, notificationBuilder.build());
		}
	}
}
