package com.example.contentproviderdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ExampleDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "example.db";
    private static final int DATABASE_VERSION = 1;

    public ExampleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE "+ ExampleContract.ExampleEntry.TABLE_NAME +" ("+
                ExampleContract.ExampleEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ExampleContract.ExampleEntry.COLUMN_NAME +" TEXT NOT NULL, "+
                ExampleContract.ExampleEntry.COLUMN_DESCRIPTION +" TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ ExampleContract.ExampleEntry.TABLE_NAME);
        onCreate(db);
    }
}
