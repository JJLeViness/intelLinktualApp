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
import android.widget.Toast;

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

    private ArrayAdapter<String> eventAdapter;
    private Map<String, List<String>> eventMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ImageButton calendarHome = findViewById(R.id.calendar_home);
        CalendarView eventCalendar = findViewById(R.id.meeting_cv);
        ImageButton addEvent = findViewById(R.id.addEvent_button);
        ListView eventList = findViewById(R.id.calendar_Lv);

        // Initialize the adapter for the ListView
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        eventList.setAdapter(eventAdapter);

        // Initialize event map
        eventMap = new HashMap<>();

        // Set listener for date changes in the CalendarView
        eventCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = getDate(year, month, dayOfMonth);
            updateEventList(selectedDate);
        });

        calendarHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(CalendarActivity.this, HomeActivity.class);
            startActivity(homeIntent);
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(CalendarActivity.this);
                View popupView = inflater.inflate(R.layout.add_event_layout, null);
                EditText eventNameEditText = popupView.findViewById(R.id.enterEventName);
                EditText eventDateEditText = popupView.findViewById(R.id.editTextDate);
                EditText eventTimeEditText = popupView.findViewById(R.id.editTextTime);
                ImageButton finishAddButton = popupView.findViewById(R.id.addEvent_imageButton);

                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
                builder.setView(popupView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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
                        addEventToList(String.valueOf(selectedDate), eventName);

                        // Update the event list for the selected date
                        updateEventList(String.valueOf(selectedDate));

                        // Dismiss the pop-up window after adding the event
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }
    // Method to update the ListView with events for the selected date
    private void updateEventList(String selectedDate) {
        eventAdapter.clear();
        List<String> events = eventMap.get(selectedDate);
        if (events != null && !events.isEmpty()) {
            eventAdapter.addAll(events);
        } else {
            eventAdapter.add("No events scheduled for this date.");
        }
    }

    // Method to add event to the calendar list
    private void addEventToList(String date, String eventName) {
        List<String> events = eventMap.get(date);
        if (events == null) {
            events = new ArrayList<>();
            eventMap.put(date, events);
        }
        events.add(eventName);
    }

    // Method to convert year, month, and day to a date string
    private String getDate(int year, int month, int dayOfMonth) {
        return year + "-" + String.format(Locale.getDefault(), "%02d", (month + 1)) + "-" + String.format(Locale.getDefault(), "%02d", dayOfMonth);
    }
}
