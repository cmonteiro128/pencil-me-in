package mobileapp.wit.edu.pencilmein;

/**
 * Created by chris on 4/3/18.
 */

import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(id, notification);

    }
}