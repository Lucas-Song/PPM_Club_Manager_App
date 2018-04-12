package com.example.lucassong.clubsandwich.club;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Lucas Song on 2/3/2018.
 */

@Entity(tableName = "club")
public class Club {

    public Club(String clubName, String bio, String meetingLocation,
                String meetingTime, String clubContactNumber, String clubEmail) {
        this.clubName = clubName;
        this.bio = bio;
        this.meetingLocation = meetingLocation;
        this.meetingTime = meetingTime;
        this.clubContactNumber = clubContactNumber;
        this.clubEmail = clubEmail;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "club_name")
    private String clubName;

    @ColumnInfo(name = "bio")
    private String bio;

    @ColumnInfo(name = "meeting_location")
    private String meetingLocation;

    @ColumnInfo(name = "meeting_time")
    private String meetingTime;

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

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
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
