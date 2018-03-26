package mobileapp.wit.edu.pencilmein;


/**
 * Created by paudyaln on 3/25/2018.
 */

public class Task {
    String description;
    String className;
    String time;

    Task(String des, String cName, String time){
        this.className = cName;
        this.description = des;
        this.time = time;
    }

}
