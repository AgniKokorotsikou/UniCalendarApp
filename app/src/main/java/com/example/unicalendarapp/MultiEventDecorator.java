package com.example.unicalendarapp;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class MultiEventDecorator implements DayViewDecorator {
    private final CalendarDay date;
    private final MultiDotSpan dotSpan;

    public MultiEventDecorator(MultiDotSpan dotSpan, CalendarDay date) {
        this.date = date;
        this.dotSpan = dotSpan;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(date); // Only decorate the specified date
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(dotSpan); // Apply the multi-dot span

        // Set the text color to black
        view.addSpan(new ForegroundColorSpan(Color.BLACK));
    }
}
