package mobileapp.wit.edu.pencilmein;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MyCalendarView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        CalendarView calendarView = findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        try {
            calendarView.setDate(calendar);
        }
        catch (Exception e){
            Log.v("Date Out of Range", e.getMessage());
        }

        List<EventDay> events = new ArrayList<>();
        TaskHandler t = new TaskHandler();
        List<String> taskDates = t.getAllTaskDates();
        Calendar cal = new GregorianCalendar();
        int month, day, year;
        for(String s: taskDates){
            if(s.length() == 10){
                month = Integer.valueOf(s.substring(0, 2)) - 1;
                day = Integer.valueOf(s.substring(3, 5));
                year = Integer.valueOf(s.substring(6));
                cal.set(year, month, day);
                EventDay e = new EventDay(cal, R.drawable.circle_event);
                events.add(e);
                Log.v("Task", "Added");
            }
        }
        calendarView.setEvents(events);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                switchToTaskView(eventDay.getCalendar().getTime());
            }
        });
        calendarView.getCurrentPageDate();


    }

    private void switchToTaskView(Date date){
        Intent intent = new Intent(this, TaskView.class);
        intent.putExtra("Date", date);
        startActivity(intent);

    }
}
