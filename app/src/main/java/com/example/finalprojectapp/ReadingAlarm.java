package com.example.finalprojectapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class ReadingAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_alarm);

        TimePicker alarm_time = findViewById(R.id.timePicker);

        Button start_timer = findViewById(R.id.startAlarm);

        start_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = alarm_time.getHour()*60;
                int minute = alarm_time.getMinute();

                Log.d("Reading Alar", "Starting alarm from " + hour + ":" + minute);

                long alarm = (long) (hour + minute) *1000*60;
                Log.d("Testing alarm - current time", String.valueOf(alarm));

                long cur_time = (System.currentTimeMillis() % 86400000) - 14400000;


                Log.d("Testing alarm - current time", String.valueOf(cur_time));


                long dif_time = alarm - cur_time;

                //Creating a pending intent for sendNotification class.
                Intent intent = new Intent(getApplicationContext(), SendNotification.class);
                PendingIntent pendingIntent = null;

                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //Generating object of alarmManager using getSystemService method. Here ALARM_SERVICE is used to receive alarm manager with intent at a time.
                AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

                //this method creates a repeating, exactly timed alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, dif_time, pendingIntent); //1 minute apart
                Log.d("alarm set for", String.valueOf(dif_time));
                Log.d("===Sensing alarm===", "One time alert alarm has been created. This alarm will send to a broadcast sensing receiver.");




            }
        });
    }
}