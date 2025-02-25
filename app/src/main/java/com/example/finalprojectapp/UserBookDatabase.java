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

public class UserBookDatabase extends SQLiteOpenHelper {
    private String TABLE_NAME = "UserBooksDB";
    private static final String USERNAME_COL = "username";
    private static final String BOOK_TITLE_COL = "book_title";
    private static final String BOOK_AUTHOR_COL = "book_author";
    private static final String BOOK_LIST_COL = "book_list";
    private static final String BOOK_IMAGE_ID_COL = "book_image_id";

    public UserBookDatabase(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, 1);
    }
    public UserBookDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String query = " CREATE TABLE " + TABLE_NAME + " ("
                + USERNAME_COL + " TEXT, "
                + BOOK_TITLE_COL + " TEXT, "
                + BOOK_AUTHOR_COL + " TEXT, "
                + BOOK_LIST_COL + " TEXT, "
                + BOOK_IMAGE_ID_COL + " INTEGER)";

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

    public void addInfo(String username, String book_title, String book_author, String book_list, String book_image_id) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(USERNAME_COL, username);
        values.put(BOOK_TITLE_COL, book_title);
        values.put(BOOK_AUTHOR_COL, book_author);
        values.put(BOOK_LIST_COL, book_list);
        values.put(BOOK_IMAGE_ID_COL, book_image_id);


        Log.d("add info - user books", username + ", "+ book_list +", " + book_title+ ", " + book_author);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public  String checkBook(String book_image_id) {
        String query = "SELECT book_list FROM " + TABLE_NAME + " WHERE book_image_id = " + book_image_id + ";";
        Log.d("Check book - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("Check book - count", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("Check book - string", extract.getString(0));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return extract.getString(0);
        }

        else {
            return "";
        }
    }

    // returns array list of books by the passed list
    public ArrayList<ArrayList<String>> getList(String book_list) {
        String query = "SELECT book_title, book_author, book_image_id FROM " + TABLE_NAME + " WHERE book_list = \"" + book_list+ "\";";
        Log.d("get book list- query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("get book list - count", String.valueOf(extract.getCount()));

        ArrayList<ArrayList<String>> book_pair = new ArrayList<ArrayList<String>>();

        if (extract.getCount() > 0) {

            while(extract.moveToNext()) {
                ArrayList<String> pair = new ArrayList<String>();
                pair.add(extract.getString(0));
                pair.add(extract.getString(1));
                pair.add(extract.getString(2));

                // append list of book_pair
                book_pair.add(pair);

            }
            return book_pair;
        }

        else {
            return book_pair;
        }
    }

    public String getTotalBooksRead(String username) {
        String query = "SELECT COUNT(book_title) FROM " + TABLE_NAME + " WHERE (username = \"" +username + "\") " +
                " AND (book_list != \"want to read\");" ;

        Log.d(" Total book - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        if (extract.getCount()>0) {

            extract.moveToLast();

            return extract.getString(0);
        }

        return "";

    }
}
