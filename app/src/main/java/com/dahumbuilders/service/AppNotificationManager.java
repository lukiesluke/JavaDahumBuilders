package com.dahumbuilders.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.dahumbuilders.R;
import com.dahumbuilders.activity.MainActivity;

import static com.dahumbuilders.network.Constant.CHANNEL_ID;

public class AppNotificationManager {

    private static AppNotificationManager instance;
    private final Context context;

    public AppNotificationManager(Context context) {
        this.context = context;
    }

    public static synchronized AppNotificationManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppNotificationManager(context);
        }
        return instance;
    }

    public void displayNotification(String title, String body) {

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_format_list_bulleted_24)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }

}
