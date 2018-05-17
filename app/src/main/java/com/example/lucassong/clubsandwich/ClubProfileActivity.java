package com.example.lucassong.clubsandwich;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.lucassong.clubsandwich.club.Club;
import com.example.lucassong.clubsandwich.club.ClubAdapter;
import com.example.lucassong.clubsandwich.club.ClubViewModel;
import com.example.lucassong.clubsandwich.event_add.AddEventActivity;
import com.example.lucassong.clubsandwich.post_add.AddPostActivity;

import java.util.ArrayList;
import java.util.List;

public class ClubProfileActivity extends AppCompatActivity {

    private ClubViewModel viewModel;
    private RecyclerView recyclerView;
    private ClubAdapter adapter;

    private String clubName;

    private FloatingActionButton addPost;
    private FloatingActionButton addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_profile);

        clubName = getIntent().getStringExtra("clubName");

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new ClubAdapter(new ArrayList<Club>());
        adapter.setContext(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(ClubViewModel.class);
        viewModel.getClubByName(clubName).observe(ClubProfileActivity.this, new Observer<List<Club>>() {
            @Override
            public void onChanged(@Nullable List<Club> clubs) {
                adapter.updateItems(clubs);
            }
        });

        Context context = ClubProfileActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.username_pref_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(getString(R.string.username_pref_file_key), null);

        if (username.equals("admin")) {
            addPost = findViewById(R.id.add_post);
            addPost.setVisibility(View.VISIBLE);
            addPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ClubProfileActivity.this, AddPostActivity.class);
                    intent.putExtra("clubName", clubName);
                    startActivity(intent);
                }
            });

            addEvent = findViewById(R.id.add_event);
            addEvent.setVisibility(View.VISIBLE);
            addEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ClubProfileActivity.this, AddEventActivity.class);
                    intent.putExtra("clubName", clubName);
                    startActivity(intent);
                }
            });
        }
    }
}
