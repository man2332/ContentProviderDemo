package com.example.contentproviderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

//        insertNewRecord("John", "Supercooolmangg");
//        insertNewRecord("Rick", "Supercooolmangg");
//        insertNewRecord("Glen", "Supercooolmangg");
//        insertNewRecord("Daryl", "Supercooolmangg");
//        insertNewRecord("Joe", "Supercooolmangg");
//        insertNewRecord("Steve", "Supercooolmangg");

        loadAll();
    }
    public void loadAll(){
        String text = "_____________________\n";

        Cursor cursor = getAllData();
        if(cursor.moveToFirst()){
            String name = cursor.getString(0);
            String description = cursor.getString(1);
            text += "Name: "+name+" Description: "+description+"\n\n";
            while(cursor.moveToNext()){
                text += "Name: "+cursor.getString(0)+" Description: "+cursor.getString(1)+"\n\n";
            }
        }
        textView.setText(text);

    }

    Uri insertNewRecord(String name, String description){
        ContentValues values = new ContentValues();
        values.put(ExampleContract.ExampleEntry.COLUMN_NAME, name);
        values.put(ExampleContract.ExampleEntry.COLUMN_DESCRIPTION, description);

        return getContentResolver().insert(ExampleContract.ExampleEntry.CONTENT_URI, values);
    }
    Cursor getAllData(){

        return getContentResolver().query(ExampleContract.ExampleEntry.CONTENT_URI,
                new String[]{ExampleContract.ExampleEntry.COLUMN_NAME, ExampleContract.ExampleEntry.COLUMN_DESCRIPTION},
                null,null,null);
    }

}
