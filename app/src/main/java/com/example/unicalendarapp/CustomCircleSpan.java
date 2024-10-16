package com.example.unicalendarapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

public class CustomCircleSpan implements LineBackgroundSpan {
    private final int color;

    public CustomCircleSpan(int color) {
        this.color = color;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence charSequence, int start, int end, int lineNum) {
        int oldColor = paint.getColor();  // Save the original color
        paint.setColor(color);  // Set the new color

        // Calculate the circle's position and radius
        float radius = (right - left) / 2f;
        canvas.drawCircle((left + right) / 2f, (top + bottom) / 2f, radius, paint);  // Draw the circle

        paint.setColor(oldColor);  // Restore the original color
    }
}
