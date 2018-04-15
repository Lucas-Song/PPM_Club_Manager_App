package com.example.lucassong.clubsandwich;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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

        Context context = MainActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.username_pref_file_key), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        loginUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(getString(R.string.username_pref_file_key), "user");
                editor.commit();
                startActivity(new Intent(MainActivity.this, TimelineActivity.class));
            }
        });

        loginClubAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(getString(R.string.username_pref_file_key), "admin");
                editor.commit();
                startActivity(new Intent(MainActivity.this, TimelineActivity.class));
            }
        });
    }
}
