

package com.example.unicalendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private RecyclerView subjectRecyclerView;
    private SubjectAdapter subjectAdapter;
    private List<String> subjects;
    private SelectedDayDecorator selectedDayDecorator;
    private TodayDecorator currentDayDecorator;
    private CalendarDay today;
    private CalendarDay selectedDate;
    private HashMap<CalendarDay, List<String>> subjectMap = new HashMap<>();

    // Add request code for adding subjects
    private static final int REQUEST_ADD_SUBJECT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        subjectRecyclerView = findViewById(R.id.subjectRecyclerView);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectAdapter = new SubjectAdapter(new ArrayList<>());
        subjectRecyclerView.setAdapter(subjectAdapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_subject);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddSubjectActivity.class);
            startActivityForResult(intent, REQUEST_ADD_SUBJECT);
        });

        // Get today's date
        today = CalendarDay.today();
        selectedDate = today;  // Initially set selected date to today

        // Apply the default blue selection color
//        calendarView.setSelectionColor(Color.BLUE);

        // Apply a grey circle to the current day initially
        currentDayDecorator = new TodayDecorator(today);
        calendarView.addDecorator(currentDayDecorator);

        // Handle date selection
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            selectedDate = date;

            // Refresh decorators: Remove old and add new decorators
            calendarView.removeDecorators();

            // Update the grey circle for today's date
            currentDayDecorator = new TodayDecorator(today);
            calendarView.addDecorator(currentDayDecorator);

            // Update blue circle for the newly selected day
            selectedDayDecorator = new SelectedDayDecorator(date);
            calendarView.addDecorator(selectedDayDecorator);

            // Refresh subject list for the selected date
            loadSubjectsForDate(selectedDate);
        });

        // Load initial subjects for today
        loadSubjectsForDate(today);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_SUBJECT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> selectedSubjects = data.getStringArrayListExtra("selectedSubjects");
            if (selectedSubjects != null && !selectedSubjects.isEmpty()) {
                subjectMap.put(selectedDate, new ArrayList<>(selectedSubjects));

                // Decorate the date with a dot or a special marker if desired
                HashSet<CalendarDay> eventDays = new HashSet<>(subjectMap.keySet());
                calendarView.addDecorator(new EventDecorator(Color.BLUE, eventDays));  // Add blue dot for events

                updateSubjectList();
            }
        }
    }

    // Load subjects for the selected date
    private void loadSubjectsForDate(CalendarDay date) {
        List<String> subjectsForDate = subjectMap.get(date);
        if (subjectsForDate == null) {
            subjectsForDate = new ArrayList<>();
        }
        subjectAdapter.updateSubjects(subjectsForDate);
    }

    // Update RecyclerView with subjects for the selected day
    private void updateSubjectList() {
        List<String> subjectsForDate = subjectMap.get(selectedDate);
        if (subjectsForDate == null) {
            subjectsForDate = new ArrayList<>();
        }
        subjectAdapter.updateSubjects(subjectsForDate);
    }
}
