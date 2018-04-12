package com.example.lucassong.clubsandwich.reminder;

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
public interface ReminderDao {

    @Query("SELECT * FROM Reminder")
    LiveData<List<Reminder>> getAllReminders();

    @Query("DELETE FROM Reminder")
    void clearAll();

    @Insert
    void insertAll(Reminder... reminders);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);
}
