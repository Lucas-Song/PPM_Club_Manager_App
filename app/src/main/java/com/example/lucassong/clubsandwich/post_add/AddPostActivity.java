package com.example.lucassong.clubsandwich.post_add;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lucassong.clubsandwich.ConnectionHandler;
import com.example.lucassong.clubsandwich.R;
import com.example.lucassong.clubsandwich.post.Post;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lucas Song on 14/2/2018.
 */

public class AddPostActivity extends AppCompatActivity {

    private static final String TAG = "AddClubActivity";

    private String clubName;
    private Date timestamp;

    private EditText textContent;
    private EditText photoContent;

    private Button saveButton;

    private AddPostViewModel addPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);

        clubName = getIntent().getStringExtra("clubName");

        textContent = findViewById(R.id.text_content);
        photoContent = findViewById(R.id.photo_content);

        saveButton = findViewById(R.id.save_button);

        timestamp = Calendar.getInstance().getTime();

        addPostViewModel = ViewModelProviders.of(this).get(AddPostViewModel.class);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: firstName: " + firstName.getText().toString());
                if (clubName == null || textContent.getText() == null || photoContent.getText() == null) {
                    Toast.makeText(AddPostActivity.this, "Missing fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Post newPost = new Post(
                            clubName,
                            timestamp,
                            textContent.getText().toString(),
                            photoContent.getText().toString(),
                            0,
                            0,
                            false,
                            true
                    );

                    addPostViewModel.addPost(newPost);

                    ConnectionHandler connectionHandler = new ConnectionHandler();
                    connectionHandler.insertPostIntoServer(newPost);

                    finish();
                }
            }
        });
    }
}
