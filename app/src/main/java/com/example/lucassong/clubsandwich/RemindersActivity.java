package com.example.lucassong.clubsandwich;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RemindersActivity extends AppCompatActivity {

    private static final String TAG = "RemindersActivity";

    private String eventName;
    private int eventID;

    private TextView title;

    private ReminderViewModel viewModel;
    private RecyclerView recyclerView;
    private ReminderAdapter adapter;
    private FloatingActionButton addReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        eventName = getIntent().getStringExtra("eventName");
        eventID = getIntent().getIntExtra("eventID", 0);

        title = findViewById(R.id.event_name);

        title.setText(eventName);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new ReminderAdapter(new ArrayList<Reminder>());
        adapter.setContext(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        CalendarContractHandler.updateRemindersView(RemindersActivity.this, RemindersActivity.this, RemindersActivity.this, eventID);

        viewModel = ViewModelProviders.of(this).get(ReminderViewModel.class);

        viewModel.getReminderList().observe(RemindersActivity.this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(@Nullable List<Reminder> reminders) {
                adapter.updateItems(reminders);
                Log.d("R_A", "updateItems() called");
            }
        });

        addReminder = findViewById(R.id.add_reminder);
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG,"onClick: pressed!");
                Intent intent = new Intent(RemindersActivity.this, AddReminderActivity.class);
                intent.putExtra("eventID", eventID);
                intent.putExtra("existingReminder", false);
                startActivity(intent);
            }
        });
    }
}
