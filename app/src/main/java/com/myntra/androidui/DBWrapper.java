package com.myntra.androidui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by prateek.mehra on 18/12/14.
 */
public class DBWrapper {

    private DBHelper helper;
    private SQLiteDatabase db;


    public DBWrapper (Context ctx) {
        helper = new DBHelper(ctx);
        db = helper.getWritableDatabase();

    }

    public long addCredentials(String userid, String password){
        //Insert to table
        ContentValues values = new ContentValues();
        values.put(DBHelper.CLM_USERNAME, userid);
        values.put(DBHelper.CLM_PASSWORD, password);
        return db.insert(DBHelper.TABLE_NAME, null, values);
    }

    public Cursor getCredentials(){
        //Query
        String[] columns = {DBHelper.CLM_USERNAME, DBHelper.CLM_PASSWORD};
        Cursor c = db.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        return c;
    }

    public void closeDatabase(){
        db.close();
    }
}
