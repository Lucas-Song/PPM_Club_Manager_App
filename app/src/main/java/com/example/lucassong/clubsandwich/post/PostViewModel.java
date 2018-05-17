package com.example.lucassong.clubsandwich.post;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.lucassong.clubsandwich.AppDatabase;

import java.util.List;

/**
 * Created by Lucas Song on 16/2/2018.
 */

public class PostViewModel extends AndroidViewModel {

    private final LiveData<List<Post>> postList;

    private AppDatabase appDatabase;

    public PostViewModel(Application application) {

        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        postList = appDatabase.postDao().getAllPosts();
    }

    public LiveData<List<Post>> getPostList() {
        return postList;
    }

    public void deleteItem(Post post) {
        new deleteAsyncTask(appDatabase).execute(post);
    }

    public void deleteAllPosts() {
        new deleteAllAsyncTask(appDatabase).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Post, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Post... params) {
            db.postDao().delete(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Post, Void, Void> {

        private AppDatabase db;

        deleteAllAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Post... params) {
            db.postDao().clearAll();
            return null;
        }
    }
}
