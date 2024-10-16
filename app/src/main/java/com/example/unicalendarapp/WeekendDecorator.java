package com.example.unicalendarapp;

import android.graphics.Color;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import android.text.style.ForegroundColorSpan;

import java.util.Calendar;

public class WeekendDecorator implements DayViewDecorator {

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Calendar calendar = day.getCalendar();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLUE)); // Red text for weekends
    }
}
