package com.example.lucassong.clubsandwich.reminder;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Lucas Song on 21/3/2018.
 */

@Entity(tableName = "reminder")
public class Reminder {

    public Reminder(int reminderID, int eventID, long minutesBeforeAlert) {
        this.reminderID = reminderID;
        this.eventID = eventID;
        this.minutesBeforeAlert = minutesBeforeAlert;
    }

    @PrimaryKey
    @ColumnInfo(name = "reminder_ID")
    private int reminderID;

    @ColumnInfo(name = "event_ID")
    private int eventID;

    @ColumnInfo(name = "minutes_before_alert")
    private long minutesBeforeAlert;

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public long getMinutesBeforeAlert() {
        return minutesBeforeAlert;
    }

    public void setMinutesBeforeAlert(long minutesBeforeAlert) {
        this.minutesBeforeAlert = minutesBeforeAlert;
    }
}
