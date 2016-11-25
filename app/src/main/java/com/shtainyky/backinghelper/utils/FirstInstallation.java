package com.shtainyky.backinghelper.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.shtainyky.backinghelper.R;
import com.shtainyky.backinghelper.database_sharedprefences.DatabaseHelper;
import com.shtainyky.backinghelper.database_sharedprefences.QueryPreferences;
import com.shtainyky.backinghelper.model.ThemeItem;

import java.util.List;

public class FirstInstallation {

    private Context context;

    private DatabaseHelper helper;

    public FirstInstallation(Context context) {
        this.context = context;
        helper = new DatabaseHelper(context);
        for (int i = 0; i < 6; i++) {
            new FirstParseTitleIs().execute(i);
        }
        QueryPreferences.setStoredFirstInstallation(context, true);
    }


    private class FirstParseTitleIs extends AsyncTask<Integer, Void, List<ThemeItem>> {
        private int position;
        private List<ThemeItem> mListThemeItems;

        @Override
        protected List<ThemeItem> doInBackground(Integer... params) {
            position = params[0];
            return Parsing.parsingResult(params[0]);
        }

        @Override
        protected void onPostExecute(List<ThemeItem> listThemeItems) {
            mListThemeItems = listThemeItems;
            QueryPreferences.setStoredFirstInstallation(context, false);
            switch (position) {
                case 0:
                    helper.insertListThemes(mListThemeItems, DatabaseHelper.TABLE_NAME_THEME_1);
                    Toast.makeText(context,
                            context.getResources().getString(R.string.first_inst_tab_1),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    helper.insertListThemes(mListThemeItems, DatabaseHelper.TABLE_NAME_THEME_2);
                    Toast.makeText(context,
                            context.getResources().getString(R.string.first_inst_tab_2),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    helper.insertListThemes(mListThemeItems, DatabaseHelper.TABLE_NAME_THEME_3);
                    Toast.makeText(context,
                            context.getResources().getString(R.string.first_inst_tab_3),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    helper.insertListThemes(mListThemeItems, DatabaseHelper.TABLE_NAME_THEME_4);
                    Toast.makeText(context,
                            context.getResources().getString(R.string.first_inst_tab_4),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    helper.insertListThemes(mListThemeItems, DatabaseHelper.TABLE_NAME_THEME_5);
                    Toast.makeText(context,
                            context.getResources().getString(R.string.first_inst_tab_5),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    helper.insertListThemes(mListThemeItems, DatabaseHelper.TABLE_NAME_THEME_6);
                    Toast.makeText(context,
                            context.getResources().getString(R.string.first_inst_tab_6),
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context,
                            context.getResources().getString(R.string.first_inst),
                            Toast.LENGTH_LONG).show();

            }
        }
    }
}
