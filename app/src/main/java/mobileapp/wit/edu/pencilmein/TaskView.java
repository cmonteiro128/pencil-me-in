package mobileapp.wit.edu.pencilmein;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by chris on 3/21/18.
 */

public class TaskView extends AppCompatActivity {
    private TextView dateSelected;
    private TaskItemAdapter adapter;
    private StorageHandler storage;
    private TaskHandler taskHandler;

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
     *
     */
    public void getAllTask(String date){
        Log.d("Inside getALlTask", "done");
        taskHandler = new TaskHandler();
        List<Task> listOfTask = taskHandler.retrieveTasksForDate(date);

        if(listOfTask == null){
            Log.d("Cannot inflate", "failed");
        }
        else {
            Log.d("Inflate task in view", "done");
            adapter = new TaskItemAdapter(this, 0, listOfTask);
            ListView listView = (ListView) findViewById(R.id.listView_taskview);
            listView.setAdapter(adapter);
        }
    }


}