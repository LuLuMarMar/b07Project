// NotificationHelper.java
package com.example.b07_project;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {

    private static final String EVENT_CHANNEL_ID = "EventChannelId";
    private static final String ANNOUNCEMENT_CHANNEL_ID = "AnnouncementChannelId";

    public static void showEventNotification(Context context, String eventName, String eventDetails) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(EVENT_CHANNEL_ID, "Event Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Create a BigTextStyle for longer text
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                .bigText(eventDetails);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, EVENT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("New Event")
                .setContentText(eventName)
                .setStyle(bigTextStyle)  // Apply the BigTextStyle
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(0, builder.build());
    }

    public static void showAnnouncementNotification(Context context, String title, String announcement) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ANNOUNCEMENT_CHANNEL_ID, "Announcement Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Create a BigTextStyle for longer text
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle()
                .bigText(announcement);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ANNOUNCEMENT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(announcement)
                .setStyle(bigTextStyle)  // Apply the BigTextStyle
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(0, builder.build());
    }
}
