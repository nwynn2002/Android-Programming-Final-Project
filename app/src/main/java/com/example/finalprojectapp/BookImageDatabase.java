package com.example.finalprojectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookImageDatabase extends SQLiteOpenHelper {

    private String TABLE_NAME = "BookImageDB";
    private static final String BOOK_IMAGE_COL = "book_image";
    private static final String BOOK_IMAGE_ID_COL = "book_image_id";

    public BookImageDatabase(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, 1);
    }

    public BookImageDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String query = " CREATE TABLE " + TABLE_NAME + " ("
                + BOOK_IMAGE_COL + " TEXT, "
                + BOOK_IMAGE_ID_COL + " INTEGER PRIMARY KEY)";

        db.execSQL(query);
    }

    public List<String> getColumnHeaders() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> columnNames = new ArrayList<>();
        // Query that returns at least one row. The actual data doesn't matter as we're just after the structure.
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " LIMIT 1", null);

        // Ensure the cursor is not null and contains at least one column.
        if (cursor != null && cursor.getColumnCount() > 0) {
            // Loop through all columns and add their names to the list.
            for (String name : cursor.getColumnNames()) {
                columnNames.add(name);
            }
            cursor.close();
        }

        db.close();
        return columnNames;
    }

    public void addInfo(String book_image_id, String book_image) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(BOOK_IMAGE_ID_COL, book_image_id);
        values.put(BOOK_IMAGE_COL, book_image);



        Log.d("===== add book image info =======", book_image_id + ", "+ book_image);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public String getImage(String book_id) {
        String query = "SELECT book_image FROM " + TABLE_NAME + " WHERE book_image_id = \"" + book_id + "\";";
        Log.d("Get Image - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("Get Image - count", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("Get Image - string", extract.getString(0));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return extract.getString(0);
        }

        else {
            return "";
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

}
