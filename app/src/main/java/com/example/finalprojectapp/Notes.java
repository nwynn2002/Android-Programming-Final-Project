package com.example.finalprojectapp;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Notes extends AppCompatActivity {
    private String str_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Button saveNote = findViewById(R.id.save_note);
        Button deleteNote = findViewById(R.id.del_note);
        EditText note = findViewById(R.id.note_txt);
        TextView note_error = findViewById(R.id.note_error);

        deleteNote.setVisibility(View.INVISIBLE);
        note_error.setVisibility(View.INVISIBLE);



        CalendarView noteCal = findViewById(R.id.calendarView);


        noteCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // get date selected
                month = month +1;
                str_date = year+"-"+month+"-"+dayOfMonth;

                String potential_note = MainActivity.USER_NOTES_DB.getNote(Login.CURRENT_USER, str_date);

                if (!potential_note.equals("")) {
                    deleteNote.setVisibility(View.VISIBLE);
                    saveNote.setVisibility(View.INVISIBLE);
                    note.setText(potential_note);
                } else {
                    deleteNote.setVisibility(View.INVISIBLE);
                    saveNote.setVisibility(View.VISIBLE);
                    note.setText("");
                }
            }
        });

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (note.equals("")) {
                    note_error.setVisibility(View.VISIBLE);
                } else {
                    String note_txt = note.getText().toString();
                    MainActivity.USER_NOTES_DB.addInfo(Login.CURRENT_USER, str_date, note_txt);
                }
            }
        });

        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.USER_NOTES_DB.deleteNote(Login.CURRENT_USER, str_date);
                deleteNote.setVisibility(View.INVISIBLE);
            }
        });

        Button export_notes = findViewById(R.id.export_note);
        export_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ArrayList<String>> allNotes = MainActivity.USER_NOTES_DB.getAllNotes(Login.CURRENT_USER);
                try {
                    writeNotesFiles(allNotes);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    private void writeNotesFiles(ArrayList<ArrayList<String>> notes) throws JSONException, IOException {
        String myDir = Environment.getExternalStorageDirectory() + "/Download/BookNotes.txt"; //creating a file in the internal storage/Documents folder on phone.


        Log.d("PrintDir", "=====" + myDir);
        File file = new File(myDir);    //creating a file object

        //Write to the file and store in internal storage
        FileOutputStream fOut = new FileOutputStream(file, true); //create a file output stream for writing data to file
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);  //converts character stream into byte stream

        JSONObject actJSON = new JSONObject();   //create a JSONObject
        for (int i = 0; i < notes.size(); i ++) {
            actJSON.put("Note", notes.get(i).get(0));
            actJSON.put("Date Created", notes.get(i).get(1));

            try {
                myOutWriter.append(actJSON.toString() + "\n");  //write JSONObject to file

            } catch (Exception e) {
                e.printStackTrace();  //to handle exceptions and errors.
            }
        }

        myOutWriter.close();
        fOut.close();
    }
}