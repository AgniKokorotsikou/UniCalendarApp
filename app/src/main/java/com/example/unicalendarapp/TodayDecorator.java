package com.example.unicalendarapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class TodayDecorator implements DayViewDecorator {

    private CalendarDay currentDate;

    public TodayDecorator(CalendarDay currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(currentDate); // Only decorate the current day
    }

    @Override
    public void decorate(DayViewFacade view) {
        // Apply grey circle to today's date
        view.addSpan(new CustomCircleSpan(Color.GRAY));

        // Create a grey circle drawable
//        ShapeDrawable greyCircle = new ShapeDrawable(new OvalShape());
//        greyCircle.getPaint().setColor(Color.GRAY);
//        greyCircle.setIntrinsicWidth(20);  // Adjust the size as needed
//        greyCircle.setIntrinsicHeight(20); // Adjust the size as needed
//
//        // Apply the grey circle to the current day
//        view.setBackgroundDrawable(greyCircle);

        // Set the text color for the current day (you can modify this if necessary)
        view.addSpan(new ForegroundColorSpan(Color.BLACK));
    }
}
