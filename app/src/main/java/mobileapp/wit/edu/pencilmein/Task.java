package mobileapp.wit.edu.pencilmein;


/**
 * Created by paudyaln on 3/25/2018.
 */

public class Task {
    String description;
    String className;
    String dueTime;
    int notificationTime;


    Task(String cName, String des, String dueTime, int notification){
        this.className = cName;
        this.description = des;
        this.dueTime = dueTime;
        this.notificationTime = notification;
    }
}
