'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

//Commented out for now since database structure is not yet finalized
//Referenced from: https://github.com/firebase/functions-samples/tree/master/fcm-notifications

/*exports.sendFriendNotification = functions.database.ref('/friendNotifiers/{notifiedUid}/{notifierUid}').onWrite(event => {
	const notifierUid = event.params.notifierUid;
	const notifiedUid = event.params.notifiedUid;

	if(!event.data.val()){
		return console.log('User ', notifierUid, 'cancelled food request for user', notifiedUid);
	}
	console.log('User ', notifierUid, 'requests if user', notifiedUid, "wants food");

	const getDeviceTokensPromise = admin.database().ref('/users/${notifiedUid}/notificationTokens').once('value');

	const getNotifierProfilePromise = admin.auth().getUser(notifierUid);

	return Promise.all([getDeviceTokensPromise, getNotifierProfilePromise]).then(results => {
		const tokensSnapshot = results[0];
		const notifier = results[1];

		if(!tokensSnapshot.hasChildren()){
			return console.log('No notification tokens to send to.');
		}

		console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');
		console.log('Got notifier profile', notifier);

		const payload = {
			notification: {
				title: 'Do you want food?',
				body: `${notifier.displayName}`
			}
		};

		const tokens = Object.keys(tokensSnapshot.val());

		return admin.messaging().sendToDevice(tokens, payload).then(response => {
			const tokensToRemove = [];
			response.results.forEach((result, index) =>{
				const error = result.error;
				if(error){
					console.error('Couldn\'t send notification to', tokens[index], error);
					if(error.code === 'messaging/invalid-registration-token' ||
						error.code === 'messaging/registration-token-not-registered'){
							tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
						}
				}
			});
			return Promise.all(tokensToRemove);
		});
	})
});*/