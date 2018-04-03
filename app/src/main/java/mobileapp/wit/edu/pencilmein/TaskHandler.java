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
    private String date;

    TaskHandler(String date){
        this.date = date.replace('/', 'd');
    }

    /*
     * @param: Date
     * Save new task to the database
     */
    public void saveTask(Task t){

        if(Paper.book().contains(this.date)) {
            List<Task> taskArrayList = Paper.book().read(this.date);
            taskArrayList.add(t);
            Paper.book().write(this.date, taskArrayList);


        }
        else {
            List<Task> taskArrayList = new ArrayList<Task>();
            taskArrayList.add(t);
            Paper.book().write(this.date, taskArrayList);
        }

        Log.d("Task Handler", "Task Succesfully saved");

        List<String> allKeys = Paper.book().getAllKeys();

        //List<Task> list = Paper.book().read(this.date);
        Log.e("keys in Task handler:", allKeys.toString());

    }

    /*
     * @param: date
     * @return: all task on provide date
     */
    public List<Task> retriveTask(String d){
        String date = d.replace('/', 'd');
        if(Paper.book().contains(date)) {
            List<Task> taskList = Paper.book().read(date);
            return taskList;
        }
        else{
            return null;
        }

    }


    /*
     * @param:
     */
    public List<Task> retriveTask(){
            Log.e("date retrive task", this.date);
            Log.d("Retrive Tasl", "done");
            List<Task> taskList = Paper.book().read(this.date);
            return taskList;
    }

}
