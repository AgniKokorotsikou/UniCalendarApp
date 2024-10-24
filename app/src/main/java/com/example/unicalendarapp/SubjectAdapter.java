package com.example.unicalendarapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    private List<String> subjects;
    private HashMap<String, String> subjectTimes; // Add this for subject times
    private String description;

    // Constructor to pass in the list of subjects, time, and description
    public SubjectAdapter(List<String> subjects) {
        this.subjects = subjects;
        this.subjectTimes = subjectTimes;
        this.description = description;
    }

    public void updateSubjects(List<String> newSubjects, HashMap<String, String> newTimes, String description) {
        subjects.clear();
        subjects.addAll(newSubjects);  // Update the list with new subjects
        this.subjectTimes = newTimes;
        this.description = description;  // Assuming you have a field to store the description
        notifyDataSetChanged();  // Notify the adapter of the data change
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the subject_item layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String subject = subjects.get(position);
        String time = "";  // Retrieve this from the timeMap (pass this when updating the adapter)
        String description = "";  // Retrieve this from the descriptionMap

        holder.subjectTextView.setText(subject);  // Set the subject text

        // Set the time for the subject if available
        time = subjectTimes.get(subject);
        if (time != null) {
            holder.timeTextView.setText(time);  // Display the associated time
        } else {
            holder.timeTextView.setText("No time selected"); // Or some default
        }
        // Set the description
        holder.descriptionTextView.setText("Description: " + description);

        // If description is available, show it
//        if (description != null && !description.isEmpty()) {
//            holder.descriptionTextView.setText("Description: " + description);
//            holder.descriptionTextView.setVisibility(View.VISIBLE);
//        } else {
//            holder.descriptionTextView.setVisibility(View.GONE); // Hide if no description is set
//        }
    }

    // Inside ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView;
        TextView timeTextView;
        TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.subjectTextView);
            timeTextView = itemView.findViewById(R.id.time_text_view);        // Link time TextView
            descriptionTextView = itemView.findViewById(R.id.description_text_view);  // Link description TextView
        }
    }
}

