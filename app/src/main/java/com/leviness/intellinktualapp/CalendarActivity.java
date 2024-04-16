package com.leviness.intellinktualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private ListView eventList;
    private ArrayAdapter<String> eventAdapter;

    private Map<Date, List<String>> eventMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ImageButton calendarHome = findViewById(R.id.calendar_home);
        CalendarView eventCalendar = findViewById(R.id.meeting_cv);

        eventList = findViewById(R.id.calendar_Lv);

        // Initialize the adapter for the ListView
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        eventList.setAdapter(eventAdapter);
        eventMap = new HashMap<>();

        // Set listener for date changes in the CalendarView
        eventCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Date selectedDate = getDate(year, month, dayOfMonth);
                updateEventList(selectedDate);
            }
        });

        calendarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(CalendarActivity.this,HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        // Set listener for add event button click
        ImageButton addEventButton = findViewById(R.id.addEvent_button);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the layout for the pop-up window
                LayoutInflater inflater = LayoutInflater.from(CalendarActivity.this);
                View popupView = inflater.inflate(R.layout.add_event_layout, null);

                // Find views within the pop-up window
                EditText eventNameEditText = popupView.findViewById(R.id.enterEventName);
                EditText eventDateEditText = popupView.findViewById(R.id.editTextDate);
                EditText eventTimeEditText = popupView.findViewById(R.id.editTextTime);
                ImageButton finishAddButton = popupView.findViewById(R.id.addEvent_imageButton);

                // Create the AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
                builder.setView(popupView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                // Set a listener for the finish add button
                finishAddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String eventName = eventNameEditText.getText().toString();
                        String eventDate = eventDateEditText.getText().toString();
                        String eventTime = eventTimeEditText.getText().toString();

                        // Convert eventDate to Date object
                        // Add event to the list for the selected date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                        Date selectedDate = null;
                        try {
                            selectedDate = dateFormat.parse(eventDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                                addEventToList(selectedDate, eventName);

                        // Update the event list for the selected date
                        updateEventList(selectedDate);

                        // Dismiss the pop-up window after adding the event
                        alertDialog.dismiss();

                    }
                });


            }
        });

    }

    // Method to update the ListView with events for the selected date
    private void updateEventList(int year, int month, int dayOfMonth) {
        // Clear existing events
        eventAdapter.clear();


        List<String> events = getEventsForDate(year, month, dayOfMonth);

        // Add events to the ListView
        if (events != null && !events.isEmpty()) {
            eventAdapter.addAll(events);
        } else {
            eventAdapter.add("No events scheduled for this date.");
        }
    }

    // Dummy method to get events for the selected date
    private List<String> getEventsForDate(int year, int month, int dayOfMonth) {
        //dummy events
        List<String> events = new ArrayList<>();
        events.add("Event 1 - 10:00 AM");
        events.add("Event 2 - 2:00 PM");
        return events;
    }




    private void updateEventList(Date selectedDate) {
        eventAdapter.clear();
        List<String> events = eventMap.get(selectedDate);
        if (events != null && !events.isEmpty()) {
            eventAdapter.addAll(events);
        } else {
            eventAdapter.add("No events scheduled for this date.");
        }
    }

    private Date getDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.getTime();
    }

    // Method to add event to the calendar list
    private void addEventToList(Date date, String eventName) {
        List<String> events = eventMap.get(date);
        if (events == null) {
            events = new ArrayList<>();
            eventMap.put(date, events);
        }
        events.add(eventName);
    }
}