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
import android.widget.Button;
import android.widget.Toast;

import com.example.lucassong.clubsandwich.post.Post;
import com.example.lucassong.clubsandwich.post.PostAdapter;
import com.example.lucassong.clubsandwich.post.PostViewModel;
import com.example.lucassong.clubsandwich.post_add.AddPostActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button loginUserButton;
    private Button loginClubAdminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginUserButton = findViewById(R.id.user_button);
        loginClubAdminButton = findViewById(R.id.club_admin_button);

        loginUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TimelineActivity.class));
            }
        });

        loginClubAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Still under construction", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
