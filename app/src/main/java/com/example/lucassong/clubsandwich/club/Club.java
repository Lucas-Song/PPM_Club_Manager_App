package com.example.lucassong.clubsandwich.club;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.lucassong.clubsandwich.DateConverter;

import java.util.Date;

/**
 * Created by Lucas Song on 2/3/2018.
 */

@Entity(tableName = "club")
public class Club {

    public Club(String clubName, String bio, String meetingLocation,
                Date firstMeetingDate, Date meetingTime, String recurrenceRule,
                String clubContactNumber, String clubEmail) {
        this.clubName = clubName;
        this.bio = bio;
        this.meetingLocation = meetingLocation;
        this.firstMeetingDate = firstMeetingDate;
        this.meetingTime = meetingTime;
        this.recurrenceRule = recurrenceRule;
        this.clubContactNumber = clubContactNumber;
        this.clubEmail = clubEmail;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "club_name")
    private String clubName;

    @ColumnInfo(name = "club_bio")
    private String bio;

    @ColumnInfo(name = "club_meeting_location")
    private String meetingLocation;

    @ColumnInfo(name = "club_first_meeting_date")
    @TypeConverters(DateConverter.class)
    private Date firstMeetingDate;
    
    @ColumnInfo(name = "club_meeting_time")
    @TypeConverters(DateConverter.class)
    private Date meetingTime;

    @ColumnInfo(name = "club_meeting_recurrence_rule")
    private String recurrenceRule;

    @ColumnInfo(name = "club_contact_number")
    private String clubContactNumber;

    @ColumnInfo(name = "club_email")
    private String clubEmail;

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public Date getFirstMeetingDate() {
        return firstMeetingDate;
    }

    public void setFirstMeetingDate(Date firstMeetingDate) {
        this.firstMeetingDate = firstMeetingDate;
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public String getClubContactNumber() {
        return clubContactNumber;
    }

    public void setClubContactNumber(String clubContactNumber) {
        this.clubContactNumber = clubContactNumber;
    }

    public String getClubEmail() {
        return clubEmail;
    }

    public void setClubEmail(String clubEmail) {
        this.clubEmail = clubEmail;
    }
}
