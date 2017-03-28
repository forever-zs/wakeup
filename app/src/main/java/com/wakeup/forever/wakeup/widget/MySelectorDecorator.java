package com.wakeup.forever.wakeup.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.wakeup.forever.wakeup.R;

/**
 * Use a custom selector
 */
public class MySelectorDecorator implements DayViewDecorator {

    private Drawable drawable;
    private CalendarDay date;

    public MySelectorDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.flower);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date.equals(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }

    public void setDate(CalendarDay date) {
        this.date = date;
    }
}
