package mobileapp.wit.edu.pencilmein;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by paudyaln on 3/25/2018.
 */

public class TaskHandler {
    private String date;

    TaskHandler(String date){
        this.date = date;
    }

    public void saveTask(Task t){
        List<Task> tasks = new ArrayList<Task>();
        if(Paper.book().contains(this.date)){
            tasks = Paper.book().read(this.date);
            tasks.add(t);
            Paper.book().write(this.date, tasks);
        }
        else{
            tasks.add(t);
            Paper.book().write(this.date, tasks);
        }
        Log.d("Task Handler", "Task Succesfully saved");

    }

    /*
     * @param: date
     * @return: all task on provide date
     */
    public List<Task> retriveTask(String date){
        List<Task> tasks = new ArrayList<Task>();
        if(Paper.book().contains(date)){
            return Paper.book().read(date);
        }
        else{
            return tasks;
        }
    }


    /*
     * @param:
     */
}
