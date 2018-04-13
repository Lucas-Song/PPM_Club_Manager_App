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

import java.text.DateFormat;
import java.util.List;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ViewHolder> {
    private Context context;

    private static final String TAG = "ClubAdapter";
    private List<Club> clubs;
    private DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

    public ClubAdapter(List<Club> clubs) {
        this.clubs = clubs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.club_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Club club = clubs.get(position);
        holder.clubName.setText(club.getClubName());
        holder.clubBio.setText(club.getBio());
        holder.clubMeetingLocation.setText(club.getMeetingLocation());
        holder.clubMeetingTime.setText(timeFormat.format(club.getMeetingTime()));
        holder.clubMeetingRecurrenceRule.setText(
                CalendarContractHandler.formatRecurrenceRule(
                        club.getRecurrenceRule(), holder.clubMeetingRecurrenceRule.getContext()
                ));
        holder.clubContactNumber.setText(club.getClubContactNumber());
        holder.clubEmail.setText(club.getClubEmail());
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
        public TextView clubBio;
        public TextView clubMeetingLocation;
        public TextView clubMeetingTime;
        public TextView clubMeetingRecurrenceRule;
        public TextView clubContactNumber;
        public TextView clubEmail;

        public ViewHolder(View itemView) {
            super(itemView);

            clubName = itemView.findViewById(R.id.club_name);
            clubBio = itemView.findViewById(R.id.club_bio);
            clubMeetingLocation = itemView.findViewById(R.id.club_meeting_location);
            clubMeetingTime = itemView.findViewById(R.id.club_meeting_time);
            clubMeetingRecurrenceRule = itemView.findViewById(R.id.club_meeting_recurrence_rule);
            clubContactNumber = itemView.findViewById(R.id.club_contact_number);
            clubEmail = itemView.findViewById(R.id.club_email);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
