package com.example.lucassong.clubsandwich;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context context;

    private static final String TAG = "EventAdapter";
    private List<Event> events;
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("h:mm a, EEEE, d MMMM yyyy");

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.eventID = event.getEventID();
        holder.eventName.setText(event.getEventName());
        holder.clubName.setText(event.getClubName());
        holder.eventDetails.setText(event.getEventDetails());
        holder.eventLocation.setText(event.getEventLocation());
        holder.eventStartDateTime.setText(dateTimeFormat.format(event.getEventStartDateTime()));
        holder.eventEndDateTime.setText(dateTimeFormat.format(event.getEventEndDateTime()));
        holder.recurrenceRule.setText(
                CalendarContractHandler.formatRecurrenceRule(
                        event.getRecurrenceRule(), holder.recurrenceRule.getContext()
                ));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void addItems(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int eventID;
        public TextView eventName;
        public TextView clubName;
        public TextView eventDetails;
        public TextView eventLocation;
        public TextView eventStartDateTime;
        public TextView eventEndDateTime;
        public TextView recurrenceRule;

        public ViewHolder(View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.event_name);
            clubName = itemView.findViewById(R.id.club_name);
            eventDetails = itemView.findViewById(R.id.event_details);
            eventLocation = itemView.findViewById(R.id.event_location);
            eventStartDateTime = itemView.findViewById(R.id.event_start_date_time);
            eventEndDateTime = itemView.findViewById(R.id.event_end_date_time);
            recurrenceRule = itemView.findViewById(R.id.event_recurrence_rule);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view)
                {
                    Intent intent = new Intent(context, RemindersActivity.class);
                    intent.putExtra("eventName", eventName.getText().toString());
                    intent.putExtra("eventID", eventID);
                    context.startActivity(intent);
                    return true;
                }
            });
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
