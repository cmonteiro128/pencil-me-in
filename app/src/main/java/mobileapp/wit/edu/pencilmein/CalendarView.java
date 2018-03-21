package mobileapp.wit.edu.pencilmein;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alamkanak.weekview.WeekView;

public class CalendarView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        // Get a reference for the week view in the layout.
        //mWeekView = (WeekView) findViewById(R.id.weekView);

// Set an action when any event is clicked.
        //mWeekView.setOnEventClickListener(mEventClickListener);

// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week view.
        //mWeekView.setMonthChangeListener(mMonthChangeListener);

// Set long press listener for events.
        //mWeekView.setEventLongPressListener(mEventLongPressListener);
    }
}
