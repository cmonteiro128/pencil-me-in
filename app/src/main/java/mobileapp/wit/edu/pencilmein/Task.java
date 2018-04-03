package mobileapp.wit.edu.pencilmein;


import android.util.Log;

/**
 * Created by paudyaln on 3/25/2018.
 */

public class Task {
    String description;
    String className;
    String dueDate;
    String dueTime;
    int notificationTime;


    Task(String className, String description, String dueDate, String dueTime, int notificationTime) {
        this.className = className;
        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.notificationTime = notificationTime;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public void setNotificationTime(int notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getDescription() {
        return description;
    }

    public String getClassName() {
        return className;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueDateForStorage(){
            return dueDate.replace('/', 'd');
    }

    public String getDueTime() {
        return dueTime;
    }

    public int getNotificationTime() {
        return notificationTime;
    }


    public void printTask() {
        Log.v("ClassName", className);
        Log.v("Description", description);
        Log.v("Due Date", dueTime);
        Log.v("Notification Time", notificationTime + "");
    }
}

