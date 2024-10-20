package com.example.unicalendarapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    private List<String> subjects;

    public SubjectAdapter(List<String> subjects) {
        this.subjects = subjects;
    }

    public void updateSubjects(List<String> newSubjects) {
        subjects.clear();
        subjects.addAll(newSubjects);  // Update the list with new subjects
        notifyDataSetChanged();  // Notify the adapter of the data change
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        // Display the time for each subject
        if (time != null && !time.isEmpty()) {
            holder.timeTextView.setText("Time: " + time);
            holder.timeTextView.setVisibility(View.VISIBLE);  // Ensure visibility if time exists
        } else {
            holder.timeTextView.setVisibility(View.GONE);  // Hide if no time is set
        }

        // Display description if available
        if (description != null && !description.isEmpty()) {
            holder.descriptionTextView.setText("Description: " + description);
        } else {
            holder.descriptionTextView.setVisibility(View.GONE);  // Hide if no description is set
        }
    }

    // Inside ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView;
        TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time_text_view);        // Link time TextView
//            descriptionTextView = itemView.findViewById(R.id.description_text_view);  // Link description TextView
        }
    }
}

