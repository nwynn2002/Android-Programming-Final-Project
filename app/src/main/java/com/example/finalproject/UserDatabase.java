package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase extends SQLiteOpenHelper {

    private String TABLE_NAME = "UserDB";
    private static final String FIRST_NAME_COL = "last_name";
    private static final String LAST_NAME_COL = "first_name";
    private static final String EMAIL_COL = "email";
    private static final String BIRTHDAY_COL = "birthday";
    private static final String USERNAME_COL = "username";
    private static final String PASSWORD_COL = "password";
    private static final String GENDER_COL = "gender";

    private static final String DATE_TIME_CREATED_COL = "created";
    private static final String RECEIVE_BIRTHDAY_NOTES = "notes";


    public UserDatabase(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, 1);
    }

    public UserDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        String query = " CREATE TABLE " + TABLE_NAME + " ("
                + USERNAME_COL + " TEXT PRIMARY KEY, "
                + PASSWORD_COL + " TEXT, "
                + FIRST_NAME_COL + " TEXT, "
                + LAST_NAME_COL + " TEXT, "
                + BIRTHDAY_COL + " DATE,"
                + GENDER_COL + " TEXT,"
                + EMAIL_COL + " TEXT, "
                + RECEIVE_BIRTHDAY_NOTES + "TEXT,"
                + DATE_TIME_CREATED_COL + " DATETIME)";

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

    public void addInfo(String username, String password, String first_name, String last_name, String birthday, String gender, String email, String notes, String created) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(USERNAME_COL, username);
        values.put(PASSWORD_COL, password);
        values.put(FIRST_NAME_COL, first_name);
        values.put(LAST_NAME_COL, last_name);
        values.put(BIRTHDAY_COL, birthday);
        values.put(GENDER_COL, gender);
        values.put(EMAIL_COL, email);
        values.put(RECEIVE_BIRTHDAY_NOTES, notes);
        values.put(DATE_TIME_CREATED_COL, created);

        Log.d("===== add info =======", username + ", "+ password);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public  String checkUniqueUser(String username) {
        String query = "SELECT username FROM " + TABLE_NAME + " WHERE username = \"" + username + "\";";
        Log.d("=====  Check Unique User - query =======", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("=====  Check Unique User - count =======", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("=====  Check Unique User - string =======", extract.getString(0));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return extract.getString(0);
        }

        else {
            return "";
        }
    }

    public  String checkUniqueEmail(String email) {
        String query = "SELECT email FROM " + TABLE_NAME + " WHERE email = \"" + email + "\";";
        Log.d("=====  Check Unique User - query =======", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("=====  Check Unique User - count =======", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("=====  Check Unique Email - string =======", extract.getString(0));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return extract.getString(0);
        }

        else {
            return "";
        }
    }


    public String checkPassword(String username_or_email) {
        String query = "SELECT password FROM " + TABLE_NAME + " WHERE username = \"" + username_or_email + "\";";
        Log.d("=====  Check Password - query =======", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("=====  Check Password & User- count =======", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("=====  Check Password - string =======", extract.getString(0));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return extract.getString(0);
        }

        else {
            query = "SELECT password FROM " + TABLE_NAME + " WHERE email = \"" + username_or_email + "\";";
            Log.d("=====  Check Password - query =======", query);

            db = this.getReadableDatabase();

            extract = db.rawQuery(query, null);

            Log.d("=====  Check Password & Email - count =======", String.valueOf(extract.getCount()));

            if (extract.getCount() > 0) {

                extract.moveToLast();

                Log.d("=====  Check Password - string =======", extract.getString(0));
                //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
                return extract.getString(0);
            } else {
                return "";
            }

        }
    }

    public String getName(String username) {
        String query = "SELECT name FROM " + TABLE_NAME + " WHERE username = \"" + username + "\";";
        Log.d("=====  Get Name - query =======", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("=====  Get Name - count =======", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("=====  Get Name - string =======", extract.getString(0));
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
