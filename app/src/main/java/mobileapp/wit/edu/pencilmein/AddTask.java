package mobileapp.wit.edu.pencilmein;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by paudyaln on 3/25/2018.
 */

public class AddTask extends AppCompatActivity{
    private EditText des, alm, dueDate;
    private Spinner classSpinner;
    private Button done;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_addtask);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize Paper
        Paper.init(getApplicationContext());

        addItemToSpinner();
        addListenerInButton();
        addListenerInDate();

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

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String description = des.getText().toString();
                String alarm = alm.getText().toString();
                String item = classSpinner.getSelectedItem().toString();


                Log.e("Button Done Clicked", "xxxxxxxxxxxxxxxxxxxxx");
                Task t = new Task(description, item, alarm);
                TaskHandler d = new TaskHandler("1993-16-17");
                d.saveTask(t);

                Toast.makeText(AddTask.this,
                        "OnClickListener : " +
                                "\nSpinner : "+ String.valueOf(t.description),
                        Toast.LENGTH_SHORT).show();

                Log.d("This is the task des", t.description);

                Intent intent = new Intent(v.getContext(), DailyView.class);
                startActivity(intent);
                finish();
            }
        });


    }

    /*
     * Add Item to spinner
     */
    public void addItemToSpinner(){
        //Class List Spinner
        Spinner classSpinner = (Spinner)findViewById(R.id.classListSpinner);

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
        dueDate = (EditText) findViewById(R.id.selectDueDate);
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddTask.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dueDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
}
