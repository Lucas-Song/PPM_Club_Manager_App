package com.example.lucassong.clubsandwich;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Lucas Song on 2/3/2018.
 */

@Entity(tableName = "club")
public class Club {

    @PrimaryKey
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

}
