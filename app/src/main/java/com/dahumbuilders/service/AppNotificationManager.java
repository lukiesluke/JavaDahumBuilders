package com.dahumbuilders.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.dahumbuilders.R;
import com.dahumbuilders.activity.MainActivity;

import java.util.Random;

import static com.dahumbuilders.network.Constant.CHANNEL_DESCRIPTION;
import static com.dahumbuilders.network.Constant.CHANNEL_ID;
import static com.dahumbuilders.network.Constant.CHANNEL_NAME;

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
        int randomID = new Random().nextInt(61) + 20;
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_format_list_bulleted_24)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notificationManager.notify(randomID, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.setDescription(CHANNEL_DESCRIPTION);
        channel.setLightColor(Color.GREEN);
        channel.enableVibration(true);
        notificationManager.createNotificationChannel(channel);
    }
}
