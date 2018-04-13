package com.example.lucassong.clubsandwich.club;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucassong.clubsandwich.ClubProfileActivity;
import com.example.lucassong.clubsandwich.R;

import java.util.List;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class ClubMiniAdapter extends RecyclerView.Adapter<ClubMiniAdapter.ViewHolder> {
    private Context context;

    private static final String TAG = "ClubAdapter";
    private List<Club> clubs;

    public ClubMiniAdapter(List<Club> clubs) {
        this.clubs = clubs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.club_mini_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Club club = clubs.get(position);
        holder.clubName.setText(club.getClubName());
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public void updateItems(List<Club> clubs) {
        this.clubs = clubs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView clubName;

        public ViewHolder(View itemView) {
            super(itemView);

            clubName = itemView.findViewById(R.id.club_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, ClubProfileActivity.class);
                    //intent.putExtra("clubName", clubName.getText().toString());
                    //intent.putExtra("clubID", clubID);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
