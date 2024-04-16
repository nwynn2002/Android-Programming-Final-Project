package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    protected static boolean backAllowed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}