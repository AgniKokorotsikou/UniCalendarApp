package com.example.unicalendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;

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
    private HashMap<String, Integer> colorMap = new HashMap<String, Integer>();

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
        // Attach the helper to the RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(subjectRecyclerView);


        FloatingActionButton fab = findViewById(R.id.fab_add_subject);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddSubjectActivity.class);
            startActivityForResult(intent, REQUEST_ADD_SUBJECT);
        });

        // Get today's date
        today = CalendarDay.today();
        selectedDate = today;

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

            // Update event days and decorators
            updateEventDecorators();
        });
        // Load initial subjects for today
        loadSubjectsForDate(today);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_SUBJECT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> selectedSubjects = data.getStringArrayListExtra("selectedSubjects");
            String repeatOption = data.getStringExtra("repeatOption"); // Get the repeat option
//            int selectedColor = data.getIntExtra("selectedColor", Color.BLACK); // Get the chosen color
            HashMap<String, Integer> subjectColors = (HashMap<String, Integer>) data.getSerializableExtra("subjectColors");
            if (selectedSubjects != null && !selectedSubjects.isEmpty()) {
                subjectMap.put(selectedDate, new ArrayList<>(selectedSubjects));

                HashSet<CalendarDay> eventDays = new HashSet<>(subjectMap.keySet());

                // Store color and subjects
//                colorMap.put(selectedDate, selectedColor);
                // Iterate through the selected subjects to retrieve and store their corresponding colors
                for (String subject : selectedSubjects) {
                    subjectMap.put(selectedDate, new ArrayList<>(selectedSubjects));

                    // Ensure colors are retrieved safely
                    if (subjectColors.containsKey(subject)) {
                        int color = subjectColors.get(subject);
                        // Store the color associated with the date, depending on your design
                        colorMap.put(subject, color); // Map by subject
                    }
                }

                // Handle the subjects and repeat option here, e.g., add to your subjectMap
                subjectMap.put(selectedDate, new ArrayList<>(selectedSubjects));

                // Optionally, apply your repeat logic here (if you want to automatically add repeated events)
                applyRepeatOptions(selectedDate, selectedSubjects, repeatOption);

                updateEventDecorators();
                updateSubjectList();
            }
        }
    }

    private void applyRepeatOptions(CalendarDay date, List<String> subjects, String repeatOption) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth(), date.getDay());

        // Handle different repeat options
        switch (repeatOption) {
            case "Daily":
                for (int i = 1; i <= 365; i++) { // Repeat for a year
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    CalendarDay newDate = CalendarDay.from(calendar.getTime());
                    subjectMap.put(newDate, new ArrayList<>(subjects));
                }
                break;

            case "Weekly":
                for (int i = 1; i <= 52; i++) {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                    CalendarDay newDate = CalendarDay.from(calendar.getTime());
                    subjectMap.put(newDate, new ArrayList<>(subjects));
                }
                break;

            case "Monthly":
                for (int i = 1; i <= 12; i++) {
                    calendar.add(Calendar.MONTH, 1);
                    CalendarDay newDate = CalendarDay.from(calendar.getTime());
                    subjectMap.put(newDate, new ArrayList<>(subjects));
                }
                break;
        }

        // After adding the repeating events, update the decorators
        updateEventDecorators();
    }

    // Update event decorators for all dates
    private void updateEventDecorators() {
        calendarView.removeDecorators(); // Remove all previous decorators

        // Apply today's decorator and selected date decorator
        calendarView.addDecorator(currentDayDecorator);
        calendarView.addDecorator(selectedDayDecorator);

        // Add event decorators for each day with subjects
        for (CalendarDay day : subjectMap.keySet()) {
            List<String> subjectsForDay = subjectMap.get(day);
            List<Integer> dotColors = getDotColorsForSubjects(subjectsForDay);
            calendarView.addDecorator(new MultiEventDecorator(new MultiDotSpan(dotColors), day)); //it works fine this way

//            int dotColor = colorMap.get(day); // Get the color for the current day
//            calendarView.addDecorator(new MultiEventDecorator(new MultiDotSpan(Collections.singletonList(dotColor)), day));
        }
    }


    // Function to generate dot colors based on subjects
//    private List<Integer> getDotColorsForSubjects(List<String> subjects) {
//        List<Integer> colors = new ArrayList<>();
//        for (String subject : subjects) {
//            if (subject.contains("Math")) {
//                colors.add(Color.BLUE);
//            } else if (subject.contains("History")) {
//                colors.add(Color.RED);
//            } else if (subject.contains("Physics")) {
//                colors.add(Color.GREEN);
//            } else {
//                colors.add(Color.BLACK); // Default color
//            }
//        }
//        return colors;
//    }

    private List<Integer> getDotColorsForSubjects(List<String> subjects) {
        List<Integer> dotColors = new ArrayList<>();
        for (String subject : subjects) {
            if (colorMap.containsKey(subject)) {
                int color = colorMap.get(subject); // Get color for each subject
                dotColors.add(color);
            }
        }
        return dotColors;
    }

    public class DefaultDayTextDecorator implements DayViewDecorator {
        private final int defaultTextColor;

        public DefaultDayTextDecorator(int color) {
            this.defaultTextColor = color; // Set your default text color here
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;  // Apply this to all days
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(defaultTextColor));  // Set the default text color
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

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // Move the subject/event in the RecyclerView
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(subjects, fromPosition, toPosition);
            subjectAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // No swipe behavior needed
        }
    };
}