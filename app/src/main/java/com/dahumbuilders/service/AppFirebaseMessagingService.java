package com.dahumbuilders.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        sendRegistrationToServer(token);
        Log.d("lwg", "Refreshed token: " + token);
    }

    private void sendRegistrationToServer(String token) {

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("lwg", "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("lwg", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("lwg", "Message Notification Body: " + remoteMessage.getNotification().getBody());

            AppNotificationManager.getInstance(getApplicationContext())
                    .displayNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }
}
