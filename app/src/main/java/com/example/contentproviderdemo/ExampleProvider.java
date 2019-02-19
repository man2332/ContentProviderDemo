package com.example.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ExampleProvider extends ContentProvider {

    private ExampleDbHelper mOpenHelper;

    public static final int CODE_EXAMPLE = 100;
    public static final int CODE_WITH_ID = 101;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {

        mOpenHelper = new ExampleDbHelper(getContext());

        return  mOpenHelper != null;
    }
    @androidx.annotation.Nullable
    @Override
    public Cursor query(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String[] projection, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs, @androidx.annotation.Nullable String sortOrder) {
        Cursor cursor;
        db = mOpenHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)){
            case CODE_EXAMPLE:{
                cursor = db.query(ExampleContract.ExampleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }
            case CODE_WITH_ID: {
                String _id = uri.getLastPathSegment();
                String[] selectArguments = new String[]{_id};

                cursor = db.query(ExampleContract.ExampleEntry.TABLE_NAME,
                        projection,
                        ExampleContract.ExampleEntry._ID+" = ? ",
                        selectArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @androidx.annotation.Nullable
    @Override
    public String getType(@androidx.annotation.NonNull Uri uri) {
        return null;
    }

    @androidx.annotation.Nullable
    @Override
    public Uri insert(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values) {

        db = mOpenHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)){
            case CODE_EXAMPLE:
                long _id=db.insert(ExampleContract.ExampleEntry.TABLE_NAME, null, values);
                if(_id != -1){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return ExampleContract.ExampleEntry.buildExampleUriWithId(_id);
            default:
                return null;
        }

    }

    @Override
    public int delete(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs) {
        return 0;
    }

    public static UriMatcher buildUriMatcher(){

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ExampleContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ExampleContract.ExampleEntry.TABLE_NAME, CODE_EXAMPLE);
        matcher.addURI(authority, ExampleContract.ExampleEntry.TABLE_NAME+"/#", CODE_WITH_ID);

        return  matcher;
    }
}
