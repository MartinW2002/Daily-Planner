package me.martinwiesner.dailyplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    String selectedDay;
    private TextView noPlanTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        selectedDay = "monday";
        noPlanTextView = (TextView) findViewById(R.id.textview_noplan);

        noPlanTextView.setVisibility(View.INVISIBLE);
        selectDay("monday");

        if (getIntent().getExtras() != null) {
            selectedDay = getIntent().getExtras().getString("DAY");

            List<Task> tasks = getTasks(selectedDay);
            tasks.add(new Task(getIntent().getExtras().getString("TASK_NAME"), false));
            setTasks(selectedDay, tasks);
            updateList(tasks);
        }

        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                intent.putExtra("DAY", selectedDay);
                startActivity(intent);
            }
        });

        ListView ls = (ListView) findViewById(R.id.listview_edit);
        ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                open(i);
                return false;
            }
        });
    }

    public void open(final int i) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to remove this task?");

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                List<Task> tasks = getTasks(selectedDay);
                tasks.remove(i);
                setTasks(selectedDay, tasks);
                updateList(tasks);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_display:
                startActivity(new Intent(this, DisplayActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick3(View view) {
        String day = "";
        switch (view.getId()) {
            case R.id.textview_monday:
                selectDay("monday");
                break;
            case R.id.textview_tuesday:
                selectDay("tuesday");
                break;
            case R.id.textview_wednesday:
                selectDay("wednesday");
                break;
            case R.id.textview_thursday:
                selectDay("thursday");
                break;
            case R.id.textview_friday:
                selectDay("friday");
                break;
            case R.id.textview_saturday:
                selectDay("saturday");
                break;
            case R.id.textview_sunday:
                selectDay("sunday");
                break;
            default:
                new Exception("Invalid view").printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput(day + ".dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Task> tasks = (List<Task>) objectInputStream.readObject();
            if (tasks == null)
                tasks = new ArrayList<>();
            updateList(tasks);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateList(List<Task> tasks) {
        List<String> tasksStrings = new ArrayList<>();
        for (Task task : tasks) {
            tasksStrings.add(task.getTask());
        }
        if (tasksStrings.size() == 0) {
            noPlanTextView.setVisibility(View.VISIBLE);
        } else {
            noPlanTextView.setVisibility(View.INVISIBLE);
        }
        //if (tasksStrings == null) {
          //  tasksStrings = new ArrayList<>();
        //}
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.listview_edit, tasksStrings);
        adapter.notifyDataSetChanged();
        ListView listView = (ListView) findViewById(R.id.listview_edit);
        listView.setAdapter(adapter);
    }

    private void selectDay(String day) {
        TextView dayTextView = null;
        switch (day) {
            case "monday":
                dayTextView = (TextView) findViewById(R.id.textview_monday);
                break;
            case "tuesday":
                dayTextView = (TextView) findViewById(R.id.textview_tuesday);
                break;
            case "wednesday":
                dayTextView = (TextView) findViewById(R.id.textview_wednesday);
                break;
            case "thursday":
                dayTextView = (TextView) findViewById(R.id.textview_thursday);
                break;
            case "friday":
                dayTextView = (TextView) findViewById(R.id.textview_friday);
                break;
            case "saturday":
                dayTextView = (TextView) findViewById(R.id.textview_saturday);
                break;
            case "sunday":
                dayTextView = (TextView) findViewById(R.id.textview_sunday);
                break;
        }
        TextView oldDayTextView = null;
        switch (selectedDay) {
            case "monday":
                oldDayTextView = (TextView) findViewById(R.id.textview_monday);
                break;
            case "tuesday":
                oldDayTextView = (TextView) findViewById(R.id.textview_tuesday);
                break;
            case "wednesday":
                oldDayTextView = (TextView) findViewById(R.id.textview_wednesday);
                break;
            case "thursday":
                oldDayTextView = (TextView) findViewById(R.id.textview_thursday);
                break;
            case "friday":
                oldDayTextView = (TextView) findViewById(R.id.textview_friday);
                break;
            case "saturday":
                oldDayTextView = (TextView) findViewById(R.id.textview_saturday);
                break;
            case "sunday":
                oldDayTextView = (TextView) findViewById(R.id.textview_sunday);
                break;
        }
        oldDayTextView.setTypeface(null, Typeface.NORMAL);
        dayTextView.setTypeface(null, Typeface.BOLD);
        selectedDay = day;
        updateList(getTasks(day));
    }

    private List<Task> getTasks(String day) {
        List<Task> tasks = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput(day + ".dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            tasks = (List<Task>) objectInputStream.readObject();
            if (tasks == null)
                tasks = new ArrayList<>();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private void setTasks(String day, List<Task> tasks) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(day + ".dat", MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
