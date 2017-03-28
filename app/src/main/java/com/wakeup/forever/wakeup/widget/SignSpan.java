package com.wakeup.forever.wakeup.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.LineBackgroundSpan;

import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.app.App;

/**
 * Created by forever on 2016/9/26.
 */

public class SignSpan implements LineBackgroundSpan {

    public static final float DEFAULT_RADIUS = 3;

    private final float radius;
    private final int color;


    public SignSpan() {
        this.radius = DEFAULT_RADIUS;
        this.color = 0;
    }

    public SignSpan(int color) {
        this.radius = DEFAULT_RADIUS;
        this.color = color;
    }


    public SignSpan(float radius) {
        this.radius = radius;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified radius and color
     *
     * @param radius radius for the dot
     * @param color  color of the dot
     */
    public SignSpan(float radius, int color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint,
                               int left, int right, int top, int baseline, int bottom,
                               CharSequence charSequence,
                               int start, int end, int lineNum) {
        int oldColor = paint.getColor();
        if (color != 0) {
            paint.setColor(color);
        }
        paint.setColor(App.getGlobalContext().getResources().getColor(R.color.colorPrimary));
        String text=" ";
        paint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        canvas.drawText(text,(right-left)/2 - bounds.width()/2, (bottom-top) + bounds.height()/2+10,paint);
        paint.setColor(oldColor);
    }
}
