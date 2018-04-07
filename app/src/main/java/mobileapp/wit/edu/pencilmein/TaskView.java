package mobileapp.wit.edu.pencilmein;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

/**
 * Task View: list all the task for the selected date
 * Created by paudyaln on 3/21/18.
 */

public class TaskView extends AppCompatActivity {
    private TextView dateSelected;
    private TaskItemAdapter adapter;
    private StorageHandler storage;
    private TaskHandler taskHandler;
    private Button prevButton, nextButton;
    private Date c;
    private SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private String formattedDate, todayDate;
    private LayoutInflater layoutInflater;
    private ConstraintLayout constraintLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.taskViewToolbar);
        setSupportActionBar(toolbar);

        //Initialize Paper
        Paper.init(getApplicationContext());


        storage = new StorageHandler();
        List<StorageHandler.ClassListData> classes= storage.retrieveClassListObject();
        if(classes == null) {
            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(intent);
            finish();
        }
        else {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = df.format(c);


            Log.e("date in Task view", formattedDate);

            dateSelected = (TextView)findViewById(R.id.date_main);
            dateSelected.setText(formattedDate);
            getAllTask(formattedDate);




            /*
            for(StorageHandler.ClassListData i: classes){
                System.out.println(i.className);
            }
            LinearLayout ll = (LinearLayout) findViewById(R.id.classListView);
            for (StorageHandler.ClassListData i : classes) {
                TextView className = new TextView(this);
                className.setText(i.className);
                ll.addView(className);
            }
            */
        }
        listTask();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menuAdd){
            Intent intent = new Intent(this, AddTask.class);
            this.startActivity(intent);
            return true;
        }
        if(id == R.id.menuCalender){
            Intent intent = new Intent(this, MyCalendarView.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Inflate the list of task
     */
    public void getAllTask(String date){
        Log.d("Inside getALlTask", "done");
        taskHandler = new TaskHandler();
        List<Task> listOfTask = taskHandler.retrieveTasksForDate(date);

        if(listOfTask == null){
            layoutInflater = (LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.addbutton_taskview, null);
            ListView listView = (ListView) findViewById(R.id.listView_taskview);
            listView.setAdapter(new ArrayAdapter<Task>(this, R.layout.addbutton_taskview));
            Log.d("Cannot inflate", "failed");
        }
        else {
            Log.d("Inflate task in view", "done");
            adapter = new TaskItemAdapter(this, 0, listOfTask);
            ListView listView = (ListView) findViewById(R.id.listView_taskview);
            listView.setAdapter(adapter);
        }
    }

    public Date getPreviousDate(Date date){
        Date previousDate = null;
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date); //Setting the today date
            c.add(Calendar.DATE, -1); // Decreasing 1 day
            previousDate = c.getTime();
        }
        return previousDate;
    }

    public Date getNextDate(Date date){
        Date nextDate = null;
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date); // Setting the today date
            c.add(Calendar.DATE, 1); // Increasing 1 day
            nextDate = c.getTime();
        }
        return nextDate;
    }


    public void listTask(){

        storage = new StorageHandler();
        List<StorageHandler.ClassListData> classes= storage.retrieveClassListObject();
        if(classes == null) {
            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(intent);
            finish();
        }
        else {

            c = Calendar.getInstance().getTime();
            todayDate = df.format(c);

            prevButton = (Button)findViewById(R.id.button_prev);
            nextButton = (Button)findViewById(R.id.button_next);

            dateSelected = (TextView)findViewById(R.id.date_main);
            dateSelected.setText("Today");
            getAllTask(todayDate);

            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = getPreviousDate(c);
                    formattedDate = df.format(c);
                    if(formattedDate.equals(todayDate)){
                        dateSelected.setText("Today");
                    }
                    else {
                        dateSelected.setText(formattedDate);
                    }
                    getAllTask(formattedDate);
                }
            });

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = getNextDate(c);
                    formattedDate = df.format(c);
                    if(formattedDate.equals(todayDate)){
                        dateSelected.setText("Today");
                    }
                    else {
                        dateSelected.setText(formattedDate);
                    }
                    getAllTask(formattedDate);
                }
            });


            Log.e("date in Task view", c.toString());

            Date d = getNextDate(c);
            Log.e("date in Task view", d.toString());

            Date e = getPreviousDate(c);
            Log.e("date in Task view", e.toString());



        }
    }

}