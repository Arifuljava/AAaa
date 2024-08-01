package com.example.aaaa.timer;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.aaaa.R;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setContentTitle("Timer Finished")
                .setContentText("Your 24-hour countdown has ended.")
                .setSmallIcon(R.drawable.baseline_access_time_24)
                .build();
        notificationManager.notify(0, notification);
    }
}
