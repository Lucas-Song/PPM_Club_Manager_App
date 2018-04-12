package com.example.lucassong.clubsandwich.club;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucassong.clubsandwich.CalendarContractHandler;
import com.example.lucassong.clubsandwich.R;
import com.example.lucassong.clubsandwich.RemindersActivity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class ClubAdapter
        //extends RecyclerView.Adapter<ClubAdapter.ViewHolder>
        {
            /*
    private Context context;

    private static final String TAG = "ClubAdapter";
    private List<Club> clubs;
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("h:mm a, EEEE, d MMMM yyyy");

    public ClubAdapter(List<Club> clubs) {
        this.clubs = clubs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;// = LayoutInflater.from(parent.getContext())
                //.inflate(R.layout.club_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*
        Club club = clubs.get(position);
        holder.clubID = club.getClubID();
        holder.clubName.setText(club.getClubName());
        holder.clubName.setText(club.getClubName());
        holder.clubDetails.setText(club.getClubDetails());
        holder.clubLocation.setText(club.getClubLocation());
        holder.clubStartDateTime.setText(dateTimeFormat.format(club.getClubStartDateTime()));
        holder.clubEndDateTime.setText(dateTimeFormat.format(club.getClubEndDateTime()));
        holder.recurrenceRule.setText(
                CalendarContractHandler.formatRecurrenceRule(
                        club.getRecurrenceRule(), holder.recurrenceRule.getContext()
                ));
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

        public int clubID;
        public TextView clubName;
        public TextView clubDetails;
        public TextView clubLocation;
        public TextView clubStartDateTime;
        public TextView clubEndDateTime;
        public TextView recurrenceRule;

        public ViewHolder(View itemView) {
            super(itemView);

            clubName = itemView.findViewById(R.id.club_name);
            clubName = itemView.findViewById(R.id.club_name);
            clubDetails = itemView.findViewById(R.id.club_details);
            clubLocation = itemView.findViewById(R.id.club_location);
            clubStartDateTime = itemView.findViewById(R.id.club_start_date_time);
            clubEndDateTime = itemView.findViewById(R.id.club_end_date_time);
            recurrenceRule = itemView.findViewById(R.id.club_recurrence_rule);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view)
                {
                    Intent intent = new Intent(context, RemindersActivity.class);
                    intent.putExtra("clubName", clubName.getText().toString());
                    intent.putExtra("clubID", clubID);
                    context.startActivity(intent);
                    return true;
                }
            });
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
    */
}
