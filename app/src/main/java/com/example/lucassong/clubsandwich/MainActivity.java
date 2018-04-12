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
import android.view.View;

import com.example.lucassong.clubsandwich.post.Post;
import com.example.lucassong.clubsandwich.post.PostAdapter;
import com.example.lucassong.clubsandwich.post.PostViewModel;
import com.example.lucassong.clubsandwich.post_add.AddPostActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private PostViewModel viewModel;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private FloatingActionButton addPost;
    private FloatingActionButton goToCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new PostAdapter(new ArrayList<Post>());
        adapter.setContext(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(PostViewModel.class);

        viewModel.getPostList().observe(MainActivity.this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                adapter.updateItems(posts);
            }
        });

        addPost = findViewById(R.id.add_post);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddPostActivity.class));
            }
        });

        goToCalendar = findViewById(R.id.go_to_calendar);
        goToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
            }
        });
    }
}
