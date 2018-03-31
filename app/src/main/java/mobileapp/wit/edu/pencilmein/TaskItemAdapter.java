package mobileapp.wit.edu.pencilmein;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by paudyaln on 3/30/2018.
 */

public class TaskItemAdapter extends ArrayAdapter<Task> {
    private LayoutInflater mInflater;
    public TaskItemAdapter(Context context, int rid, List<Task> list){
        super(context, rid, list);
        mInflater =
                (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        // Retrieve data
        Task item = (Task)getItem(position);

        // Use layout file to generate View
        View view = mInflater.inflate(R.layout.listitem_taskview, null);

        // Set user name
        TextView className;
        className = (TextView)view.findViewById(R.id.class_name);
        className.setText(item.className);
        // Set description
        TextView des;
        des = (TextView) view.findViewById(R.id.task_des);
        des.setText(item.description);

        TextView time;
        time = (TextView)view.findViewById(R.id.displaytime);
        time.setText(item.dueTime);



        //set
        return view;
    }
}
