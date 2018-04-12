package com.example.lucassong.clubsandwich;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private Context context;

    private static final String TAG = "ReminderAdapter";
    private List<Reminder> reminders;

    public ReminderAdapter(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reminder reminder = reminders.get(position);
        String timeBeforeAlert = minutesFormatter(reminder.getMinutesBeforeAlert());
        holder.reminderID = reminder.getReminderID();
        holder.eventID = reminder.getEventID();
        holder.minutesBeforeAlert.setText(timeBeforeAlert);
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public void updateItems(List<Reminder> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int reminderID;
        public int eventID;
        public TextView minutesBeforeAlert;

        public ViewHolder(View itemView) {
            super(itemView);

            minutesBeforeAlert = itemView.findViewById(R.id.minutes_before_alert);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddReminderActivity.class);
                    intent.putExtra("reminderID", reminderID);
                    intent.putExtra("eventID", eventID);
                    intent.putExtra("existingReminder", true);
                    context.startActivity(intent);
                }
            });
        }
    }

    private String minutesFormatter(long minutesBeforeAlert) {

        long minutes = minutesBeforeAlert % 60;
        long hours = 0;
        long days = 0;
        long weeks = 0;

        String displayText = "";
        ArrayList<String> unitsText = new ArrayList<String>();

        if (minutesBeforeAlert >= 60) {
            hours = (minutesBeforeAlert - minutes) / 60;
        }
        if (hours >= 24) {
            long remainderHours = hours % 24;
            days = (hours - remainderHours) / 24;
            hours = remainderHours;
        }
        if (days >= 7) {
            long remainderDays = days % 7;
            weeks = (days - remainderDays) / 7;
            days = remainderDays;
        }

        if (minutes > 0) {
            if (minutes > 1) {
                unitsText.add(minutes + " minutes");
            } else {
                unitsText.add(minutes + " minute");
            }
        }
        if (hours > 0) {
            if (hours > 1) {
                unitsText.add(hours + " hours");
            } else {
                unitsText.add(hours + " hour");
            }
        }
        if (days > 0) {
            if (days > 1) {
                unitsText.add(days + " days");
            } else {
                unitsText.add(days + " day");
            }
        }
        if (weeks > 0) {
            if (weeks > 1) {
                unitsText.add(weeks + " weeks");
            } else {
                unitsText.add(weeks + " week");
            }
        }

        if (unitsText.size() >= 1) {
            displayText = unitsText.get(0) + " before";

            if (unitsText.size() > 1) {
                displayText = unitsText.get(1) + " and " + displayText;

                if (unitsText.size() > 2) {
                    for (int i = 2; i < unitsText.size(); i++) {
                        displayText = unitsText.get(i) + ", " + displayText;
                    }
                }
            }
        }

        return displayText;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
