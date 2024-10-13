package com.example.unicalendarapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView subjectRecyclerView;
    private SubjectAdapter subjectAdapter;
    private List<String> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Use activity_main.xml layout

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        subjectRecyclerView = findViewById(R.id.subjectRecyclerView);

        // Initialize subject list
        subjects = new ArrayList<>();

        // Setup RecyclerView
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectAdapter = new SubjectAdapter(subjects);
        subjectRecyclerView.setAdapter(subjectAdapter);

        // Handle date changes
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                loadSubjectsForDate(year, month, dayOfMonth);
            }
        });

        // Load initial subjects (could be current date)
        loadSubjectsForDate(2024, 10, 13);  // Just an example date
    }

    private void loadSubjectsForDate(int year, int month, int dayOfMonth) {
        subjects.clear();

        // Add sample subjects (could be replaced with real data later)
        subjects.add("Math - 10:00 AM");
        subjects.add("History - 12:00 PM");
        subjects.add("Physics - 2:00 PM");

        // Notify the adapter to refresh the RecyclerView
        subjectAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Subjects loaded for: " + dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
    }
}
