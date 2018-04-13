package com.example.lucassong.clubsandwich.club_add;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.example.lucassong.clubsandwich.AppDatabase;
import com.example.lucassong.clubsandwich.club.Club;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class AddClubViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddClubViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void addClub(final Club club) {
        new addAsyncTask(appDatabase).execute(club);
    }

    private static class addAsyncTask extends AsyncTask<Club, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Club... params) {
            db.clubDao().insertAll(params[0]);
            return null;
        }
    }
}
