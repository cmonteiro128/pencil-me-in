package mobileapp.wit.edu.pencilmein;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by chris on 3/21/18.
 */

public class StorageHandler {

    private String recieveJson;

    StorageHandler() {
    }

    StorageHandler(String recieveJson){
        this.recieveJson = recieveJson;
    }

    public class ClassListData {
            String className;
            String classTime;
    }

    public void saveJSON() {

        //Create our List token for GSON
        Type classListType = new TypeToken<ArrayList<ClassListData>>(){}.getType();

        //Instantiate GSON
        Gson gson = new Gson();

        //Read our JSON into a Java object via GSON
        List<ClassListData> classList = gson.fromJson(recieveJson, classListType);
        //Write the object to our NoSQL store
        Paper.book().write("classList", classList);
    }

    public List<ClassListData> retrieveClassListObject() {
        List<ClassListData> classList = Paper.book().read("classList");
        return  classList;
    }

}
