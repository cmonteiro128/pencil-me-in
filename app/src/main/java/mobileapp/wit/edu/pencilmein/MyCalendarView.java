package mobileapp.wit.edu.pencilmein;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MyCalendarView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        CalendarView myCalendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(date.getTime());

        TaskHandler taskHandler = new TaskHandler();
        Log.v("asjkdhjakshdkjahskjdhk", currentDate);
        List<Task> tasks = taskHandler.retrieveTasksForDate(currentDate);

        if(tasks != null)
        for(Task t: tasks){
           t.printTask();
        }
        else
            Log.v("ERRRORORRROROROROr", "Null");
    }
}
