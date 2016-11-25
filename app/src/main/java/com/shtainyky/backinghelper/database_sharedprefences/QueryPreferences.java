package com.shtainyky.backinghelper.database_sharedprefences;

import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPreferences {
    private static final String PREF_LAST_POSITION = "lastPosition";
    private static final String PREF_LAST_SWITCH_STRATEGY = "lastPositionSwitchPoker";
    private static final String PREF_LAST_SWITCH_GIPSY = "lastPositionSwitchGipsy";
    private static final String PREF_FIRST_INSTALLATION = "firstInstallation";


    public static int getStoredPosition(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_LAST_POSITION, 0);
    }

    public static void setStoredPosition(Context context, int position) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_LAST_POSITION, position)
                .apply();
    }

    public static boolean getStoredFirstInstallation(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_FIRST_INSTALLATION, true);
    }

    public static void setStoredFirstInstallation(Context context, boolean isFirstInstallation) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_FIRST_INSTALLATION, isFirstInstallation)
                .apply();
    }

    public static boolean getStoredPositionSwitchPokerStrategy(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_LAST_SWITCH_STRATEGY, false);
    }

    public static void setStoredPositionSwitchPokerStrategy(Context context, boolean positionSwitch) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_LAST_SWITCH_STRATEGY, positionSwitch)
                .apply();
    }

    public static boolean getStoredPositionSwitchGipsy(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_LAST_SWITCH_GIPSY, false);
    }

    public static void setStoredPositionSwitchPokerGipsy(Context context, boolean positionSwitch) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_LAST_SWITCH_GIPSY, positionSwitch)
                .apply();
    }
}
