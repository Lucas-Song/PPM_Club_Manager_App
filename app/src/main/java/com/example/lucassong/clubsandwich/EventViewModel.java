package com.example.lucassong.clubsandwich;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class EventViewModel extends AndroidViewModel {

    private final LiveData<List<Event>> eventList;
    private AppDatabase appDatabase;

    public EventViewModel(Application application) {

        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        eventList = appDatabase.eventDao().getAllEvents();
    }

    public LiveData<List<Event>> getEventList() {
        return eventList;
    }

    public void deleteItem(Event event) {
        new deleteAsyncTask(appDatabase).execute(event);
    }

    public void deleteAllEvents() {
        new deleteAllAsyncTask(appDatabase).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Event, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Event... params) {
            db.eventDao().delete(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Event, Void, Void> {

        private AppDatabase db;

        deleteAllAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Event... params) {
            db.eventDao().clearAll();
            return null;
        }
    }
}
