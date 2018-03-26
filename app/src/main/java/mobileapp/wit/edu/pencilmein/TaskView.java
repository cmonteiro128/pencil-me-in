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
 * Created by chris on 3/21/18.
 */

public class TaskView extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        //Initialize Paper
        Paper.init(getApplicationContext());

        StorageHandler storage = new StorageHandler();
        List<StorageHandler.ClassListData> classes= storage.retrieveClassListObject();
        if(classes == null) {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            finish();
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.classListView);
        for (StorageHandler.ClassListData i : classes){
            TextView className = new TextView(this);
            className.setText(i.className);
            ll.addView(className);
        }
    }
}