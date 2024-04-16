package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Login extends AppCompatActivity {
    protected static String CURRENT_USER = "";
    protected static String CURRENT_PASSWORD = "";
    // Disable the back button

    @Override
    public void onBackPressed() {
        if (MainActivity.backAllowed) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context c = this;

        // set error_txt to invisible
        TextView error_txt = findViewById(R.id.error_txt2);
        error_txt.setVisibility(View.INVISIBLE);

        Button login_user = findViewById(R.id.loginButton);

        login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDatabase db = new UserDatabase(c, "Users");

                List<String> columnHeaders = db.getColumnHeaders();

                Log.d("====== main activity =====", columnHeaders.toString());

                // get edittext views
                EditText usernameEdit = findViewById(R.id.usernameEditText3);
                EditText passwordEdit = findViewById(R.id.passwordEditText3);

                // convert to string
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    error_txt.setText("Uh oh, something's wrong. Please check that you filled out all sections and met all requirements .");
                    error_txt.setVisibility(View.VISIBLE);
                }

                else if (db.checkPassword(username).equals(password)) {
                    CURRENT_USER = username;
                    CURRENT_PASSWORD = password;
                    MainActivity.backAllowed = false;

                    //Intent intent = new Intent(Login.this, home.class);
                    //startActivity(intent);
                }

                else {
                    error_txt.setText("Username does not match password, please try again.");
                    error_txt.setVisibility(View.VISIBLE);
                }


            }
        });
    }
}