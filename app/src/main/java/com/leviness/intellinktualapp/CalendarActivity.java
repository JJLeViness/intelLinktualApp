package com.leviness.intellinktualapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);



        ImageButton calendarHome = findViewById(R.id.calendar_home);
        ImageButton addEvent=findViewById(R.id.addEvent_button);
        calendarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(CalendarActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(CalendarActivity.this);
                View popupView = inflater.inflate(R.layout.add_event_layout,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
                builder.setView(popupView);

                ImageButton finishAdd = findViewById(R.id.addEvent_imageButton);
                EditText EventName = findViewById(R.id.enterEventName);
                EditText eventDate = findViewById(R.id.editTextDate);
                EditText eventTime = findViewById(R.id.editTextTime);

                //builder.setPositiveButton(R.id.addEvent_button)

            }
        });


    }
}