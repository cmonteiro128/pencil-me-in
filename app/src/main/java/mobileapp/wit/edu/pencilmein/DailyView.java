package mobileapp.wit.edu.pencilmein;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.paperdb.Paper;

/**
 * Created by paudyaln on 3/25/2018.
 */

public class DailyView extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        Log.e("Daily View", "Initilize");
        //Initialize Paper
        Paper.init(getApplicationContext());

        List<Task> classes = Paper.book().read("1993-16-17");

        LinearLayout ll = (LinearLayout) findViewById(R.id.classListView);
        for (Task i : classes){
            TextView className = new TextView(this);
            className.setText(i.description);
            ll.addView(className);
        }

        List<String> allKeys = Paper.book().getAllKeys();
        Log.e("all keys", allKeys.toString());
    }
}
