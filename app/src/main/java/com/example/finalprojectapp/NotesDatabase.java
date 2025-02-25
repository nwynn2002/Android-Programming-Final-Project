package com.example.finalprojectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NotesDatabase extends SQLiteOpenHelper {
    private String TABLE_NAME = "NotesDB";
    private static final String USERNAME_COL = "username";
    private static final String DATE_CREATED_COL = "date_created";
    private static final String NOTE_COL = "note";

    public NotesDatabase(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, 1);
    }


    public NotesDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String query = " CREATE TABLE " + TABLE_NAME + " ("
                + USERNAME_COL + " TEXT, "
                + DATE_CREATED_COL + " TEXT, "
                + NOTE_COL + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

    public void addInfo(String username, String date, String note) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(USERNAME_COL, username);
        values.put(DATE_CREATED_COL, date);
        values.put(NOTE_COL, note);


        Log.d("===== add info =======", username + ", "+ note);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String getNote(String username, String date) {
        String query = "SELECT note FROM " + TABLE_NAME + " WHERE (username = \"" + username + "\") " +
                "AND (date_created = \"" + date + "\");";
        Log.d("Get note - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("Get note", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("Get note", extract.getString(0));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return extract.getString(0);
        } else {
            return "";
        }
    }

    public ArrayList<ArrayList<String>> getAllNotes(String username) {
        String query = "SELECT note, date_created FROM " + TABLE_NAME + " WHERE username = \"" + username + "\";";
        Log.d("Get all note - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("Get note", String.valueOf(extract.getCount()));
        ArrayList<ArrayList<String>> notes = new ArrayList<ArrayList<String>>();

        if (extract.getCount() > 0) {

            while(extract.moveToNext()) {
                ArrayList<String> pair = new ArrayList<String>();

                pair.add(extract.getString(0));
                pair.add(extract.getString(1));

                notes.add(pair);

            }
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return notes;
        } else {
            return notes;
        }
    }

        public void deleteNote(String username, String date) {
        String query = "DELETE FROM "+ TABLE_NAME + " WHERE (username = \"" + username + "\") " +
                "AND (date_created = \"" + date + "\");";
        Log.d("Delete note - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL(query);
    }

}
