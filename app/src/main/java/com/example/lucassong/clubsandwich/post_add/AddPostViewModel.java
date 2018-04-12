package com.example.lucassong.clubsandwich.post_add;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.example.lucassong.clubsandwich.AppDatabase;
import com.example.lucassong.clubsandwich.post.Post;

/**
 * Created by Lucas Song on 16/2/2018.
 */

public class AddPostViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddPostViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void addPost(final Post post) {
        new addAsyncTask(appDatabase).execute(post);
    }

    private static class addAsyncTask extends AsyncTask<Post, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Post... params) {
            db.postDao().insertAll(params[0]);
            return null;
        }
    }
}
