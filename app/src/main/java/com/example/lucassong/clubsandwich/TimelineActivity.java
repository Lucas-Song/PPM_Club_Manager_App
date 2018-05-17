package com.example.lucassong.clubsandwich;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lucassong.clubsandwich.club_add.AddClubActivity;
import com.example.lucassong.clubsandwich.post.Post;
import com.example.lucassong.clubsandwich.post.PostAdapter;
import com.example.lucassong.clubsandwich.post.PostViewModel;
import com.example.lucassong.clubsandwich.post_add.AddPostActivity;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private PostViewModel viewModel;
    private RecyclerView recyclerView;
    private PostAdapter adapter;

    private FloatingActionButton goToCalendar;
    private FloatingActionButton userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new PostAdapter(new ArrayList<Post>());
        adapter.setContext(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(PostViewModel.class);

        viewModel.getPostList().observe(TimelineActivity.this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                adapter.updateItems(posts);
            }
        });

        goToCalendar = findViewById(R.id.go_to_calendar);
        goToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TimelineActivity.this, CalendarActivity.class));
            }
        });

        userProfile = findViewById(R.id.user_profile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TimelineActivity.this, UserProfileActivity.class));
            }
        });
    }
}
