package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class User extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        if (MainActivity.backAllowed) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        // set error_txt to invisible
        TextView error_txt = findViewById(R.id.error_txt);
        error_txt.setVisibility(View.INVISIBLE);

        Button register_user = findViewById(R.id.registerButton);

        register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get edittext views
                EditText firstNameEdit = findViewById(R.id.firstNameEditText);
                EditText lastNameEdit = findViewById(R.id.lastNameEditText);
                EditText emailEdit = findViewById(R.id.emailEditText);
                EditText birthdayEdit = findViewById(R.id.birthdayEditTextDate);
                EditText genderEdit = findViewById(R.id.genderEditText);


                EditText usernameEdit = findViewById(R.id.usernameEditText);
                EditText passwordEdit = findViewById(R.id.passwordEditText);
                EditText confirmPasswordEdit = findViewById(R.id.confirmPasswordEditText);
                CheckBox receiveNotesCheckbox = findViewById(R.id.receive_notes_box);


                // convert to string
                String firstName = firstNameEdit.getText().toString();
                String lastName = lastNameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String birthday = birthdayEdit.getText().toString();
                String gender = genderEdit.getText().toString();


                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String confirmPassword = confirmPasswordEdit.getText().toString();

                String receiveNotes = (receiveNotesCheckbox.isSelected()) ? "yes" : "no";

                LocalDateTime created = LocalDateTime.now();



                if (firstName.isEmpty() ||lastName.isEmpty() || email.isEmpty() || birthday.isEmpty() || gender.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    error_txt.setText("Uh oh, something's wrong. Please check that you filled out all sections and met all requirements .");
                    error_txt.setVisibility(View.VISIBLE);
                }

                else {
                    if (verifyPass(password) & verifyUsername(username)) {
                        if (verifyDate(birthday)) {
                            if (MainActivity.db.checkUniqueUser(username).isEmpty()) {
                                if (MainActivity.db.checkUniqueEmail(email).isEmpty()) {
                                    if (password.equals(confirmPassword)) {
                                        MainActivity.db.addInfo(username, password, firstName, lastName, birthday, gender, email, receiveNotes, created.toString());

                                        MainActivity.backAllowed = false;

                                        Intent intent = new Intent(User.this, Login.class);
                                        startActivity(intent);
                                    } else {
                                        error_txt.setText("Passwords do not match.");
                                        error_txt.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                        error_txt.setText("Uh oh, someone already has that email, please choose a new one or try to login.");
                                        error_txt.setVisibility(View.VISIBLE);
                                    }
                            } else {
                                error_txt.setText("Uh oh, someone already has that username, please choose a new one.");
                                error_txt.setVisibility(View.VISIBLE);
                            }
                        } else {
                            error_txt.setText("Please give a valid birthday.");
                            error_txt.setVisibility(View.VISIBLE);
                        }

                    } else {
                        error_txt.setText("Uh oh, something's wrong. Please check that you filled out all sections and met all requirements .");
                        error_txt.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
    }

    private Boolean verifyUsername(String username) {
        if(username.length() == 5) {

            // check each character is alphanumeric
            for (int i = 0; i < username.length(); i++) {
                char character = username.charAt(i);

                // if not alphanumeric return false
                if (!Character.isLetterOrDigit(character)) {
                    return false;
                }
            }

            // return true since all characters are alphanumeric and length == 5
            return true;
        }

        else {
            return false;
        }
    }

    private Boolean verifyPass(String password) {
        if(password.length() == 8) {
            char firstChar = password.charAt(0);    //gets first character

            return Character.isUpperCase(firstChar);    //returns if first character is upper case
        }

        else {
            return false;
        }
    }

    private Boolean verifyDate(String birthday) {
        LocalDate date = null;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        try {
            date = LocalDate.parse(birthday, format);
        } catch(DateTimeParseException e) {
            Log.d("===== verify date =====", "Invalid birthday given", e);
            return false;
        }

        return true;
    }
}