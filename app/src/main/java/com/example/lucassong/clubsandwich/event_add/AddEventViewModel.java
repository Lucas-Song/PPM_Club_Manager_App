package com.example.lucassong.clubsandwich.event_add;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.example.lucassong.clubsandwich.AppDatabase;
import com.example.lucassong.clubsandwich.ConnectionHandler;
import com.example.lucassong.clubsandwich.event.Event;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class AddEventViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddEventViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void addEvent(final Event event) {
        new addAsyncTask(appDatabase).execute(event);
    }

    public void addEventToServer(String eventName) {
        new uploadSyncTask(appDatabase).execute(eventName);
    }

    private static class addAsyncTask extends AsyncTask<Event, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Event... params) {
            db.eventDao().insertAll(params[0]);
            return null;
        }
    }

    private static class uploadSyncTask extends AsyncTask<String, Event, Event> {

        private AppDatabase db;

        uploadSyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Event doInBackground(final String... params) {
            Event foundEvent = db.eventDao().findByName(params[0]);

            return foundEvent;
        }

        @Override
        protected void onPostExecute(Event event) {
            super.onPostExecute(event);

            ConnectionHandler connectionHandler = new ConnectionHandler();
            connectionHandler.insertEventIntoServer(event);
        }
    }
}
