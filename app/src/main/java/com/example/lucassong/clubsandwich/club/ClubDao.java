package com.example.lucassong.clubsandwich.club;

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
public interface ClubDao {

    @Query("SELECT * FROM Club")
    LiveData<List<Club>> getAllClubs();

    @Query("SELECT * FROM Club WHERE club_name LIKE :clubName LIMIT 1")
    Club findByName(String clubName);

    @Query("DELETE FROM Club")
    void clearAll();

    @Insert
    void insertAll(Club... clubs);

    @Update
    void update(Club club);

    @Delete
    void delete(Club club);
}
