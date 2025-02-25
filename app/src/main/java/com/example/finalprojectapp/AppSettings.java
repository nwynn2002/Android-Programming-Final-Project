package com.example.finalprojectapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class AppSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

        Button book_alarm = findViewById(R.id.readingAlarm);
        Button book_notes = findViewById(R.id.book_notes);
        Button logout = findViewById(R.id.logout_btn);

        book_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppSettings.this, ReadingAlarm.class);
                startActivity(intent);
            }
        });

        book_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppSettings.this, Notes.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppSettings.this, Login.class);
                logoutUser(".AllThingsBooks_"+Login.CURRENT_USER+".txt");

                MainActivity.backAllowed = false;
                startActivity(intent);
            }
        });

    }

    protected boolean logoutUser(String filePath) {
        String myDir = Environment.getExternalStorageDirectory() + "/Download/" + filePath;
        File myObj = new File(myDir);

        return myObj.delete();

    }
}