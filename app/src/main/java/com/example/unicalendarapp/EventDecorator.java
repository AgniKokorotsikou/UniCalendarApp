package com.example.unicalendarapp;

import android.graphics.Color;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {
    private final int color;
    private final HashSet<CalendarDay> dates;

    public EventDecorator(int color, HashSet<CalendarDay> dates) {
        this.color = color;
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);  // Only decorate days that have events/subjects
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));  // Add a dot to the date with the specified color
    }
}
