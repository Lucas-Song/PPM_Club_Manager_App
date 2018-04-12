package com.example.lucassong.clubsandwich.post;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.lucassong.clubsandwich.DateConverter;

import java.util.Date;

/**
 * Created by Lucas Song on 14/2/2018.
 */

@Entity(tableName = "post")
public class Post {

    public Post(String clubName, Date timestamp, String textContent,
                String photoContent, int eventID, int pollID,
                boolean isImportant, boolean isPublic) {
        this.clubName = clubName;
        this.timestamp = timestamp;
        this.textContent = textContent;
        this.photoContent = photoContent;
        this.eventID = eventID;
        this.pollID = pollID;
        this.isImportant = isImportant;
        this.isPublic = isPublic;
    }


    //Data declaration

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_ID")
    private int postID;

    @ColumnInfo(name = "club_name")
    private String clubName;

    @ColumnInfo(name = "timestamp")
    @TypeConverters(DateConverter.class)
    private Date timestamp;

    @ColumnInfo(name = "text_content")
    private String textContent;

    @ColumnInfo(name = "photo_content")
    private String photoContent;

    @ColumnInfo(name = "event_ID")
    private int eventID;

    @ColumnInfo(name = "poll_ID")
    private int pollID;

    @ColumnInfo(name = "is_important")
    private boolean isImportant;

    @ColumnInfo(name = "is_public")
    private boolean isPublic;


    //Getters and setters

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getPhotoContent() {
        return photoContent;
    }

    public void setPhotoContent(String photoContent) {
        this.photoContent = photoContent;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
