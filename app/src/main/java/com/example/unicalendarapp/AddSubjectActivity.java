package com.example.unicalendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AddSubjectActivity extends AppCompatActivity {

    private CheckBox mathCheckBox, historyCheckBox, physicsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        // Initialize the checkboxes
        mathCheckBox = findViewById(R.id.checkbox_math);
        historyCheckBox = findViewById(R.id.checkbox_history);
        physicsCheckBox = findViewById(R.id.checkbox_physics);

        // Handle the "Done" button click
        findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a list of selected subjects
                ArrayList<String> selectedSubjects = new ArrayList<>();
                if (mathCheckBox.isChecked()) {
                    selectedSubjects.add("Math");
                }
                if (historyCheckBox.isChecked()) {
                    selectedSubjects.add("History");
                }
                if (physicsCheckBox.isChecked()) {
                    selectedSubjects.add("Physics");
                }

                // Return the selected subjects to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("selectedSubjects", selectedSubjects);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
