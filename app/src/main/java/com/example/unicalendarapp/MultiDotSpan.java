package com.example.unicalendarapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import java.util.List;

public class MultiDotSpan implements LineBackgroundSpan {
    private final List<Integer> colors;

    public MultiDotSpan(List<Integer> colors) {
        this.colors = colors;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence charSequence, int start, int end, int lineNumber) {
        int totalDots = colors.size();
        float radius = 6f;
        float centerX = (left + right) / 2f;
        float centerY = bottom + radius * 2;

        for (int i = 0; i < totalDots; i++) {
            paint.setColor(colors.get(i));
            canvas.drawCircle(centerX - (totalDots - 1) * radius + (i * 2 * radius), centerY, radius, paint);
        }
    }
}
