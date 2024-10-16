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
        holder.subjectTextView.setText(subject);  // Set the subject text
    }

    // Inside ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.subjectTextView);  // Link the TextView to the layout
        }
    }
}

