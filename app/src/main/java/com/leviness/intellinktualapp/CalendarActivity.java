package com.leviness.intellinktualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
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

    SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ImageButton calendarHome = findViewById(R.id.calendar_home);
        CalendarView eventCalendar = findViewById(R.id.meeting_cv);
        ImageButton addEvent = findViewById(R.id.addEvent_button);
        ListView eventList = findViewById(R.id.calendar_Lv);
        ImageButton export = findViewById(R.id.calendar_export);
        sharedPreferences = getSharedPreferences("MyEvents", MODE_PRIVATE);

        // Initialize the adapter for the ListView
        eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        eventList.setAdapter(eventAdapter);

        eventList.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectedDate = getDateFromCalendar();
            String eventToDelete = eventAdapter.getItem(position);
            showConfirmationDialog(selectedDate, eventToDelete);
        });



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

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, ?> allEntries = sharedPreferences.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    String date = entry.getKey();
                    List<String> events = getEvents(date);
                    if (events != null && !events.isEmpty()) {
                        // Export each event associated with the date to the device's calendar
                        for (String event : events) {
                            exportToCalendar(date, event);
                        }
                    }
                }



            }
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


                        addEventToList(eventDate, eventName,eventTime);



                        // Update the event list for the selected date
                        updateEventList(eventDate);

                        // Dismiss the pop-up window after adding the event
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    private void updateEventList(String selectedDate) {
        List<String> events = getEvents(selectedDate);
        eventAdapter.clear();
        if (events != null && !events.isEmpty()) {
            eventAdapter.addAll(events);
        } else {
            eventAdapter.add("No events scheduled for this date.");
        }

        Log.d("CalendarActivity", "Events for date " + selectedDate + ": " + events);

    }


    private void addEventToList(String selectedDate, String eventName,String eventTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        List<String> events = getEvents(selectedDate);
        if (events == null) {
            events = new ArrayList<>();
        }

        String event = eventName + " at " + eventTime;
        events.add(event);

        String eventsJson = gson.toJson(events);
        editor.putString(selectedDate, eventsJson);
        editor.apply();

        Log.d("CalendarActivity", "Event added to date " + selectedDate + ": " + eventName);
    }
    private List<String> getEvents(String selectedDate) {
        String eventsJson = sharedPreferences.getString(selectedDate, null);
        if (eventsJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {}.getType();
            return gson.fromJson(eventsJson, type);
        }
        return null;
    }


    private void deleteEvent(String selectedDate, String eventToDelete) {
        List<String> events = getEvents(selectedDate);
        if (events != null) {
            events.remove(eventToDelete);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String eventsJson = gson.toJson(events);
            editor.putString(selectedDate, eventsJson);
            editor.apply();
            Log.d("CalendarActivity", "Event deleted from date " + selectedDate + ": " + eventToDelete);
        }
        // Update the event list for the selected date
        updateEventList(selectedDate);
    }




    private void showConfirmationDialog(final String selectedDate, final String eventToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this event?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteEvent(selectedDate, eventToDelete);
                        updateEventList(selectedDate);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    private void exportToCalendar(String selectedDate, String eventTitle) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, eventTitle);
        startActivity(intent);
    }


    private String getDateFromCalendar() {
        CalendarView eventCalendar = findViewById(R.id.meeting_cv);
        long selectedDateMillis = eventCalendar.getDate();
        Date selectedDate = new Date(selectedDateMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return dateFormat.format(selectedDate);
    }


    private String getDate(int year, int month, int dayOfMonth) {
        return String.format(Locale.getDefault(), "%02d/%02d/%04d", (month + 1), dayOfMonth, year);
    }
}
