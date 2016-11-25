package com.shtainyky.backinghelper.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.shtainyky.backinghelper.R;
import com.shtainyky.backinghelper.database_sharedprefences.DatabaseHelper;
import com.shtainyky.backinghelper.model.ThemeItem;
import com.shtainyky.backinghelper.utils.Parsing;

import java.util.List;

public class PollServiceGipsy extends IntentService {
    private static final String TAG = "PollServiceGipsy";
    private static final int POLL_INTERVAL = 1000 * 20; // 20 секунд
    private DatabaseHelper helper = new DatabaseHelper(this);


    public static Intent newIntent(Context context) {
        return new Intent(context, PollServiceGipsy.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = PollServiceGipsy.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL, pi);

        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = PollServiceGipsy.newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    public PollServiceGipsy() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 4; i < 6; i++) {
            switch (i) {
                case 4:
                    Log.i(TAG, "Parsing doWorkForTab1(4);" + i);
                    doWorkForTab5(4);
                    break;
                case 5:
                    Log.i(TAG, "Parsing doWorkForTab1(5);" + i);
                    doWorkForTab6(5);
                    break;
            }
        }
        Log.i(TAG, "Received an intent: " + intent);
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        return isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
    }

    private void doWorkForTab5(int position) {
        List<ThemeItem> lastItems5 = helper.getAllThemes(DatabaseHelper.TABLE_NAME_THEME_5);
        List<ThemeItem> newItems5 = Parsing.parsingResult(position);
        if (newItems5.size() == 0) {
            return;
        }
        for (int j = 0; j < newItems5.size(); j++) {
            if (newItems5.get(j).isThisThemeInSavedListItems(lastItems5)) {
                Log.i(TAG, "Got an old result: " + newItems5.get(j).getTitle());
            } else {
                List<String> listUnwantedUsers = helper.getAllUsers();
                Log.i(TAG, "Got a new result: " + newItems5.get(j).getTitle());
                if (!newItems5.get(j).isThemesUserInListUnwantedUsers(listUnwantedUsers)) {
                    NotificationForBacking.sendNotificationsGipsy(this, newItems5.get(j));
                }
                helper.deleteAllSavedThemes(DatabaseHelper.TABLE_NAME_THEME_5);
                helper.insertListThemes(newItems5, DatabaseHelper.TABLE_NAME_THEME_5);
            }
        }

    }

    private void doWorkForTab6(int position) {
        List<ThemeItem> lastItems6 = helper.getAllThemes(DatabaseHelper.TABLE_NAME_THEME_6);
        List<ThemeItem> newItems6 = Parsing.parsingResult(position);
        if (newItems6.size() == 0) {
            return;
        }
        for (int j = 0; j < newItems6.size(); j++) {
            if (newItems6.get(j).isThisThemeInSavedListItems(lastItems6)) {
                Log.i(TAG, "Got an old result: " + lastItems6.get(j).getTitle());
            } else {
                List<String> listUnwantedUsers = helper.getAllUsers();
                Log.i(TAG, "Got a new result: " + newItems6.get(j).getTitle());
                if (!newItems6.get(j).isThemesUserInListUnwantedUsers(listUnwantedUsers)) {
                    NotificationForBacking.sendNotificationsGipsy(this, newItems6.get(j));
                }
                helper.deleteAllSavedThemes(DatabaseHelper.TABLE_NAME_THEME_6);
                helper.insertListThemes(newItems6, DatabaseHelper.TABLE_NAME_THEME_6);

            }
        }


    }


}
