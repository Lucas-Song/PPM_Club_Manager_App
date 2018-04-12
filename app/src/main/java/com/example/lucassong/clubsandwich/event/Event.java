package com.example.lucassong.clubsandwich.event;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.lucassong.clubsandwich.DateConverter;

import java.util.Date;

/**
 * Created by Lucas Song on 21/2/2018.
 */

@Entity(tableName = "event")
public class Event {

    public Event(int eventID, String clubName, String eventName,
                 String eventDetails, String eventLocation,
                 Date eventStartDateTime, Date eventEndDateTime,
                 String recurrenceRule) {
        this.eventID = eventID;
        this.clubName = clubName;
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.eventLocation = eventLocation;
        this.eventStartDateTime = eventStartDateTime;
        this.eventEndDateTime = eventEndDateTime;
        this.recurrenceRule = recurrenceRule;
    }



    //Data declaration

    @PrimaryKey
    @ColumnInfo(name = "event_ID")
    private int eventID;

    @ColumnInfo(name = "club_name")
    private String clubName;

    @ColumnInfo(name = "event_name")
    private String eventName;

    @ColumnInfo(name = "event_details")
    private String eventDetails;

    @ColumnInfo(name = "event_location")
    private String eventLocation;

    @ColumnInfo(name = "event_start_date_time")
    @TypeConverters(DateConverter.class)
    private Date eventStartDateTime;

    @ColumnInfo(name = "event_end_date_time")
    @TypeConverters(DateConverter.class)
    private Date eventEndDateTime;

    @ColumnInfo(name = "event_recurrence_rule")
    private String recurrenceRule;



    //Getters and setters

    public int getEventID() {
        return eventID;
    }
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getClubName() {
        return clubName;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDetails() {
        return eventDetails;
    }
    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventLocation() {
        return eventLocation;
    }
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public Date getEventStartDateTime() {
        return eventStartDateTime;
    }
    public void setEventStartDateTime(Date eventStartDateTime) {
        this.eventStartDateTime = eventStartDateTime;
    }

    public Date getEventEndDateTime() {
        return eventEndDateTime;
    }
    public void setEventEndDateTime(Date eventEndDateTime) {
        this.eventEndDateTime = eventEndDateTime;
    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }
    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }
}
