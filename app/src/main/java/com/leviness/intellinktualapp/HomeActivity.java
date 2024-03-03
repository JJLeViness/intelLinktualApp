package com.leviness.intellinktualapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        //declaring buttons
        Button documents = findViewById(R.id.button_Documents);
        Button  meetings = findViewById(R.id.button_meeting);
        Button flashCards = findViewById(R.id.button_flashcards);
        Button calendar = findViewById(R.id.button_Calendar);

        documents.setBackgroundColor(getResources().getColor(R.color.cyan));
        meetings.setBackgroundColor(getResources().getColor(R.color.cyan));
        flashCards.setBackgroundColor(getResources().getColor(R.color.cyan));
        calendar.setBackgroundColor(getResources().getColor(R.color.cyan));

        documents.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Intent documentIntent = new Intent(HomeActivity.this, DocumentActivity.class);
                startActivity(documentIntent);
            }
        });

       meetings.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Intent meetingIntent = new Intent(HomeActivity.this,MeetingRoomActivity.class);
               startActivity(meetingIntent);

           }

       });
       calendar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent calendarIntent = new Intent(HomeActivity.this,CalendarActivity.class);
               startActivity(calendarIntent);
           }
       });

       flashCards.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent flashCardIntent = new Intent(HomeActivity.this,flashCardActivity.class);
               startActivity(flashCardIntent);
           }
       });




    }
}