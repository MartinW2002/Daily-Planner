package me.martinwiesner.dailyplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    private String selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Intent myIntent = getIntent();
        selectedDay = myIntent.getExtras().getString("DAY");
    }

    public void onClick2(View view) {
        if (view.getId() == R.id.button_ok) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("DAY", selectedDay);
            intent.putExtra("TASK_NAME", ((EditText) findViewById(R.id.edittext_task_name)).getText().toString());
            startActivity(intent);
        } else if (view.getId() == R.id.button_cancel) {
            finish();
            startActivity(new Intent(this, EditActivity.class));
        }
    }
}
