package com.leviness.intellinktualapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String eventName = intent.getStringExtra("eventName");
        showNotification(context, eventName);
    }

    private void showNotification(Context context, String eventName) {
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Your_Channel_Id")

                .setContentTitle("Event Reminder")
                .setContentText(eventName + " is about to start")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0,builder.build());
    }
}
