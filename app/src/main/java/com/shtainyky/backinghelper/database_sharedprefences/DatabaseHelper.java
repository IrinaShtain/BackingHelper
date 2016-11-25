package com.shtainyky.backinghelper.database_sharedprefences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shtainyky.backinghelper.model.ThemeItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "users.db";

    public final static String TABLE_NAME_THEME_1 = "PokerStrategy_1";
    public final static String TABLE_NAME_THEME_2 = "PokerStrategy_2";
    public final static String TABLE_NAME_THEME_3 = "PokerStrategy_3";
    public final static String TABLE_NAME_THEME_4 = "PokerStrategy_4";
    public final static String TABLE_NAME_THEME_5 = "GipsyTeam_5";
    public final static String TABLE_NAME_THEME_6 = "GipsyTeam_6";

    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_TITLE = "title";
    private final static String COLUMN_USER = "user";
    private final static String COLUMN_LINK = "link";
    private final static String COLUMN_REVIEWS = "reviews";

    private final static String TABLE_NAME_USER = "users";
    private final static String COLUMN_ID_USER = "_id";
    private final static String COLUMN_NAME_USER = "user_name";

    private SQLiteDatabase sqLiteDatabase;

    private final static String DATABASE_CREATE_THEME_1 = "create table " + TABLE_NAME_THEME_1 + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null ," +
            COLUMN_USER + " text not null ," +
            COLUMN_LINK + " text not null, " +
            COLUMN_REVIEWS + " integer not null);";

    private final static String DATABASE_CREATE_THEME_2 = "create table " + TABLE_NAME_THEME_2 + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null ," +
            COLUMN_USER + " text not null ," +
            COLUMN_LINK + " text not null, " +
            COLUMN_REVIEWS + " integer not null);";

    private final static String DATABASE_CREATE_THEME_3 = "create table " + TABLE_NAME_THEME_3 + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null ," +
            COLUMN_USER + " text not null ," +
            COLUMN_LINK + " text not null, " +
            COLUMN_REVIEWS + " integer not null);";

    private final static String DATABASE_CREATE_THEME_4 = "create table " + TABLE_NAME_THEME_4 + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null ," +
            COLUMN_USER + " text not null ," +
            COLUMN_LINK + " text not null, " +
            COLUMN_REVIEWS + " integer not null);";

    private final static String DATABASE_CREATE_THEME_5 = "create table " + TABLE_NAME_THEME_5 + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null ," +
            COLUMN_USER + " text not null ," +
            COLUMN_LINK + " text not null, " +
            COLUMN_REVIEWS + " integer not null);";

    private final static String DATABASE_CREATE_THEME_6 = "create table " + TABLE_NAME_THEME_6 + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null ," +
            COLUMN_USER + " text not null ," +
            COLUMN_LINK + " text not null, " +
            COLUMN_REVIEWS + " integer not null);";

    private final static String DATABASE_CREATE_USER = "create table " + TABLE_NAME_USER + " (" +
            COLUMN_ID_USER + " integer primary key autoincrement, " +
            COLUMN_NAME_USER + " text not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_THEME_1);
        db.execSQL(DATABASE_CREATE_THEME_2);
        db.execSQL(DATABASE_CREATE_THEME_3);
        db.execSQL(DATABASE_CREATE_THEME_4);
        db.execSQL(DATABASE_CREATE_THEME_5);
        db.execSQL(DATABASE_CREATE_THEME_6);
        db.execSQL(DATABASE_CREATE_USER);
        sqLiteDatabase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query1 = "DROP TABLE IF EXISTS " + TABLE_NAME_THEME_1;
        db.execSQL(query1);

        String query2 = "DROP TABLE IF EXISTS " + TABLE_NAME_THEME_2;
        db.execSQL(query2);

        String query3 = "DROP TABLE IF EXISTS " + TABLE_NAME_THEME_3;
        db.execSQL(query3);

        String query4 = "DROP TABLE IF EXISTS " + TABLE_NAME_THEME_4;
        db.execSQL(query4);

        String query5 = "DROP TABLE IF EXISTS " + TABLE_NAME_THEME_5;
        db.execSQL(query5);

        String query6 = "DROP TABLE IF EXISTS " + TABLE_NAME_THEME_6;
        db.execSQL(query6);

        String queryUser = "DROP TABLE IF EXISTS " + TABLE_NAME_USER;
        db.execSQL(queryUser);

        this.onCreate(db);
    }

    public void deleteAllSavedThemes(String tableName) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(tableName, null, null);
        sqLiteDatabase.close();
    }

    public void insertListThemes(List<ThemeItem> listThemes, String tableName) {
        for (int i = 0; i < listThemes.size(); i++) {
            insertTheme(listThemes.get(i), tableName);
        }
    }

    private void insertTheme(ThemeItem item, String tableName) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, item.getTitle());
        values.put(COLUMN_USER, item.getUser());
        values.put(COLUMN_LINK, item.getLink());
        values.put(COLUMN_REVIEWS, item.getViews());

        sqLiteDatabase.insert(tableName, null, values);
        sqLiteDatabase.close();

    }

    public List<ThemeItem> getAllThemes(String tableName) {
        List<ThemeItem> themeItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + tableName;
        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ThemeItem themeItem = new ThemeItem();
                themeItem.setTitle(cursor.getString(1));
                themeItem.setUser(cursor.getString(2));
                themeItem.setLink(cursor.getString(3));
                themeItem.setViews(Integer.parseInt(cursor.getString(4)));
                themeItems.add(themeItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return themeItems;
    }

    public boolean insertUser(String userName) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USER, userName);

        sqLiteDatabase.insert(TABLE_NAME_USER, null, values);
        sqLiteDatabase.close();
        return true;
    }

    public void deleteUser(String userName) {
        sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(TABLE_NAME_USER, COLUMN_NAME_USER + " = ?",
                new String[]{String.valueOf(userName)});
        sqLiteDatabase.close();
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_USER;
        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                users.add(String.valueOf(cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return users;
    }
}
