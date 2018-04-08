package mobileapp.wit.edu.pencilmein;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.paperdb.Paper;

/**
 * AddTask layout
 * Created by paudyaln on 3/25/2018.
 */

public class AddTask extends AppCompatActivity{
    private EditText des;
    private TextView dueTime, dueDate;
    private Spinner classSpinner;
    private NumberPicker notificationSpinner;
    private Button done;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private Date currentDate;
    private int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_addtask);
        calendar = new GregorianCalendar();

        Bundle b = getIntent().getExtras();
        if(b != null){
            currentDate = (Date)b.get("Date");
            calendar.setTime(currentDate);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.addtaskToolbar);
        setSupportActionBar(toolbar);

        //Initialize Paper
        Paper.init(getApplicationContext());

        addItemToSpinner();
        addListenerInDate();
        addItemToNotificationSpinner();
        openTimePickerDialog();
        addListenerInButton();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addListenerInButton(){
        //Class List Spinner
        classSpinner = (Spinner)findViewById(R.id.classListSpinner);
        des = (EditText)findViewById(R.id.addTaskDescription);
        done = (Button)findViewById(R.id.addTaskButton);
        notificationSpinner = (NumberPicker)findViewById(R.id.alarmHourPicker);
        dueDate = (TextView) findViewById(R.id.selectDueDate);
        dueTime = (TextView) findViewById(R.id.dueTime);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String className = classSpinner.getSelectedItem().toString();
                String description = des.getText().toString();
                int notificaionTime = notificationSpinner.getValue();
                String date = dueDate.getText().toString();
                String time = dueTime.getText().toString();

                Log.e("cName in Task view", className);
                Log.e("des in Task view", description);
                Log.e("date in Task view", date);
                Log.e("time in Task view", time);


                Log.e("Button Done Clicked", "xxxxxxxxxxxxxxxxxxxxx");

                Task t = new Task(className, description, date, time, notificaionTime);
                TaskHandler d = new TaskHandler();
                d.saveTask(t);

                Toast.makeText(AddTask.this,
                        "OnClickListener : " +
                                "\nSpinner : "+ String.valueOf(t.description),
                        Toast.LENGTH_SHORT).show();

                Log.d("This is the task des", t.description);

                //Set the notification
                scheduleNotification(getNotification(t.description), 3000);

                Intent intent = new Intent(v.getContext(), TaskView.class);
                intent.putExtra("Date", currentDate);
                startActivity(intent);
                finish();
            }
        });


    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Log.v("This has run:", "scheudleNotification");
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("PencilMeIn: Task Reminder");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.pencil_me_in_logo);
        return builder.build();
    }

    /*
     * Add Item to spinner
     */
    public void addItemToSpinner(){
        //Class List Spinner
        classSpinner = (Spinner)findViewById(R.id.classListSpinner);

        //Getting Classes
        StorageHandler storage = new StorageHandler();
        List<StorageHandler.ClassListData> classes= storage.retrieveClassListObject();
        if(classes == null) {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            finish();
        }

        List<String> class_list = new ArrayList<String>();
        for (StorageHandler.ClassListData i : classes) {
            class_list.add(i.className);
        }

        // Creating adapter for spinner - Drop down layout style - list view with radio button - set adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, class_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(dataAdapter);
    }

    /*
     * Date Picker
     */
    public void addListenerInDate(){
        dueDate = (TextView) findViewById(R.id.selectDueDate);
        //calender class's instance and get current date , month and year from calender
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        final String formattedDate = sdf.format(calendar.getTime());
        dueDate.setText(formattedDate);

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddTask.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(year, monthOfYear, dayOfMonth);
                                dueDate.setText(sdf.format(calendar.getTime()));

                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    //function to show timepicker dialog
    private void openTimePickerDialog()
    {
        dueTime = (TextView)findViewById(R.id.dueTime);
        calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        dueTime.setText(updateTime(hour,min));
        dueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePickerDialog = new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        //set selected time to textview
                        dueTime.setText(updateTime(hour,min));
                    }
                },hour,min,false);
                timePickerDialog.show();
            }
        });



    }

    public void addItemToNotificationSpinner(){
        //Class List Spinner
        notificationSpinner = (NumberPicker) findViewById(R.id.alarmHourPicker);

        notificationSpinner.setMinValue(0);
        notificationSpinner.setMaxValue(24);
        notificationSpinner.setValue(1);
        notificationSpinner.setWrapSelectorWheel(true);

        notificationSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                notificationSpinner.setValue(newVal);
            }
        });
    }


    // function to get am and pm from time
    private String updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        return aTime;
    }

}
