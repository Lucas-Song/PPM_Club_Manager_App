package com.example.lucassong.clubsandwich.post;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucassong.clubsandwich.ClubProfileActivity;
import com.example.lucassong.clubsandwich.R;
import com.example.lucassong.clubsandwich.reminder_add.AddReminderActivity;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by Lucas Song on 14/2/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;

    private static final String TAG = "PostAdapter";
    private List<Post> posts;
    private DateFormat dateAndTimeFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT);

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.clubName.setText(post.getClubName());
        holder.timestamp.setText(dateAndTimeFormat.format(post.getTimestamp()));
        holder.textContent.setText(post.getTextContent());
        holder.photoContent.setText(post.getPhotoContent());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void updateItems(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView clubName;
        public TextView timestamp;
        public TextView textContent;
        public TextView photoContent;

        public ViewHolder(View itemView) {
            super(itemView);
            clubName = itemView.findViewById(R.id.club_name);
            timestamp = itemView.findViewById(R.id.timestamp);
            textContent = itemView.findViewById(R.id.text_content);
            photoContent = itemView.findViewById(R.id.photo_content);

            clubName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClubProfileActivity.class);
                    intent.putExtra("clubName", clubName.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
