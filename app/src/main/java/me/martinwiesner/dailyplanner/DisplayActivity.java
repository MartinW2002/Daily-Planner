package me.martinwiesner.dailyplanner;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    private String selectedDay;
    private TextView noPlanTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        selectedDay = "monday";
        noPlanTextView = (TextView) findViewById(R.id.textview_noplan_dis);

        noPlanTextView.setVisibility(View.INVISIBLE);
        selectDay("monday");

        updateList(getTasks(selectedDay));

        ListView ls = (ListView) findViewById(R.id.listview_display);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                if (textView.getPaint().isStrikeThruText())
                    textView.setPaintFlags(0);
                else
                    textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });
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
        switch (view.getId()) {
            case R.id.textview_monday_dis:
                selectDay("monday");
                break;
            case R.id.textview_tuesday_dis:
                selectDay("tuesday");
                break;
            case R.id.textview_wednesday_dis:
                selectDay("wednesday");
                break;
            case R.id.textview_thursday_dis:
                selectDay("thursday");
                break;
            case R.id.textview_friday_dis:
                selectDay("friday");
                break;
            case R.id.textview_saturday_dis:
                selectDay("saturday");
                break;
            case R.id.textview_sunday_dis:
                selectDay("sunday");
                break;
            default:
                new Exception("Invalid view").printStackTrace();
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
        if (tasksStrings == null) {
            tasksStrings = new ArrayList<>();
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.listview_display, tasksStrings);
        adapter.notifyDataSetChanged();
        ListView listView = (ListView) findViewById(R.id.listview_display);
        listView.setAdapter(adapter);
    }

    private void selectDay(String day) {
        TextView dayTextView = null;
        switch (day) {
            case "monday":
                dayTextView = (TextView) findViewById(R.id.textview_monday_dis);
                break;
            case "tuesday":
                dayTextView = (TextView) findViewById(R.id.textview_tuesday_dis);
                break;
            case "wednesday":
                dayTextView = (TextView) findViewById(R.id.textview_wednesday_dis);
                break;
            case "thursday":
                dayTextView = (TextView) findViewById(R.id.textview_thursday_dis);
                break;
            case "friday":
                dayTextView = (TextView) findViewById(R.id.textview_friday_dis);
                break;
            case "saturday":
                dayTextView = (TextView) findViewById(R.id.textview_saturday_dis);
                break;
            case "sunday":
                dayTextView = (TextView) findViewById(R.id.textview_sunday_dis);
                break;
        }
        TextView oldDayTextView = null;
        switch (selectedDay) {
            case "monday":
                oldDayTextView = (TextView) findViewById(R.id.textview_monday_dis);
                break;
            case "tuesday":
                oldDayTextView = (TextView) findViewById(R.id.textview_tuesday_dis);
                break;
            case "wednesday":
                oldDayTextView = (TextView) findViewById(R.id.textview_wednesday_dis);
                break;
            case "thursday":
                oldDayTextView = (TextView) findViewById(R.id.textview_thursday_dis);
                break;
            case "friday":
                oldDayTextView = (TextView) findViewById(R.id.textview_friday_dis);
                break;
            case "saturday":
                oldDayTextView = (TextView) findViewById(R.id.textview_saturday_dis);
                break;
            case "sunday":
                oldDayTextView = (TextView) findViewById(R.id.textview_sunday_dis);
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
        return tasks;
    }
}
