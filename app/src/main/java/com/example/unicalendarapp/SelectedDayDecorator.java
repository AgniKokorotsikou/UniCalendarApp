package com.example.unicalendarapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.style.ForegroundColorSpan;

import com.example.unicalendarapp.CustomCircleSpan;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class SelectedDayDecorator implements DayViewDecorator {

    private CalendarDay selectedDate;

    public SelectedDayDecorator(CalendarDay selectedDate) {
        this.selectedDate = selectedDate;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(selectedDate); // Only decorate the selected day
    }

    @Override
    public void decorate(DayViewFacade view) {
        // Apply a custom circle span (blue circle for the selected day)
        view.addSpan(new CustomCircleSpan(Color.BLUE));

        // Set the text color for the selected day
        view.addSpan(new ForegroundColorSpan(Color.WHITE));
    }
}
