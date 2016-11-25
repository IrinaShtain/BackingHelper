package com.shtainyky.backinghelper.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.shtainyky.backinghelper.R;
import com.shtainyky.backinghelper.model.ThemeItem;

import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationForBacking {

    public static void sendNotificationsStrategy(Context context, ThemeItem themeItem) {
        int notificationId = getNotificationId();
        PendingIntent pi = getPendingIntent(context, themeItem);

        Resources resources = context.getResources();
        String someLongText = resources.getString(R.string.new_theme_text)
                + " " + themeItem.getUser()
                + " " + resources.getString(R.string.new_theme_text1)
                + " " + themeItem.getTitle();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker(resources.getString(R.string.new_theme_title))
                .setContentTitle(resources.getString(R.string.new_theme_title_pokerStrategy))
                .setContentText(someLongText)
                .setSmallIcon(R.mipmap.ic_message_text)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_VIBRATE);

        Notification notification = new NotificationCompat.BigTextStyle(builder)
                .bigText(someLongText).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
        Log.i("mLog", "!!!!!!sendNotifications!!!!!!!!!!!!!!!!!!!!!!!!!!!!Got a new result: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
    }

    public static void sendNotificationsGipsy(Context context, ThemeItem themeItem) {
        int notificationId = getNotificationId();
        PendingIntent pi = getPendingIntent(context, themeItem);

        Resources resources = context.getResources();
        String someLongText = resources.getString(R.string.new_theme_text)
                + " " + themeItem.getUser()
                + " " + resources.getString(R.string.new_theme_text1)
                + " " + themeItem.getTitle();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker(resources.getString(R.string.new_theme_title))
                .setContentTitle(resources.getString(R.string.new_theme_title_GipsyTeam))
                .setContentText(someLongText)
                .setSmallIcon(R.mipmap.ic_message_text)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.sound));

        Notification notification = new NotificationCompat.BigTextStyle(builder)
                .bigText(someLongText).build();


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
        Log.i("mLog", "!!!!!!sendNotifications!!!!!!!!!!!!!!!!!!!!!!!!!!!!Got a new result: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
    }

    private static int getNotificationId() {
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        return Integer.valueOf(last4Str);
    }

    private static PendingIntent getPendingIntent(Context context, ThemeItem themeItem) {
        Uri address = Uri.parse(themeItem.getLink());
        Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
        return PendingIntent.getActivity(context, 0, openlinkIntent, 0);
    }

}
