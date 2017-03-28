package com.wakeup.forever.wakeup.widget;

import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.app.App;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by forever on 2016/9/25.
 */

public class SignUpDecorator implements DayViewDecorator {
    private int color;
    private HashSet<CalendarDay> dates;
    private Drawable drawable;

    public SignUpDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
        drawable = App.getGlobalContext().getResources().getDrawable(R.drawable.hot);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new SignSpan());
        view.setSelectionDrawable(drawable);
    }
}
