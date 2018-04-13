package com.example.lucassong.clubsandwich;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lucassong.clubsandwich.club.Club;
import com.example.lucassong.clubsandwich.club.ClubDao;
import com.example.lucassong.clubsandwich.event.Event;
import com.example.lucassong.clubsandwich.event.EventDao;
import com.example.lucassong.clubsandwich.post.Post;
import com.example.lucassong.clubsandwich.post.PostDao;
import com.example.lucassong.clubsandwich.reminder.Reminder;
import com.example.lucassong.clubsandwich.reminder.ReminderDao;

/**
 * Created by Lucas Song on 15/2/2018.
 */

@Database(entities = {Club.class, Post.class, Event.class, Reminder.class}, version = 11)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_db")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract PostDao postDao();
    public abstract EventDao eventDao();
    public abstract ReminderDao reminderDao();
    public abstract ClubDao clubDao();
}
