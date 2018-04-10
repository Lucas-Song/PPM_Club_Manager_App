package com.example.lucassong.clubsandwich;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class ReminderViewModel extends AndroidViewModel {

    private final LiveData<List<Reminder>> reminderList;
    private AppDatabase appDatabase;

    public ReminderViewModel(Application application) {

        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        reminderList = appDatabase.reminderDao().getAllReminders();
    }

    public LiveData<List<Reminder>> getReminderList() {
        return reminderList;
    }

    public void deleteItem(Reminder reminder) {
        new deleteAsyncTask(appDatabase).execute(reminder);
    }

    public void deleteAllReminders() {
        new deleteAllAsyncTask(appDatabase).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Reminder, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Reminder... params) {
            db.reminderDao().delete(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Reminder, Void, Void> {

        private AppDatabase db;

        deleteAllAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Reminder... params) {
            db.reminderDao().clearAll();
            return null;
        }
    }
}
