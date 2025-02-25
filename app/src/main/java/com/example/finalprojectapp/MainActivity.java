package com.example.finalprojectapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected static boolean backAllowed = true;
    protected static UserDatabase db;
    protected static BookDatabase BOOK_DB;
    protected static BookImageDatabase BOOK_IMAGE_DB;
    protected static UserBookDatabase USER_BOOK_DB;
    protected static NotesDatabase USER_NOTES_DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context c = this;
        requestAppPermissions();



        // initialize databases
        db = new UserDatabase(c, "Users");


        BOOK_DB = new BookDatabase(c, "Books");
        BOOK_IMAGE_DB = new BookImageDatabase(c, "BookImages");
        USER_BOOK_DB = new UserBookDatabase(c, "UserBooks");
        USER_NOTES_DB = new NotesDatabase(c, "UserNotes");



        List<String> columnHeaders = db.getColumnHeaders();

        Log.d("====== main activity =====", columnHeaders.toString());

        String logged_user = checkLogState("AllThingsBooks");

        if (!logged_user.equals("false")) {
            Login.CURRENT_USER = logged_user;
            Login.CURRENT_PASSWORD = db.getPassword(Login.CURRENT_USER);
            Login.CURRENT_FIRST_NAME = db.getName(Login.CURRENT_USER);

            Intent intent = new Intent(MainActivity.this, UserProfile.class);
            startActivity(intent);

        }



        Button btn = findViewById(R.id.login_btn);  // Find the submit button in the layout

        // Set an OnClickListener on the button to handle click events
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Log.d("Book DB", "adding books");
                    addBooks();

                    List<String> bookColumnHeaders = BOOK_DB.getColumnHeaders();

                    Log.d("====== main activity =====", bookColumnHeaders.toString());

                    ArrayList<ArrayList<String>> books = BOOK_DB.getIDandImage();

                    Log.d("=== testing ===", String.valueOf(books));

                    Log.d("Book Image DB", "adding images");
                    addBookImages(books);
                } catch (FileNotFoundException e) {
                    Log.d("Trying file", "=== File not found ===");
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Create an intent to start the user registeration activity
                Intent messageIntent = new Intent(getApplicationContext(), User.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });


        Button btn2 = findViewById(R.id.register_btn);  // Find the submit button in the layout

        // Set an OnClickListener on the button to handle click events
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the log in activity
                Intent messageIntent2 = new Intent(getApplicationContext(), Login.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent2);

            }
        });


    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    void requestAppPermissions() {
        //  if (hasReadPermissions() && hasWritePermissions()) {
        if (hasWritePermissions()) {

            return;
        }
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 0);
    }

    public static void writeFile(String filename, String username, String password, String logout) throws JSONException, FileNotFoundException {

        String myDir = Environment.getExternalStorageDirectory() + "/Download/." + filename; //creating a file in the internal storage/Documents folder on phone.

        // String myDir = Environment.getExternalStorageDirectory() +"/Documents/"+filename; //creating a file in the internal storage/Documents folder on phone.
        Log.d("PrintDir", "=====" + myDir);
        File file = new File(myDir);    //creating a file object
        JSONObject actJSON = new JSONObject();   //create a JSONObject
        actJSON.put("Username", username);
        actJSON.put("Password", password);
        actJSON.put("Logged Out", logout);

        //Write to the file and store in internal storage
        FileOutputStream fOut = new FileOutputStream(file, true); //create a file output stream for writing data to file
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);  //converts character stream into byte stream
        try {
            myOutWriter.append(actJSON.toString() + "\n");  //write JSONObject to file
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();  //to handle exceptions and errors.
        }

    }

    /*
     * checkLogState returns if the device has been logged in
     */
    public String checkLogState(String filename) {
        Log.d("Check log state", "in check log state");

        String myDir = Environment.getExternalStorageDirectory() + "/Download/"; //creating a file in the internal storage/Documents folder on phone.

        File fileDir = new File(myDir);

        List<String> potentialFiles = new ArrayList<String>();

        if (fileDir.exists() && fileDir.isDirectory()) {
            String[] dirFiles = fileDir.list();

            assert dirFiles != null;
            for (String fileName : dirFiles) {
                if (fileName.contains(filename)) {
                    String[] splitFile = fileName.split("_");
                    Log.d("spilt file", Arrays.toString(splitFile));
                    String user_txt = splitFile[1];
                    Log.d("second spilt file", user_txt);
                    String[] secondSplit = user_txt.split("\\.");
                    Log.d("second spilt file", Arrays.toString(secondSplit));

                    return secondSplit[0];
                }
            }
        }

        return "false";

    }

    /*
     * addBooks adds books from given file path
     */
    private void addBooks() throws FileNotFoundException {

        Log.d("Add Books", "==== attempting to find files ====");

        try {
            InputStream is = getResources().openRawResource(R.raw.books);

            BufferedReader lineReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            Log.d("Add Books", "==== reading file ====");

            ArrayList<String> cur_book = new ArrayList<String>();

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(";");
                String title = data[0];
                String author = data[1];
                String isbn = data[2];
                String url = data[3];
                String image_url = data[4];


                BOOK_DB.addInfo(title, author, isbn, url, image_url);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    // adds image paths to BookImages DB
    private void addBookImages(ArrayList<ArrayList<String>> book_pair) throws IOException {

        Log.d("Add Book Image", "preparing to add images");

        String book_id;
        String[] image_path;
        String image;
        for (int i = 0; i < book_pair.size(); i++) {
            // get book id and image url for current pair
            book_id = book_pair.get(i).get(0);
            image_path = book_pair.get(i).get(1).split("\\.");
            image = image_path[0];

            Log.d("Add Book Image", book_id +", "+image);

            // create url link to image and pull it

            Log.d("Add Book Image", "==== reading image path ====");

            BOOK_IMAGE_DB.addInfo(book_id, image);

        }
    }

}