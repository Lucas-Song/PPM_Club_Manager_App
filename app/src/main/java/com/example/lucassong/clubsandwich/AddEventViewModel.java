package com.example.lucassong.clubsandwich;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

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
}
