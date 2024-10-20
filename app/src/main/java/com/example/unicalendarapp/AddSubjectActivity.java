package com.example.unicalendarapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import android.app.TimePickerDialog;
import android.widget.TextView;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashMap;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddSubjectActivity extends AppCompatActivity {
    private TextView timeTextView;  // Display selected time
    private String selectedTime;
    private String description;
    private CheckBox mathCheckBox, historyCheckBox, physicsCheckBox;
    private Spinner repeatSpinner;
    private Button mathColorButton, historyColorButton, physicsColorButton;
    private int mathColor = Color.BLACK; // Default color for Math
    private int historyColor = Color.BLACK; // Default color for History
    private int physicsColor = Color.BLACK; // Default color for Physics

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        // Initialize the checkboxes
        mathCheckBox = findViewById(R.id.checkbox_math);
        historyCheckBox = findViewById(R.id.checkbox_history);
        physicsCheckBox = findViewById(R.id.checkbox_physics);

        // Initialize the repeat spinner
        repeatSpinner = findViewById(R.id.spinner_repeat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.repeat_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(adapter);

        // Initialize color buttons and their click listeners
        mathColorButton = findViewById(R.id.color_button_math);
        historyColorButton = findViewById(R.id.color_button_history);
        physicsColorButton = findViewById(R.id.color_button_physics);

        mathColorButton.setOnClickListener(v -> showColorPicker(true, 0));
        historyColorButton.setOnClickListener(v -> showColorPicker(false, 1));
        physicsColorButton.setOnClickListener(v -> showColorPicker(false, 2));

        // Initialize the timeTextView
        timeTextView = findViewById(R.id.timeTextView);
        // Set up a click listener to open the TimePicker dialog when timeTextView is clicked
        timeTextView.setOnClickListener(v -> showTimePickerDialog());

        // Handle the "Done" button click
        findViewById(R.id.btn_done).setOnClickListener(v -> {
            ArrayList<String> selectedSubjects = new ArrayList<>();
            HashMap<String, Integer> subjectColors = new HashMap<>();

            if (mathCheckBox.isChecked()) {
                selectedSubjects.add("Math");
                subjectColors.put("Math", mathColor);
            }
            if (historyCheckBox.isChecked()) {
                selectedSubjects.add("History");
                subjectColors.put("History", historyColor);
            }
            if (physicsCheckBox.isChecked()) {
                selectedSubjects.add("Physics");
                subjectColors.put("Physics", physicsColor);
            }

            // Get the selected repeat option
            String repeatOption = repeatSpinner.getSelectedItem().toString();

            // Return the selected subjects, repeat option, and colors to MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putStringArrayListExtra("selectedSubjects", selectedSubjects);
            resultIntent.putExtra("repeatOption", repeatOption);
            resultIntent.putExtra("subjectColors", subjectColors); // Pass the colors map back
            setResult(RESULT_OK, resultIntent);

            resultIntent.putStringArrayListExtra("selectedSubjects", selectedSubjects);
            resultIntent.putExtra("subjectTime", selectedTime);  // Add the selected time
//            resultIntent.putExtra("subjectDescription", description);  // Already handled for description
            setResult(RESULT_OK, resultIntent);

            finish();
        });
    }

    private void showColorPicker(boolean isMath, int subjectIndex) {
        int initialColor = isMath ? mathColor : (subjectIndex == 1 ? historyColor : physicsColor);
        new AmbilWarnaDialog(this, initialColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                if (isMath) {
                    mathColor = color; // Save the selected color for Math
                } else if (subjectIndex == 1) {
                    historyColor = color; // Save the selected color for History
                } else {
                    physicsColor = color; // Save the selected color for Physics
                }
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // Do nothing on cancel
            }
        }).show();
    }

    private void showTimePickerDialog() {
        // Use Calendar to get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minuteOfHour) -> {
                    // Format the time as HH:mm
                    selectedTime = String.format("%02d:%02d", hourOfDay, minuteOfHour);
                    timeTextView.setText(selectedTime);  // Update the TextView with selected time
                }, hour, minute, true);  // 'true' for 24-hour format, false for AM/PM

        // Show the dialog
        timePickerDialog.show();
    }
}
