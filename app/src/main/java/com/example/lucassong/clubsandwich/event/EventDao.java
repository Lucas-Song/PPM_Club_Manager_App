package com.example.lucassong.clubsandwich.event;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Lucas Song on 21/2/2018.
 */

@Dao
public interface EventDao {

    @Query("SELECT * FROM Event")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM Event WHERE event_ID LIKE :eventID LIMIT 1")
    Event findByID(String eventID);

    @Query("SELECT * FROM Event WHERE event_name LIKE :eventName LIMIT 1")
    Event findByName(String eventName);

    @Query("DELETE FROM Event")
    void clearAll();

    @Insert
    void insertAll(Event... events);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);
}
