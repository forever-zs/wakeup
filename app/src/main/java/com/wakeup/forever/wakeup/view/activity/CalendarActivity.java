package com.wakeup.forever.wakeup.view.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.activityPresenter.CalendarActivityPresenter;
import com.wakeup.forever.wakeup.widget.MySelectorDecorator;
import com.wakeup.forever.wakeup.widget.SignUpDecorator;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LL on 2016/8/28.
 */

@RequiresPresenter(CalendarActivityPresenter.class)
public class CalendarActivity extends BaseActivity<CalendarActivityPresenter> {
    @Bind(R.id.mcv_calendarView)
    MaterialCalendarView mcvCalendarView;
    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;

    //private ArrayList<Calendar> calendarArrayList;
    private ProgressDialog progressDialog;

    private MySelectorDecorator mySelectorDecorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_calendar_view);
        ButterKnife.bind(this);
        /*calendarArrayList = new ArrayList<Calendar>();
        getPresenter().initData();*/
        initData();
        initView();

    }

    private void initData() {
        mySelectorDecorator=new MySelectorDecorator(this);
        getPresenter().initData();
    }

    public void onGetSignDays(ArrayList<Calendar> calendars){
        ArrayList<CalendarDay> dates = new ArrayList<>();
        for(Calendar calendar:calendars){
            CalendarDay day = CalendarDay.from(calendar);
            dates.add(day);
        }
        mcvCalendarView.addDecorator(new SignUpDecorator(Color.RED, dates));

    }

    public  void initView() {
        mcvCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                /*signUpDecorator.setDate(date.getDate());
                widget.invalidateDecorators();*/
                mySelectorDecorator.setDate(date);
                mcvCalendarView.invalidateDecorators();
            }
        });
        mcvCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        Calendar instance = Calendar.getInstance();
        mcvCalendarView.setSelectedDate(instance.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        mcvCalendarView.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();

        mcvCalendarView.addDecorator(mySelectorDecorator);//设置选中时的日期样式

        mySelectorDecorator.setDate(CalendarDay.today());
        mcvCalendarView.invalidateDecorators();
    }

    /*public ArrayList<Calendar> getCalendarArrayList() {
        return calendarArrayList;
    }*/

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("加载中...");
        progressDialog.show();
    }

    @OnClick({R.id.ib_back, R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_title:
                break;
            case R.id.rl_title:
                break;
        }
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    /*public ArrayList<Integer> getSignDays(int year, int month) {
        ArrayList<Integer> days = new ArrayList<Integer>();

        for (Calendar calendar : calendarArrayList) {

            if ((calendar.get(Calendar.YEAR) == year) && (calendar.get(Calendar.MONTH) == month)) {
                days.add(calendar.get(Calendar.DAY_OF_MONTH));
            }
        }
        return days;
    }
    */


}








