package com.example.contentproviderdemo;

import android.net.Uri;
import android.provider.BaseColumns;

public class ExampleContract {

    public static final String CONTENT_AUTHORITY = "com.example.contentproviderdemo";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final class ExampleEntry implements BaseColumns{


        public static final String TABLE_NAME = "example";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static Uri buildExampleUriWithId(long id){
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }

    }
}
