package com.example.lucassong.clubsandwich;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lucas Song on 14/2/2018.
 */

public class AddPostActivity extends AppCompatActivity {

    private static final String TAG = "AddPostActivity";

    private Date timestamp;

    private EditText clubName;
    private EditText textContent;
    private EditText photoContent;

    private Button saveButton;

    private AddPostViewModel addPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);

        clubName = findViewById(R.id.club_name);
        textContent = findViewById(R.id.text_content);
        photoContent = findViewById(R.id.photo_content);

        saveButton = findViewById(R.id.save_button);

        timestamp = Calendar.getInstance().getTime();

        addPostViewModel = ViewModelProviders.of(this).get(AddPostViewModel.class);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: firstName: " + firstName.getText().toString());
                if (clubName.getText() == null
                        || textContent.getText() == null
                        || photoContent.getText() == null) {
                    Toast.makeText(AddPostActivity.this, "Missing fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    addPostViewModel.addPost(new Post(
                            clubName.getText().toString(),
                            timestamp,
                            textContent.getText().toString(),
                            photoContent.getText().toString(),
                            1,
                            1,
                            false,
                            true
                    ));
                    finish();
                    startActivity(new Intent(AddPostActivity.this, MainActivity.class));
                }
            }
        });
    }
}
