package com.example.finalproject;


import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

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

import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    protected static boolean backAllowed = true;
    protected static UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context c = this;

        // get python working
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python python = Python.getInstance();
        python.getModule("main").callAttr("hello_world");


        db = new UserDatabase(c, "Users");
        requestAppPermissions();


        List<String> columnHeaders = db.getColumnHeaders();

        Log.d("====== main activity =====", columnHeaders.toString());

//        try {
//            if (checkLogState("AllThingsBooks_User.txt")) {
//
//                Intent intent = new Intent(MainActivity.this, UserProfile.class);
//                startActivity(intent);
//
//            }
//        } catch (FileNotFoundException e) {
//            Log.d("==== On create - main ====", "No file found, starting main activity");
//        }

        Button btn=findViewById(R.id.bu);  // Find the submit button in the layout

        // Set an OnClickListener on the button to handle click events
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the user registeration activity
                Intent messageIntent = new Intent(getApplicationContext(), User.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent);

            }
        });


        Button btn2=findViewById(R.id.bl);  // Find the submit button in the layout

        // Set an OnClickListener on the button to handle click events
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an intent to start the log in activity
                Intent messageIntent2 = new Intent(getApplicationContext(),Login.class);

                // Start the oneThroughFour activity
                startActivity(messageIntent2);

            }
        });


    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestAppPermissions() {
        //  if (hasReadPermissions() && hasWritePermissions()) {
        if (hasWritePermissions()) {

            return;
        }
        ActivityCompat.requestPermissions(this,
                new String[] {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 0);
    }

    public static void writeFile(String filename, String username, String password, String logout) throws JSONException, FileNotFoundException {

        String myDir = Environment.getExternalStorageDirectory() +"/Download/."+filename; //creating a file in the internal storage/Documents folder on phone.

        // String myDir = Environment.getExternalStorageDirectory() +"/Documents/"+filename; //creating a file in the internal storage/Documents folder on phone.
        Log.d("PrintDir","====="+myDir);
        File file = new File(myDir);    //creating a file object
        JSONObject actJSON = new JSONObject();   //create a JSONObject
        actJSON.put("Username",username);
        actJSON.put("Password",password);
        actJSON.put("Logged Out", logout);

        //Write to the file and store in internal storage
        FileOutputStream fOut = new FileOutputStream(file, true); //create a file output stream for writing data to file
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);  //converts character stream into byte stream
        try {
            myOutWriter.append(actJSON.toString() + "\n");  //write JSONObject to file
            myOutWriter.close();
            fOut.close();
        }
        catch (Exception e){
            e.printStackTrace();  //to handle exceptions and errors.
        }

    }

//     public boolean checkLogState(String filename) throws FileNotFoundException {
//
//        PyObject json = py.getModule("json");
//        PyObject logFile = PyObject_Call("open", );
//
//        return false;
//
//    }


}