package mobileapp.wit.edu.pencilmein;

import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by paudyaln on 3/25/2018.
 */

public class TaskHandler {

    /*
     * @param: Date
     * Save new task to the database
     */
    public void saveTask(Task t){
        if(Paper.book().contains(t.getDueDateForStorage())) {
            List<Task> taskArrayList = Paper.book().read(t.getDueDateForStorage());
            taskArrayList.add(t);
            Paper.book().write(t.getDueDateForStorage(), taskArrayList);
        }
        else {
            List<Task> taskArrayList = new ArrayList<Task>();
            taskArrayList.add(t);
            Paper.book().write(t.getDueDateForStorage(), taskArrayList);
        }

        Log.d("Task Handler", "Task Succesfully saved");


        List<String> allKeys = Paper.book().getAllKeys();

        //List<Task> list = Paper.book().read(this.date);
        Log.e("Keys in Task handler:", allKeys.toString());

    }

    /*
     * @param: date
     * @return: all task on provide date
     */
    public List<Task> retrieveTasksForDate(String date){
        String d = date.replace('/', 'd');
        if(Paper.book().contains(date)) {
            return Paper.book().read(d);
        }
        else{
            return null;
        }
    }
}
