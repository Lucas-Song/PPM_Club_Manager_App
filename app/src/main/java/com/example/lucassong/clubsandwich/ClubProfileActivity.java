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
import android.view.View;

import com.example.lucassong.clubsandwich.event.Event;
import com.example.lucassong.clubsandwich.event.EventAdapter;
import com.example.lucassong.clubsandwich.event.EventViewModel;
import com.example.lucassong.clubsandwich.event_add.AddEventActivity;

import java.util.ArrayList;
import java.util.List;

public class ClubProfileActivity extends AppCompatActivity {

    private EventViewModel viewModel;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private FloatingActionButton addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_profile);

        /*
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new ClubAdapter(new ArrayList<Club>());
        adapter.setContext(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        /*
        CalendarContractHandler.updateCalendarView(this, this, this);

        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        viewModel.getEventList().observe(CalendarActivity.this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {
                adapter.updateItems(events);
            }
        });
        */
    }
}
