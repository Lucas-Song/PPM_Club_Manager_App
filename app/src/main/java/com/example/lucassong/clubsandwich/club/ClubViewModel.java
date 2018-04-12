package com.example.lucassong.clubsandwich.club;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.lucassong.clubsandwich.AppDatabase;

import java.util.List;

/**
 * Created by Lucas Song on 21/2/2018.
 */

public class ClubViewModel extends AndroidViewModel {

    private final LiveData<List<Club>> clubList;
    private AppDatabase appDatabase;

    public ClubViewModel(Application application) {

        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        clubList = appDatabase.clubDao().getAllClubs();
    }

    public LiveData<List<Club>> getClubList() {
        return clubList;
    }

    public void deleteItem(Club club) {
        new deleteAsyncTask(appDatabase).execute(club);
    }

    public void deleteAllClubs() {
        new deleteAllAsyncTask(appDatabase).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Club, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Club... params) {
            db.clubDao().delete(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Club, Void, Void> {

        private AppDatabase db;

        deleteAllAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Club... params) {
            db.clubDao().clearAll();
            return null;
        }
    }
}
