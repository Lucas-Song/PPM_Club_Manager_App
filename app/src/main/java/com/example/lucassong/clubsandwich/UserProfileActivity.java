package com.example.lucassong.clubsandwich;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lucassong.clubsandwich.club.Club;
import com.example.lucassong.clubsandwich.club.ClubAdapter;
import com.example.lucassong.clubsandwich.club.ClubMiniAdapter;
import com.example.lucassong.clubsandwich.club.ClubViewModel;
import com.example.lucassong.clubsandwich.event.EventAdapter;
import com.example.lucassong.clubsandwich.event.EventViewModel;
import com.example.lucassong.clubsandwich.reminder.Reminder;
import com.example.lucassong.clubsandwich.reminder.ReminderAdapter;
import com.example.lucassong.clubsandwich.reminder.ReminderViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private ClubViewModel viewModel;
    private RecyclerView recyclerView;
    private ClubMiniAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new ClubMiniAdapter(new ArrayList<Club>());
        adapter.setContext(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(ClubViewModel.class);

        viewModel.getClubList().observe(UserProfileActivity.this, new Observer<List<Club>>() {
            @Override
            public void onChanged(@Nullable List<Club> clubs) {
                adapter.updateItems(clubs);
            }
        });
    }
}
