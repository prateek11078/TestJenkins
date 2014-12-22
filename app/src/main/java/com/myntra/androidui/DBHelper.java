package com.myntra.androidui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prateek.mehra on 18/12/14.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "auth.db";

    public static final String TABLE_NAME = "Credentials";
    public static final String CLM_USERNAME = "userid";
    public static final String CLM_PASSWORD = "password";

    private static final String TABLE_QUERY = "create table "+TABLE_NAME +" ("+ CLM_USERNAME + " text, "+ CLM_PASSWORD + " text)";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
