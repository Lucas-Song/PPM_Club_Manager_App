package com.example.lucassong.clubsandwich.reminder_add;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.example.lucassong.clubsandwich.AppDatabase;
import com.example.lucassong.clubsandwich.reminder.Reminder;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class AddReminderViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddReminderViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void addReminder(final Reminder reminder) {
        new addAsyncTask(appDatabase).execute(reminder);
    }

    private static class addAsyncTask extends AsyncTask<Reminder, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Reminder... params) {
            db.reminderDao().insertAll(params[0]);
            return null;
        }
    }
}
