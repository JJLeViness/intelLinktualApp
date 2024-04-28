package com.leviness.intellinktualapp;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if the message contains data payload
        if (remoteMessage.getData().size() > 0) {
            // Handle data payload
            handleDataPayload(remoteMessage.getData());
        }

        // Check if the message contains notification payload
        if (remoteMessage.getNotification() != null) {
            // Handle notification payload
            handleNotificationPayload(remoteMessage.getNotification());
        }
    }

    private void handleDataPayload(Map<String, String> data) {
        // Extract data from the message
        String messageText = data.get("message");


    }

    private void handleNotificationPayload(RemoteMessage.Notification notification) {
        // Extract notification information
        String title = notification.getTitle();
        String body = notification.getBody();


    }
}
