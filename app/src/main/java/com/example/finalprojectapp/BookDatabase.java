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

public class BookDatabase extends SQLiteOpenHelper {

    private String TABLE_NAME = "BookDB";
    private static final String BOOK_TITLE_COL = "book_title";
    private static final String BOOK_AUTHOR_COL = "book_author";
    private static final String BOOK_ISBN_COL = "book_isbn";
    private static final String BOOK_URl_COL = "book_url";
    private static final String BOOK_IMAGE_PATH_COL = "book_image_path";
    private static final String BOOK_IMAGE_ID_COL = "book_image_id";

    public BookDatabase(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, 1);
    }

    public BookDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String query = " CREATE TABLE " + TABLE_NAME + " ("
                + BOOK_TITLE_COL + " TEXT, "
                + BOOK_AUTHOR_COL + " TEXT, "
                + BOOK_ISBN_COL + " TEXT, "
                + BOOK_IMAGE_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOOK_URl_COL + " TEXT,"
                + BOOK_IMAGE_PATH_COL + " TEXT)";

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

    public void addInfo(String book_title, String book_author, String book_isbn, String book_url, String book_image_path) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(BOOK_TITLE_COL, book_title);
        values.put(BOOK_AUTHOR_COL, book_author);
        values.put(BOOK_ISBN_COL, book_isbn);
        values.put(BOOK_URl_COL, book_url);
        values.put(BOOK_IMAGE_PATH_COL, book_image_path);


        Log.d("===== add info =======", book_title + ", "+ book_author);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<ArrayList<String>> getIDandImage() {
        String query = "SELECT book_image_id, book_image_path FROM " + TABLE_NAME + ";";
        Log.d("Get Book ID and Image URL - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);
        ArrayList<ArrayList<String>> book_pair = new ArrayList<ArrayList<String>>();

        Log.d("Get Book ID and Image URL  - count", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            while(extract.moveToNext()) {
                // create list of id and image url
                ArrayList<String> pair = new ArrayList<String>();
                pair.add(extract.getString(0));
                pair.add(extract.getString(1));

                // append list of book_pair
                book_pair.add(pair);
            }

            return book_pair;
        }

        else {
            return book_pair;
        }
    }

    public ArrayList<ArrayList<String>> getTitleAuthor() {
        String query = "SELECT book_title, book_author, book_image_id FROM " + TABLE_NAME + ";";
        Log.d("Get Book title and author - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);
        ArrayList<ArrayList<String>> book_pair = new ArrayList<ArrayList<String>>();

        Log.d("Get Book title and author - count", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            while(extract.moveToNext()) {
                // create list of id and image url
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

    public String getBookID(String book_isbn) {
        String query = "SELECT book_image_id FROM " + TABLE_NAME + " WHERE book_isbn = \"" + book_isbn + "\";";
        Log.d("Get Book ID - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("Get Book ID - count", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("Get Book ID - string", extract.getString(0));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return extract.getString(0);
        }

        else {
            return "";
        }
    }

    public ArrayList<ArrayList<String>> getBookbyTitle(String title) {
        String query = "SELECT book_title, book_author, book_image_id FROM " + TABLE_NAME + " WHERE book_title LIKE \"%" + title + "%\";";
        Log.d("Get Book title - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);
        ArrayList<ArrayList<String>> book_pair = new ArrayList<ArrayList<String>>();

        Log.d("Get Book title - count", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            while(extract.moveToNext()) {
                // create list of id and image url
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

    public ArrayList<ArrayList<String>> getBookbyAuthor(String author) {
        String query = "SELECT book_title, book_author, book_image_id FROM " + TABLE_NAME + " WHERE book_author LIKE \"%" + author + "%\";";
        Log.d("Get Book title - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);
        ArrayList<ArrayList<String>> book_pair = new ArrayList<ArrayList<String>>();

        Log.d("Get Book title - count", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            while(extract.moveToNext()) {
                // create list of id and image url
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


    public ArrayList<String> getBookbyImageID(String id) {
        String query = "SELECT book_title, book_author FROM " + TABLE_NAME + " WHERE book_image_id = " + id +";";
        Log.d("Get Book title - query", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);
        ArrayList<String> book_pair = new ArrayList<String>();

        Log.d("Get Book title - count", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();
            // create list of id and image url
            book_pair.add(extract.getString(0));
            book_pair.add(extract.getString(1));


            return book_pair;
        }

        else {
            return book_pair;
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

}
