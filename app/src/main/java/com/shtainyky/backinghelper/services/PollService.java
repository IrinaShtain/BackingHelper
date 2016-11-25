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

public class PollService extends IntentService {
    private static final String TAG = "PollService";
    private static final int POLL_INTERVAL = 1000 * 20; // 20 секунд
    private DatabaseHelper helper = new DatabaseHelper(this);

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = PollService.newIntent(context);
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
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    Log.i(TAG, "Parsing doWorkForTab1(0);" + i);
                    doWorkForTab1(0);
                    break;
                case 1:
                    Log.i(TAG, "Parsing doWorkForTab1(1);" + i);
                    doWorkForTab2(1);
                    break;
                case 2:
                    Log.i(TAG, "Parsing doWorkForTab1(2);" + i);
                    doWorkForTab3(2);
                    break;
                case 3:
                    Log.i(TAG, "Parsing doWorkForTab1(3);" + i);
                    doWorkForTab4(3);
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

    private void doWorkForTab1(int position) {
        List<ThemeItem> lastItems1 = helper.getAllThemes(DatabaseHelper.TABLE_NAME_THEME_1);
        List<ThemeItem> items1 = Parsing.parsingResult(position);
        if (items1.size() == 0) {
            return;
        }
        for (int j = 0; j < items1.size(); j++) {
            if (items1.get(j).isThisThemeInSavedListItems(lastItems1)) {
                Log.i(TAG, "Got an old result: " + items1.get(j).getTitle());
            } else {
                List<String> listUnwantedUsers = helper.getAllUsers();
                if (!items1.get(j).isThemesUserInListUnwantedUsers(listUnwantedUsers)) {
                    NotificationForBacking.sendNotificationsStrategy(this, items1.get(j));
                }
                helper.deleteAllSavedThemes(DatabaseHelper.TABLE_NAME_THEME_1);
                helper.insertListThemes(items1, DatabaseHelper.TABLE_NAME_THEME_1);
            }
        }
    }

    private void doWorkForTab2(int position) {
        List<ThemeItem> lastItems2 = helper.getAllThemes(DatabaseHelper.TABLE_NAME_THEME_2);
        List<ThemeItem> items2 = Parsing.parsingResult(position);
        if (items2.size() == 0) {
            return;
        }
        for (int j = 0; j < items2.size(); j++) {
            if (items2.get(j).isThisThemeInSavedListItems(lastItems2)) {
                Log.i(TAG, "Got an old result: " + items2.get(j).getTitle());
            } else {
                Log.i(TAG, "Got a new result: " + items2.get(j).getTitle());
                List<String> listUnwantedUsers = helper.getAllUsers();
                if (!items2.get(j).isThemesUserInListUnwantedUsers(listUnwantedUsers)) {
                    NotificationForBacking.sendNotificationsStrategy(this, items2.get(j));
                }
                helper.deleteAllSavedThemes(DatabaseHelper.TABLE_NAME_THEME_2);
                helper.insertListThemes(items2, DatabaseHelper.TABLE_NAME_THEME_2);
            }
        }
    }

    private void doWorkForTab3(int position) {
        List<ThemeItem> lastItems3 = helper.getAllThemes(DatabaseHelper.TABLE_NAME_THEME_3);
        List<ThemeItem> items3 = Parsing.parsingResult(position);
        if (items3.size() == 0) {
            return;
        }
        for (int j = 0; j < items3.size(); j++) {
            if (items3.get(j).isThisThemeInSavedListItems(lastItems3)) {
                Log.i(TAG, "Got an old result: " + items3.get(j).getTitle());
            } else {
                Log.i(TAG, "Got a new result: " + items3.get(j).getTitle());
                List<String> listUnwantedUsers = helper.getAllUsers();
                if (!items3.get(j).isThemesUserInListUnwantedUsers(listUnwantedUsers)) {
                    NotificationForBacking.sendNotificationsStrategy(this, items3.get(j));
                }
                helper.deleteAllSavedThemes(DatabaseHelper.TABLE_NAME_THEME_3);
                helper.insertListThemes(items3, DatabaseHelper.TABLE_NAME_THEME_3);
            }
        }
    }

    private void doWorkForTab4(int position) {
        List<ThemeItem> lastItems4 = helper.getAllThemes(DatabaseHelper.TABLE_NAME_THEME_4);
        List<ThemeItem> items4 = Parsing.parsingResult(position);
        if (items4.size() == 0) {
            return;
        }
        for (int j = 0; j < items4.size(); j++) {
            if (items4.get(j).isThisThemeInSavedListItems(lastItems4)) {
                Log.i(TAG, "Got an old result: " + items4.get(j).getTitle());
            } else {
                Log.i(TAG, "Got a new result: " + items4.get(j).getTitle());
                List<String> listUnwantedUsers = helper.getAllUsers();
                if (!items4.get(j).isThemesUserInListUnwantedUsers(listUnwantedUsers)) {
                    NotificationForBacking.sendNotificationsStrategy(this, items4.get(j));
                }
                helper.deleteAllSavedThemes(DatabaseHelper.TABLE_NAME_THEME_4);
                helper.insertListThemes(items4, DatabaseHelper.TABLE_NAME_THEME_4);
            }
        }
    }
}
