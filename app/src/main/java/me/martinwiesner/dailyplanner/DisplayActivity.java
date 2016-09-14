package me.martinwiesner.dailyplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    private String selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        selectedDay = "monday";

        List<Task> tasks = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput(selectedDay + ".dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            tasks = (List<Task>) objectInputStream.readObject();
            if (tasks == null)
                tasks = new ArrayList<>();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        updateList(tasks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit:
                startActivity(new Intent(this, EditActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View view) {
        String day = "";
        switch (view.getId()) {
            case R.id.textview_monday:
                day = "monday";
                break;
            case R.id.textview_tuesday:
                day = "tuesday";
                break;
            case R.id.textview_wednesday:
                day = "wednesday";
                break;
            case R.id.textview_thursday:
                day = "thursday";
                break;
            case R.id.textview_friday:
                day = "friday";
                break;
            case R.id.textview_saturday:
                day = "saturday";
                break;
            case R.id.textview_sunday:
                day = "sunday";
                break;
            default:
                new Exception("Invalid view").printStackTrace();
        }
    }

    private void updateList(List<Task> tasks) {
        Log.e("Display", "length: " + tasks.size());
        List<String> tasksStrings = new ArrayList<>();
        for (Task task : tasks) {
            tasksStrings.add(task.getTask());
        }
        if (tasksStrings.size() == 0) {
            // TODO: 14-Sep-16 Tell the user
            return;
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.listview_edit, tasksStrings);
        ListView listView = (ListView) findViewById(R.id.listview_edit);
        listView.setAdapter(adapter);
    }
}
